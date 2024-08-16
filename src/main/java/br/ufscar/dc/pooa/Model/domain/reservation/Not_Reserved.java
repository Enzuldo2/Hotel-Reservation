package br.ufscar.dc.pooa.Model.domain.reservation;

import br.ufscar.dc.pooa.Model.interfaces.ReservationState;

public class Not_Reserved implements ReservationState {


    private static Not_Reserved instance = null;

    private Not_Reserved() {
    }

    public static Not_Reserved getInstance() {
        if (instance == null) {
            return new Not_Reserved();
        }
        return instance;
    }


    @Override
    public ReservationState getReservation() {
        return instance;
    }

    public ReservationState updateReservation() {
        return Reserved.getInstance();
    }

}
