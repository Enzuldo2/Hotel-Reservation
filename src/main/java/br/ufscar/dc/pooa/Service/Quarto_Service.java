package br.ufscar.dc.pooa.Service;

import br.ufscar.dc.pooa.Model.rooms.FactoryRoom;
import br.ufscar.dc.pooa.Model.rooms.Factory_Bridge_Room;
import br.ufscar.dc.pooa.Model.rooms.Bridge_Room;
import br.ufscar.dc.pooa.Model.rooms.Room;
import br.ufscar.dc.pooa.dao.QuartoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Quarto_Service {


    private static Quarto_Service instance = null;
    private List<Room> rooms;

    public static Quarto_Service getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Quarto_Service();
        }
        return instance;
    }

    private Quarto_Service() throws SQLException, ClassNotFoundException {
        this.rooms = new ArrayList<>();
        rooms = QuartoDAO.readRooms();
    }

    public boolean createRoom(String roomType, int roomCapacity, String roomDescription, float roomLength, float roomWidth, float roomHeight) throws SQLException, ClassNotFoundException {
        boolean created = false;
        var bridge_room = getBridgeRoom(roomType);
        if (bridge_room != null) {
            created = true;
        }
        var room  = FactoryRoom.createDefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
        addRoom(room);
        return created;
    }

    Bridge_Room getBridgeRoom(String roomType) {
        var factory = new Factory_Bridge_Room();
        switch (roomType) {
            case "Suite":
                return factory.createSuiteRoom();
            case "Familia":
                return factory.createFamiliaRoom();
            case "Single":
                return factory.createSingleRoom();
            default:
                return null;
        }
    }

    public int quantidade_quartos_para_tipo(String tipo_quarto){
        int quantidade_quartos = 0;
        for (Room room : rooms) {
            if (room.getBridgeroom().getRoomType().equals(tipo_quarto)){
                quantidade_quartos++;
            }
        }
        return quantidade_quartos;
    }

    public void addRoom(Room room) throws SQLException, ClassNotFoundException {
        QuartoDAO.createRoom(room.getBridgeroom().getRoomType(), room.getDescription(), room.getCapacity(), room.getHeight(), room.getLength(), room.getWidth(), room.getReservation());
        rooms = QuartoDAO.readRooms();
    }

    public void removeRoom(int id) throws SQLException, ClassNotFoundException {
        QuartoDAO.delete(id);
        rooms = QuartoDAO.readRooms();
    }

    public void updateRoom(Room room) throws SQLException, ClassNotFoundException {
        QuartoDAO.update(room.getRoomId(), room.getBridgeroom().getRoomType(), room.getDescription(), room.getCapacity(), room.getHeight(), room.getLength(), room.getWidth(), room.getReservation());
        rooms = QuartoDAO.readRooms();
    }

    public List<Room> getRooms() throws SQLException, ClassNotFoundException {
        rooms = QuartoDAO.readRooms();
        return rooms;
    }

    public Room getRoom(int roomId) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public Room getRoom(String roomType) throws SQLException, ClassNotFoundException {
        rooms = QuartoDAO.readRooms();
        for (Room room : rooms) {
            if (room.getBridgeroom().getRoomType().equals(roomType) && !room.getReservation()){
                return room;
            }
        }
        return null;
    }
}
