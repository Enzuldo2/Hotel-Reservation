package br.ufscar.dc.pooa.Service;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.dao.ReservaDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public boolean makeReservation(Client user, Date dataAtual, Date data_inicial, Date data_fim, String tipo_quarto) throws SQLException, ClassNotFoundException, ParseException {
        if (verifica_Reserva(dataAtual ,data_inicial, data_fim, tipo_quarto)) {
            var tipo = Quarto_Service.getInstance().getBridgeRoom(tipo_quarto);
            Reserva reserva = new Reserva(user, tipo, dataAtual, data_inicial, data_fim, true);
            reservas.add(reserva);
            ReservaDAO.createReserva(user.getPersonId(), new java.sql.Date(dataAtual.getTime()), new java.sql.Date(data_inicial.getTime()), new java.sql.Date(data_fim.getTime()), tipo_quarto, true);
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

        List<Estadia> estadias = Estadia_Service.getInstance().getEstadias();

        int quantidade_quartos = Quarto_Service.getInstance().quantidade_quartos_para_tipo(tipo_quarto);

        for (Reserva reserva : ReservaDAO.readReservas()) {
            for(Estadia estadia : estadias){
                // Verifica se o tipo de quarto é o mesmo
                if (reserva.getCategoria().getRoomType().equals(tipo_quarto)) {
                    // Verifica se as datas colidem
                    if (datasColidem(data_inicial, data_fim, reserva.getDataEntrada(), reserva.getDataSaida())) {
                        // Verifica se o quarto está reservado
                        if (reserva.getReserved()) {
                            quantidade_quartos--;
                        }
                    }
                }
                // Verifica se o quarto estara ocupado por uma estadia
                if(estadia.getQuarto().getBridgeroom().getRoomType().equals(tipo_quarto)){
                    if(datasColidem(data_inicial, data_fim, estadia.getDataEntrada(), estadia.getDataSaida())){
                        quantidade_quartos--;
                    }
                }
            }

        }

        return quantidade_quartos > 0;
    }

    private boolean isValidDateRange(Date dataAtual, Date data_inicial, Date data_fim) {
        // Verifica se a data de entrada é depois ou igual a data atual
        if (data_inicial.before(dataAtual)) {
            System.out.println("erro foi aqui " + data_inicial + " " + dataAtual);
            throw new IllegalArgumentException("Data de entrada é antes da data atual.");
        }

        // Verifica se a data de saída é depois da data de entrada
        if (data_fim.before(data_inicial)) {
            System.out.println("erro foi aqui " + data_fim + " " + data_inicial);
            throw new IllegalArgumentException("Data de saída é antes da data de entrada.");
        }

        return true;
    }

    private boolean datasColidem(Date data_inicial, Date data_fim, Date dataEntradaReserva, Date dataSaidaReserva) {
        // Verifica se o intervalo de datas da reserva colide com o intervalo da reserva atual
        return !data_fim.before(dataEntradaReserva) && !data_inicial.after(dataSaidaReserva);
    }

    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Desativa o modo lenient para impedir datas como 42/08/2024
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

    public void removeReserva(Reserva reserva) throws SQLException, ClassNotFoundException {
        ReservaDAO.delete(reserva.getId());
        Waiting_List_Service.getInstance().delete(reserva.getId());
        reservas = ReservaDAO.readReservas();
    }

    public void CancelReserva(int id) throws SQLException, ClassNotFoundException {
        for (Reserva reserva : ReservaDAO.readReservas()) {
            if (reserva.getId() == id) {
                reserva.setReserved(false);
                Waiting_List_Service.getInstance().notify(id);
                Waiting_List_Service.getInstance().delete(id);
                ReservaDAO.delete(id);
            }
        }
    }

    public List<Reserva> getReservas() throws SQLException, ClassNotFoundException {
        return ReservaDAO.readReservas();
    }
}
