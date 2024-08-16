package br.ufscar.dc.pooa.Model.domain.rooms;

import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;

public class SingleRoom implements Bridge_Room {
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
    
}
