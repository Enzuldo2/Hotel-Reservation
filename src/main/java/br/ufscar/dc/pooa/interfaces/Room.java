package br.ufscar.dc.pooa.interfaces;

import java.util.List;

public interface Room {
    int createRoom();
    Room getRoom();
    boolean updateRoom();
    boolean deleteRoom();
    List<Room> getRooms();
    List<Room> getAvailableRooms();
    float getPrice();
    float area();
    float volume();
}