package br.ufscar.dc.pooa.Model.rooms;


public class DefaultRoom  implements Room{
    private int roomId;
    private boolean reservation;
    private float width;
    private float length;
    private float height;
    private int capacity;
    private String description;
    private Bridge_Room bridge_room;

    public DefaultRoom(Bridge_Room room_type, int roomCapacity,  String roomDescription, float roomLength, float roomWidth, float roomHeight) {
        this.reservation = false;
        this.capacity = roomCapacity;
        this.description = roomDescription;
        this.width = roomWidth;
        this.length = roomLength;
        this.height = roomHeight;
        this.bridge_room = room_type;
    }

    public DefaultRoom() {
    }



    @Override
    public int getRoomId() {
        return roomId;
    }



    @Override
    public Bridge_Room getBridgeroom() {
        return bridge_room;
    }

    @Override
    public Boolean getReservation() {
        return reservation;
    }

    // Getters e Setters


    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public boolean isReserved() {
        return reservation;
    }   

    public void setReserved(boolean reserved) {
        this.reservation = reserved;

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

    public void setBridge_room(Bridge_Room bridge_room) {
        this.bridge_room = bridge_room;
    }
}
