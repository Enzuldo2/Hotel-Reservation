package br.ufscar.dc.pooa.Model.domain.users;

import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;
import br.ufscar.dc.pooa.dao.ClientDAO;
import br.ufscar.dc.pooa.Model.domain.hotel.Hotel;
import br.ufscar.dc.pooa.Model.domain.reservation.Reserved;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin {

    private static Admin instance = null;

    private Admin() {
    }

    //Singleton
    public static synchronized Admin getInstance() {
        if(instance == null) {
            instance = new Admin();
        }
        return instance;
    }
    
    public boolean createUser(String username, String password, String email, Date birthday) throws SQLException, ClassNotFoundException, ParseException {
        boolean created = ClientDAO.userExists(username, password, email, birthday);
        if(created) {
            Client user = new Client(username, password, email, birthday);
            Hotel.getInstance().getClients().add((Client) user);
            ClientDAO.createClient(username, password, email, birthday);
        }
        return created;
    }

    public Person getUser(int userId) throws SQLException, ClassNotFoundException {
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        for(Client client : clients) {
            if(client.getId() == userId) {
                return client;
            }
        }
        return null;
    }

    public boolean updateUser(Person user) throws SQLException, ClassNotFoundException {
        boolean updated = false;
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        for(Client client : clients) {
            if(client.getId() == ((Client)user).getId()) {
                clients.remove(client);
                clients.add((Client) user);
                updated = true;
                break;
            }
        }
        return updated;
    }

    public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
        boolean deleted = false;
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        for(Client client : clients) {
            if(client.getId() == userId) {
                clients.remove(client);
                deleted = true;
                break;
            }
        }
        return deleted;
    }

    public List<Person> getUsers() throws SQLException, ClassNotFoundException {
        List<Client> clientList = Hotel.getInstance().getClients();
        List<Person> userList = new ArrayList<>();
        for (Client client : clientList) {
            userList.add(client);
        }
        return userList;
    }

    public boolean createRoom(String roomType, int roomCapacity, float roomPrice, String roomDescription, float roomLength, float roomWidth, float roomHeight) {
        boolean created = false;
        if(roomType.equals("single")) {
            Bridge_Room bridge_room = new SingleRoom();
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomPrice, roomDescription, roomLength, roomWidth, roomHeight);
            //colocar no BD
        }
        else if(roomType.equals("suite")) {
            Bridge_Room bridge_room = new SuiteRoom();
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomPrice, roomDescription, roomLength, roomWidth, roomHeight);
            // colocar no BD
        }
        else if(roomType.equals("family")) {
            Bridge_Room bridge_room = new FamilyRoom();
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomPrice, roomDescription, roomLength, roomWidth, roomHeight);
            // colocar no BD
        }
        return created;
    }

    public boolean makeReservation(int userId, int roomId) throws SQLException, ClassNotFoundException {
        boolean reserved = false;
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        List<DefaultRoom> rooms = hotel.getRooms();
        for(Client client : clients) {
            if(client.getId() == userId) {
                for(DefaultRoom room : rooms) {
                    if(room.getId() == roomId) {
                        room.setReserved(Reserved.getInstance());
                        reserved = true;
                        break;
                    }
                }
            }
        }
        return reserved;
    }
}
