package br.ufscar.dc.pooa.Model.interfaces;

public interface Bridge_Room {
    Bridge_Room get_Tipo_Room();
    boolean update_Tipo_Room(Bridge_Room bridge_room);
    boolean delete_Tipo_Room();
    String getRoomType();
    Float getRoomPrice();
}
