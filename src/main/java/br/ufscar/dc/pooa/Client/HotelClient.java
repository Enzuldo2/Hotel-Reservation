package br.ufscar.dc.pooa.Client;

import br.ufscar.dc.pooa.domain.hotel.Hotel;
import br.ufscar.dc.pooa.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.domain.rooms.SuiteRoom;

public class HotelClient {
    public static void main(String[] args) {
        Hotel hotel = Hotel.getInstance();

        SingleRoom room1 = new SingleRoom();
        room1.setRoomId(1);
        room1.setActive(true);
        room1.setReserved(false);
        room1.setWidth(10.0f);
        room1.setLength(15.0f);
        room1.setHeight(8.0f);
        room1.setCapacity(1);
        room1.setPrice(100.0f);
        room1.setDescription("Quarto individual com uma cama.");

        SuiteRoom room2 = new SuiteRoom();
        room2.setRoomId(2);
        room2.setActive(true);
        room2.setReserved(false);
        room2.setWidth(20.0f);
        room2.setLength(30.0f);
        room2.setHeight(10.0f);
        room2.setCapacity(4);
        room2.setPrice(300.0f);
        room2.setDescription("Suíte luxuosa com duas camas e sala de estar.");

        FamilyRoom room3 = new FamilyRoom();
        room3.setRoomId(3);
        room3.setActive(true);
        room3.setReserved(false);
        room3.setWidth(15.0f);
        room3.setLength(25.0f);
        room3.setHeight(9.0f);
        room3.setCapacity(6);
        room3.setPrice(200.0f);
        room3.setDescription("Quarto familiar com três camas.");

        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);

        System.out.println("Códigos dos quartos no hotel:");
        for (DefaultRoom room : hotel.getRooms()) {
            System.out.println("Código do Quarto: " + room.getRoomId() + ", Descrição: " + room.getDescription());
        }
    }
}
