package br.ufscar.dc.pooa.Model.domain.rooms;

import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;

public class SuiteRoom implements Bridge_Room {

    private Float roomPrice = 200f;

    public SuiteRoom() {

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
        return "Suite";
    }

    @Override
    public Float getRoomPrice() {
        return roomPrice;
    }

}
