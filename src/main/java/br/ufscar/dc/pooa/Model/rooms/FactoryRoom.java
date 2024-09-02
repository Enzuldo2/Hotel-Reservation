package br.ufscar.dc.pooa.Model.rooms;

public class FactoryRoom {
    public static Room createDefaultRoom(Bridge_Room bridge_room, int roomCapacity, String roomDescription, float roomLength, float roomWidth, float roomHeight) {
        return new DefaultRoom(bridge_room, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight);
    }
}
