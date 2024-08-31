package br.ufscar.dc.pooa.Model.domain.rooms;

import br.ufscar.dc.pooa.Model.domain.users.Person;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;

public class FamilyRoom implements Bridge_Room {
    private Float roomPrice = 100f;


    public FamilyRoom() {
    }

    @Override
    public Bridge_Room get_Tipo_Room() {
        return null;
    }

    @Override
    public boolean update_Tipo_Room(Bridge_Room bridge_room) {
        return false;
    }

    @Override
    public boolean delete_Tipo_Room() {
        return false;
    }

    @Override
    public String getRoomType() {
        return "Familia";
    }

    @Override
    public Float getRoomPrice() {
        return roomPrice;
    }
}
