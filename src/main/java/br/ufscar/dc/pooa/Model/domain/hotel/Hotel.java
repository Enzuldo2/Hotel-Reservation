package br.ufscar.dc.pooa.Model.domain.hotel;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ufscar.dc.pooa.Model.domain.DefaultService;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;
import br.ufscar.dc.pooa.dao.ClientDAO;
import br.ufscar.dc.pooa.dao.QuartoDAO;
import br.ufscar.dc.pooa.dao.ReservaDAO;

import java.util.ArrayList;
import java.util.Objects;

public class Hotel {
    private static Hotel instance = null;
    private String name;
    private boolean isFull;
    private List<Client> clients;
    private List<DefaultRoom> rooms;
    private List<DefaultService> services;
    private List<Reserva> reservas;


    private Hotel() throws SQLException, ClassNotFoundException {
        this.clients = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
        clients = ClientDAO.readClientslist();
        this.reservas = new ArrayList<>();
        reservas = ReservaDAO.readReservas();
        rooms = QuartoDAO.readRooms();
    }

    public static Hotel getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Hotel();
        }
        return instance;
    }

    public Client haveClient(String username,String password) {
        for (Client client : clients) {
            if (client.getName().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }



    public List<DefaultRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<DefaultRoom> rooms) {
        this.rooms = rooms;
    }

    public List<DefaultService> getServices() {
        return services;
    }

    public void setServices(List<DefaultService> services) {
        this.services = services;
    }


    // Métodos para adicionar e remover clientes,  quartos, serviços e reservas

    public void addClient(Client client) throws SQLException, ClassNotFoundException {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }


    public void addRoom(DefaultRoom room) {
        this.rooms.add(room);
    }

    public void removeRoom(DefaultRoom room) {
        this.rooms.remove(room);
    }

    public void addService(DefaultService service) {
        this.services.add(service);
    }

    public void removeService(DefaultService service) {
        this.services.remove(service);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void removeReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }

    public boolean verifica_Reserva(Date data_inicial, Date data_fim, String tipo_quarto) {
        int quantidade_quartos = quantidade_quartos_para_tipo(tipo_quarto);
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
    public int quantidade_quartos_para_tipo(String tipo_quarto){
        int quantidade_quartos = 0;
        for (DefaultRoom room : rooms) {
            if (room.getBridgeroom().getRoomType().equals(tipo_quarto)) {
                quantidade_quartos++;
            }
        }
        return quantidade_quartos;
    }

    public Bridge_Room getBridgeRoom(String tipo_quarto){
        switch (tipo_quarto) {
            case "Single":
                return new SingleRoom();
            case "Suite":
                return new SuiteRoom();
            case "Family":
                return new FamilyRoom();
        }
        return null;
    }


    public Object getClient(int idCliente) {
        for (Client client : clients) {
            if (client.getId() == idCliente) {
                return client;
            }
        }
        return null;
    }
}
