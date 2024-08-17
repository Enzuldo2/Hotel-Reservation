package br.ufscar.dc.pooa.Model.interfaces;

public interface Room {
    Room getRoom();
    boolean updateRoom(Bridge_Room bridge_room);
    float area();
    float volume();
    Bridge_Room getBridgeroom();
    ReservationState getReservation_State();
}