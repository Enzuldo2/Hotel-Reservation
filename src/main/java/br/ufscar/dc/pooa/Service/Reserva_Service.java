package br.ufscar.dc.pooa.Service;

import br.ufscar.dc.pooa.Model.Estadia;
import br.ufscar.dc.pooa.Model.Reserva;
import br.ufscar.dc.pooa.Model.users.Person;
import br.ufscar.dc.pooa.dao.ReservaDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reserva_Service {
    private static Reserva_Service instance = null;
    private List<Reserva> reservas;

    private Reserva_Service() throws SQLException, ClassNotFoundException {
        reservas = ReservaDAO.readReservas();
    }

    public static Reserva_Service getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Reserva_Service();
        }
        return instance;
    }

    public boolean makeReservation(Person user, Date dataAtual, Date data_inicial, Date data_fim, String tipo_quarto) throws SQLException, ClassNotFoundException, ParseException {
        if (verifica_Reserva(dataAtual ,data_inicial, data_fim, tipo_quarto)) {
            var tipo = Quarto_Service.getInstance().getBridgeRoom(tipo_quarto);
            Reserva reserva = new Reserva(user, tipo, dataAtual, data_inicial, data_fim, true);
            reservas.add(reserva);
            ReservaDAO.createReserva(user.getPersonId(), new java.sql.Date(dataAtual.getTime()), new java.sql.Date(data_inicial.getTime()), new java.sql.Date(data_fim.getTime()), tipo_quarto, true);
            Waiting_List_Service.getInstance().notifyReserva_feita(user.getEmail());
            return true;
        }
        return false;
    }

    public List<Integer> get_Ids(Date data_inicial, Date data_fim, String tipo_quarto) throws SQLException, ClassNotFoundException {
        List<Integer> ids = new ArrayList<>();
        for (Reserva reserva : ReservaDAO.readReservas()) {
            if (reserva.getCategoria().getRoomType().equals(tipo_quarto) && !data_inicial.after(reserva.getDataSaida()) && !data_fim.before(reserva.getDataEntrada()) && reserva.getReserved()) {
                ids.add(reserva.getId());
            }
        }
        return ids;
    }

    public boolean verifica_Reserva(Date dataAtual, Date data_inicial, Date data_fim, String tipo_quarto) throws SQLException, ClassNotFoundException {
        // Validação básica de datas
        if (!isValidDateRange(dataAtual, data_inicial, data_fim)) {
            return false;
        }

        // Obter a quantidade total de quartos disponíveis para o tipo solicitado
        int quantidade_quartos = Quarto_Service.getInstance().quantidade_quartos_para_tipo(tipo_quarto);

        // Inicializa o calendário de disponibilidade
        CalendarioDisponibilidade calendario = new CalendarioDisponibilidade(quantidade_quartos);

        // Adiciona todas as reservas existentes ao calendário
        for (Reserva reserva : ReservaDAO.readReservas()) {
            if (reserva.getCategoria().getRoomType().equals(tipo_quarto)) {
                calendario.registrarReserva(reserva.getDataEntrada(), reserva.getDataSaida());
            }
        }

        // Adiciona todas as estadias existentes ao calendário
        for (Estadia estadia : Estadia_Service.getInstance().getEstadias()) {
            if (estadia.getQuarto().getBridgeroom().getRoomType().equals(tipo_quarto)) {
                calendario.registrarReserva(estadia.getDataEntrada(), estadia.getDataSaida());
            }
        }

        // Verifica se há disponibilidade no período solicitado
        return calendario.temDisponibilidade(data_inicial, data_fim);
    }

    private boolean isValidDateRange(Date dataAtual, Date data_inicial, Date data_fim) {
        if (data_inicial.before(dataAtual)) {
            System.out.println("erro foi aqui " + data_inicial + " " + dataAtual);
            throw new IllegalArgumentException("Data de entrada é antes da data atual.");
        }

        if (data_fim.before(data_inicial)) {
            System.out.println("erro foi aqui " + data_fim + " " + data_inicial);
            throw new IllegalArgumentException("Data de saída é antes da data de entrada.");
        }

        return true;
    }

    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        return dateFormat.parse(dateString);
    }


    public List<Reserva> getReservas(int id) throws SQLException, ClassNotFoundException {
        List<Reserva> reservas_response = new ArrayList<>();
        for (Reserva reserva : ReservaDAO.readReservas()) {
            if (reserva.getCliente().getPersonId() == id) {
                reservas_response.add(reserva);
            }
        }
        return reservas_response;
    }

    public void removeReserva(int reservaid) throws SQLException, ClassNotFoundException {
        ReservaDAO.delete(reservaid);
        Waiting_List_Service.getInstance().delete(reservaid);
        reservas = ReservaDAO.readReservas();
    }

    public void CancelReserva(int id) throws SQLException, ClassNotFoundException {
        for (Reserva reserva : ReservaDAO.readReservas()) {
            if (reserva.getId() == id) {
                Waiting_List_Service waiting_list = Waiting_List_Service.getInstance();
                var ids = get_Ids(reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getCategoria().getRoomType());
                waiting_list.notify(id);
                for (int i : ids) {
                    waiting_list.delete(i);
                }
                reserva.setReserved(false);
                ReservaDAO.delete(id);
                return;
            }
        }
    }

    public List<Reserva> getReservas() throws SQLException, ClassNotFoundException {
        return ReservaDAO.readReservas();
    }
}
