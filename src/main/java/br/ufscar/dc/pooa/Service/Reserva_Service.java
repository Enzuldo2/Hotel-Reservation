package br.ufscar.dc.pooa.Service;


import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
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
        this.reservas = new ArrayList<>();
        reservas = ReservaDAO.readReservas();
    }

    public static Reserva_Service getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Reserva_Service();
        }
        return instance;
    }

    public boolean makeReservation(Client user, String dataAtual, Date data_inicial, Date data_fim , String tipo_quarto) throws SQLException, ClassNotFoundException, ParseException {
        boolean created = false;
        var worked = verifica_Reserva(data_inicial, data_fim, tipo_quarto);
        if (worked) {
            var tipo = Quarto_Service.getInstance().getBridgeRoom(tipo_quarto);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date data_reserva = dateFormat.parse(dataAtual);
            Reserva reserva = new Reserva(user,tipo, data_reserva ,data_inicial, data_fim, true );
            reservas.add(reserva);
            ReservaDAO.createReserva(user.getPersonId(), new java.sql.Date(data_reserva.getTime()), new java.sql.Date(data_inicial.getTime()), new java.sql.Date(data_fim.getTime()), tipo_quarto,true);
            created = true;
        }
        return created;
    }

    public List<Integer> get_Ids(Client user, String dataAtual, Date data_inicial, Date data_fim , String tipo_quarto) throws SQLException, ClassNotFoundException, ParseException {
        List<Integer> ids = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataInicialStr = dateFormat.format(data_inicial);
        String dataFimStr = dateFormat.format(data_fim);

        for (Reserva reserva : reservas) {
            String dataReservaInicialStr = dateFormat.format(reserva.getDataEntrada());
            String dataReservaFimStr = dateFormat.format(reserva.getDataSaida());
            if (reserva.getCategoria().getRoomType().equals(tipo_quarto) &&
                    dataInicialStr.equals(dataReservaInicialStr) &&
                    dataFimStr.equals(dataReservaFimStr) &&
                    reserva.getReserved()) {
                    ids.add(reserva.getId());
            }
        }
        return ids;
    }

    public boolean verifica_Reserva(Date data_inicial, Date data_fim, String tipo_quarto) throws SQLException, ClassNotFoundException {
        reservas = ReservaDAO.readReservas();
        int quantidade_quartos = Quarto_Service.getInstance().quantidade_quartos_para_tipo(tipo_quarto);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataInicialStr = dateFormat.format(data_inicial);
        String dataFimStr = dateFormat.format(data_fim);

        for (Reserva reserva : reservas) {
            String dataReservaInicialStr = dateFormat.format(reserva.getDataEntrada());
            String dataReservaFimStr = dateFormat.format(reserva.getDataSaida());
            if (reserva.getCategoria().getRoomType().equals(tipo_quarto) &&
                    dataInicialStr.equals(dataReservaInicialStr) &&
                    dataFimStr.equals(dataReservaFimStr) &&
                    reserva.getReserved()) {
                quantidade_quartos--;
            }
        }
        return quantidade_quartos > 0;
    }

    public List<Reserva> getReservas(int id) throws SQLException, ClassNotFoundException {
        List<Reserva> reservas_response = new ArrayList<>();
        reservas = ReservaDAO.readReservas();
        for (Reserva reserva : this.reservas) {
            if (reserva.getCliente().getPersonId() == id) {
                reservas_response.add(reserva);
            }
        }
        return reservas_response;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void removeReserva(Reserva reserva) throws SQLException, ClassNotFoundException {
        ReservaDAO.delete(reserva.getId());
        Waiting_List_Service.getInstance().delete(reserva.getId());
        reservas = ReservaDAO.readReservas();
    }

    public void CancelReserva(int id) throws SQLException, ClassNotFoundException {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                reserva.setReserved(false);
                Waiting_List_Service.getInstance().notify(id);
                ReservaDAO.delete(id);
            }
        }
    }


    public List<Reserva> getReservas() throws SQLException, ClassNotFoundException {
        reservas = ReservaDAO.readReservas();
        return reservas;
    }


}
