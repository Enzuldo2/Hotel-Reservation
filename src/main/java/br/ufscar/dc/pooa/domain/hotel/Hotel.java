package br.ufscar.dc.pooa.domain.hotel;

import java.util.List;

import br.ufscar.dc.pooa.domain.Address;
import br.ufscar.dc.pooa.domain.DefaultService;
import br.ufscar.dc.pooa.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.domain.users.Admin;
import br.ufscar.dc.pooa.domain.users.Client;

import java.util.ArrayList;

public class Hotel {
    private static Hotel instance = null;
    private String name;
    private Address address;
    private boolean isFull;
    private List<Client> clients;
    private List<Admin> admins;
    private List<DefaultRoom> rooms;
    private List<DefaultService> services;


    private Hotel() {
        this.clients = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
    }

    public static Hotel getInstance() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
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


    // Métodos para adicionar e remover clientes, administradores, quartos, serviços e reservas

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public void addAdmin(Admin admin) {
        this.admins.add(admin);
    }

    public void removeAdmin(Admin admin) {
        this.admins.remove(admin);
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
