package br.ufscar.dc.pooa.Model.domain.hotel;

import java.sql.SQLException;
import java.util.List;

import br.ufscar.dc.pooa.Model.domain.DefaultService;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Admin;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.dao.ClientDAO;

import java.util.ArrayList;

public class Hotel {
    private static Hotel instance = null;
    private String name;
    private boolean isFull;
    private List<Client> clients;
    private List<DefaultRoom> rooms;
    private List<DefaultService> services;


    private Hotel() throws SQLException, ClassNotFoundException {
        this.clients = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
        clients = ClientDAO.readClientslist();
    }

    public static Hotel getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Hotel();
        }
        return instance;
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

}
