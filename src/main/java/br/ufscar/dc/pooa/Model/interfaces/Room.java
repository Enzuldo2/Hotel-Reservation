package br.ufscar.dc.pooa.Model.interfaces;

public interface Room {
    Room getRoom();
    int getRoomId();
    boolean updateRoom(Bridge_Room bridge_room);
    float area();
    float volume();
    Bridge_Room getBridgeroom();
    Boolean getReservation();
}