package br.ufscar.dc.pooa.Service;

import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;
import br.ufscar.dc.pooa.dao.QuartoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Quarto_Service {


    private static Quarto_Service instance = null;
    private List<DefaultRoom> rooms;

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
        switch (roomType) {
            case "Single": {
                Bridge_Room bridge_room = new SingleRoom();
                DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
                addRoom(room);
                created = true;
                break;
            }
            case "Suite": {
                Bridge_Room bridge_room = new SuiteRoom();
                DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
                addRoom(room);
                created = true;
                break;
            }
            case "Familia": {
                Bridge_Room bridge_room = new FamilyRoom();
                DefaultRoom room = new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
                addRoom(room);
                created = true;
                break;
            }
        }
        return created;
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
            case "Familia":
                return new FamilyRoom();
        }
        return null;
    }

    public void addRoom(DefaultRoom room) throws SQLException, ClassNotFoundException {
        QuartoDAO.createRoom(room.getBridgeroom().getRoomType(), room.getDescription(), room.getCapacity(), room.getHeight(), room.getLength(), room.getWidth(), room.getReservation());
        rooms = QuartoDAO.readRooms();
    }

    public void removeRoom(int id) throws SQLException, ClassNotFoundException {
        QuartoDAO.delete(id);
        rooms = QuartoDAO.readRooms();
    }

    public void updateRoom(DefaultRoom room) throws SQLException, ClassNotFoundException {
        QuartoDAO.update(room.getRoomId(), room.getBridgeroom().getRoomType(), room.getDescription(), room.getCapacity(), room.getHeight(), room.getLength(), room.getWidth(), room.getReservation());
        rooms = QuartoDAO.readRooms();
    }

    public List<DefaultRoom> getRooms() throws SQLException, ClassNotFoundException {
        rooms = QuartoDAO.readRooms();
        return rooms;
    }

    public DefaultRoom getRoom(int roomId) {
        for (DefaultRoom room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public DefaultRoom getRoom(String roomType) throws SQLException, ClassNotFoundException {
        rooms = QuartoDAO.readRooms();
        for (DefaultRoom room : rooms) {
            if (room.getBridgeroom().getRoomType().equals(roomType) && !room.getReservation()){
                return room;
            }
        }
        return null;
    }
}
