package br.ufscar.dc.pooa.Model.domain.rooms;

import br.ufscar.dc.pooa.Model.domain.reservation.Not_Reserved;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;
import br.ufscar.dc.pooa.Model.interfaces.ReservationState;
import br.ufscar.dc.pooa.Model.interfaces.Room;

public class DefaultRoom  implements Room{
    private int roomId;
    private ReservationState reservation;
    private float width;
    private float length;
    private float height;
    private int capacity;
    private float price;
    private String description;
    private Bridge_Room bridge_room;

    public DefaultRoom(Bridge_Room room_type, int roomCapacity, float roomPrice, String roomDescription, float roomLength, float roomWidth, float roomHeight) {
        this.reservation = Not_Reserved.getInstance();
        this.capacity = roomCapacity;
        this.price = roomPrice;
        this.description = roomDescription;
        this.width = roomWidth;
        this.length = roomLength;
        this.height = roomHeight;
        this.bridge_room = room_type;
    }


    @Override
    public Room getRoom() {
       return this;
    }




    @Override
    public boolean updateRoom(Bridge_Room bridge_room) {
        this.bridge_room = bridge_room;
        return bridge_room.update_Tipo_Room(bridge_room);
    }



    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public float area() {
        return width * length;
    }

    @Override
    public float volume() {
        return width * length * height;
    }

    // Getters e Setters

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public boolean isReserved() {
        if(reservation instanceof Not_Reserved) {
            return false;
        }
        return true;
    }   

    public void setReserved(ReservationState isReserved) {
        this.reservation = isReserved;
    }

    public float getWidth() {
        return width;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomType() {
        return bridge_room.getRoomType();
    }

    public int getId() {
        return roomId;
    }
}
