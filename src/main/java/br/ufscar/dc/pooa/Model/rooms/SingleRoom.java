package br.ufscar.dc.pooa.Model.rooms;

public class SingleRoom implements Bridge_Room {
    private Float roomPrice = 70f;

    public SingleRoom() {
    }


    @Override
    public Bridge_Room get_Tipo_Room() {
        return this;
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
        return "Single";
    }

    @Override
    public Float getRoomPrice() {
        return roomPrice;
    }

}
