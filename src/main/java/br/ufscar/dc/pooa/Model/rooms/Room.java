package br.ufscar.dc.pooa.Model.rooms;

import java.sql.SQLException;

public interface Room {
    int getRoomId();
    Bridge_Room getBridgeroom();
    Boolean getReservation();
    boolean isReserved();
    void setRoomId(int id);
    void setWidth(float width);
    void setLength(float length) throws SQLException;
    void setHeight(float height);
    void setDescription(String description);
    void setCapacity(int capacity);
    float getWidth();
    float getLength();
    float getHeight();
    int getCapacity();
    String getDescription();
    void setReserved(boolean reserved);
    void setBridge_room(Bridge_Room bridge_room);
}