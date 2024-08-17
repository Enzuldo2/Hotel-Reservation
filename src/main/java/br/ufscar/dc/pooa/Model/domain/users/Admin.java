package br.ufscar.dc.pooa.Model.domain.users;

import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;
import br.ufscar.dc.pooa.dao.ClientDAO;
import br.ufscar.dc.pooa.Model.domain.hotel.Hotel;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;
import br.ufscar.dc.pooa.dao.QuartoDAO;
import br.ufscar.dc.pooa.dao.ReservaDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Admin {

    private static final Logger logger = Logger.getLogger(Admin.class.getName());

    private Admin() {
    }

    private static class AdminHolder {
        private static final Admin INSTANCE = new Admin();
    }

    public static Admin getInstance() {
        return AdminHolder.INSTANCE;
    }

    public boolean createUser(String username, String password, String email, Date birthday) throws SQLException, ClassNotFoundException, ParseException {
        if (ClientDAO.userExists(username, password, email, birthday)) {
            logger.info("User already exists");
            return false;
        }
        Client user = new Client(username, password, email, birthday);
        Hotel.getInstance().addClient(user);
        ClientDAO.createClient(username, password, email, birthday);
        logger.info("User created successfully");
        return true;
    }

    public Person getUser(int userId) throws SQLException, ClassNotFoundException {
        for (Client client : Hotel.getInstance().getClients()) {
            if (client.getId() == userId) {
                return client;
            }
        }
        return null;
    }

    public boolean updateUser(Person user) throws SQLException, ClassNotFoundException {
        List<Client> clients = Hotel.getInstance().getClients();
        for (Client client : clients) {
            if (client.getId() == user.getPersonId()) {
                clients.remove(client);
                clients.add((Client) user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
        List<Client> clients = Hotel.getInstance().getClients();
        for (Client client : clients) {
            if (client.getId() == userId) {
                clients.remove(client);
                return true;
            }
        }
        return false;
    }

    public List<Client> getUsers() throws SQLException, ClassNotFoundException {
        List<Client> userList = Hotel.getInstance().getClients();
        return userList;
    }

    public boolean createRoom(String roomType, int roomCapacity,  String roomDescription, float roomLength, float roomWidth, float roomHeight) throws SQLException, ClassNotFoundException {
        boolean created = false;
        if(roomType.equals("Single")) {
            Bridge_Room bridge_room = new SingleRoom();
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity,  roomDescription, roomLength, roomWidth, roomHeight);
            Hotel.getInstance().addRoom(room);
            QuartoDAO.createRoom(roomType, roomDescription, roomCapacity, roomHeight, roomLength, roomWidth);
            created = true;
        }
        else if(roomType.equals("Suite")) {
            Bridge_Room bridge_room = new SuiteRoom();
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
            Hotel.getInstance().addRoom(room);
            QuartoDAO.createRoom(roomType, roomDescription, roomCapacity, roomHeight, roomLength, roomWidth);
            created = true;
        }
        else if(roomType.equals("Family")) {
            Bridge_Room bridge_room = new FamilyRoom( );
            DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
            Hotel.getInstance().addRoom(room);
            QuartoDAO.createRoom(roomType, roomDescription, roomCapacity, roomHeight, roomLength, roomWidth);
            created = true;
        }
        return created;
    }

    public boolean makeReservation(Client user, String dataAtual, Date data_inicial, Date data_fim , String tipo_quarto) throws SQLException, ClassNotFoundException, ParseException {
        boolean created = false;
        var hotel = Hotel.getInstance();
        var worked = hotel.verifica_Reserva(data_inicial, data_fim, tipo_quarto);
        if (worked) {
            var tipo = hotel.getBridgeRoom(tipo_quarto);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date data_reserva = dateFormat.parse(dataAtual);
            Reserva reserva = new Reserva(user,tipo, data_reserva ,data_inicial, data_fim, true );
            Hotel.getInstance().addReserva(reserva);
            ReservaDAO.createReserva(user.getPersonId(), new java.sql.Date(data_reserva.getTime()), new java.sql.Date(data_inicial.getTime()), new java.sql.Date(data_fim.getTime()), tipo_quarto,true);
            created = true;
        }
        return created;
    }
}
