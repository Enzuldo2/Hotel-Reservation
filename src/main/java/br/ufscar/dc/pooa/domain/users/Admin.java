package br.ufscar.dc.pooa.domain.users;

import br.ufscar.dc.pooa.dao.ClientDAO;
import br.ufscar.dc.pooa.domain.hotel.Hotel;
import br.ufscar.dc.pooa.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.domain.rooms.SuiteRoom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends DefaultUser{
    private static Admin instance = null;

    private Admin() {
        super();
    }

    //Singleton
    public static synchronized Admin getInstance() {
        if(instance == null) {
            instance = new Admin();
        }
        return instance;
    }
    
    public boolean createUser(String username, String password, String email, boolean isSuperUser, boolean isActive) throws SQLException, ClassNotFoundException {

        DefaultUser user = new Client("username", "password", "email", isSuperUser, isActive);
        int valorConvertido_super = isSuperUser ? 1 : 0;
        int valorConvertido_active = isActive ? 1 : 0;
        ClientDAO.createClient(username, password, email, valorConvertido_super, valorConvertido_active);
        return Hotel.getInstance().getClients().add((Client) user);
    }

    public DefaultUser getUser(int userId) {
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        for(Client client : clients) {
            if(client.getId() == userId) {
                return client;
            }
        }
        return null;
    }

    public boolean updateUser(DefaultUser user) {
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

    public boolean deleteUser(int userId) {
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

    public List<DefaultUser> getUsers() {
        List<Client> clientList = Hotel.getInstance().getClients();
        List<DefaultUser> userList = new ArrayList<>();
        for (Client client : clientList) {
            userList.add(client);
        }
        return userList;
    }

    public boolean createRoom(String roomType, int roomCapacity, float roomPrice, String roomDescription, float roomLength, float roomWidth, float roomHeight) {
        boolean created = false;
        if(roomType.equals("single")) {
            DefaultRoom room = new SingleRoom();
            room.setCapacity(roomCapacity);
            room.setPrice(roomPrice);
            room.setDescription(roomDescription);
            room.setLength(roomLength);
            room.setWidth(roomWidth);
            room.setHeight(roomHeight);
            //colocar no BD
        }
        else if(roomType.equals("suite")) {
            DefaultRoom room = new SuiteRoom();
            room.setCapacity(roomCapacity);
            room.setPrice(roomPrice);
            room.setDescription(roomDescription);
            room.setLength(roomLength);
            room.setWidth(roomWidth);
            room.setHeight(roomHeight);
            // colocar no BD
        }
        else if(roomType.equals("family")) {
            DefaultRoom room = new FamilyRoom();
            room.setCapacity(roomCapacity);
            room.setPrice(roomPrice);
            room.setDescription(roomDescription);
            room.setLength(roomLength);
            room.setWidth(roomWidth);
            room.setHeight(roomHeight);
            // colocar no BD
        }
        return created;
    }

    public boolean makeReservation(int userId, int roomId) {
        boolean reserved = false;
        Hotel hotel = Hotel.getInstance();
        List<Client> clients = hotel.getClients();
        List<DefaultRoom> rooms = hotel.getRooms();
        for(Client client : clients) {
            if(client.getId() == userId) {
                for(DefaultRoom room : rooms) {
                    if(room.getId() == roomId) {
                        room.setReserved(true);
                        reserved = true;
                        break;
                    }
                }
            }
        }
        return reserved;
    }
}
