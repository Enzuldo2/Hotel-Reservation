package br.ufscar.dc.pooa.Model.rooms;

public class Factory_Bridge_Room {
    public Bridge_Room createSingleRoom() {
        return new SingleRoom();
    }
    public Bridge_Room createFamiliaRoom() {
        return new FamilyRoom();
    }

    public Bridge_Room createSuiteRoom() {
        return new SuiteRoom();
    }
}
