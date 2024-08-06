package br.ufscar.dc.pooa.domain.reservation;

import br.ufscar.dc.pooa.interfaces.ReservationState;
import br.ufscar.dc.pooa.interfaces.ReservationState;


public class Reserved implements ReservationState {

    private static Reserved instance = null;

    private Reserved() {

    }

    public static Reserved getInstance() {
        if (instance == null) {
            return new Reserved();
        }
        return instance;
    }


    @Override
    public ReservationState getReservation() {
        return instance;
    }

    @Override
    public ReservationState updateReservation() {
        return Canceled.getInstance();
    }



}
