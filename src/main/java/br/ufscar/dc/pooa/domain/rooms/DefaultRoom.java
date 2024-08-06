package br.ufscar.dc.pooa.domain.rooms;

import java.util.List;

import br.ufscar.dc.pooa.interfaces.Room;

public abstract class DefaultRoom implements Room {
    private int roomId;
    private boolean isActive;
    private boolean isReserved;
    private float width;
    private float length;
    private float height;
    private int capacity;
    private float price;
    private String description;

    @Override
    public int createRoom() {
        // Implementação
        return 0;
    }

    @Override
    public Room getRoom() {
        // Implementação
        return null;
    }

    @Override
    public boolean updateRoom() {
        // Implementação
        return false;
    }

    @Override
    public boolean deleteRoom() {
        // Implementação
        return false;
    }

    @Override
    public List<Room> getRooms() {
        // Implementação
        return null;
    }

    @Override
    public List<Room> getAvailableRooms() {
        // Implementação
        return null;
    }

    @Override
    public float getPrice() {
        // Implementação
        return 0;
    }

    @Override
    public float area() {
        // Implementação
        return 0;
    }

    @Override
    public float volume() {
        // Implementação
        return 0;
    }

    // Getters e Setters

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isReserved() {
        return isReserved;
    }   

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
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
        return this.getClass().getSimpleName();
    }

    public int getId() {
        return roomId;
    }
}
