package br.ufscar.dc.pooa.domain.reservation;

import br.ufscar.dc.pooa.interfaces.ReservationState;
import br.ufscar.dc.pooa.interfaces.ReservationState;

import java.util.Date;
import java.util.List;

public class Canceled implements ReservationState {


    private static  Canceled instance = null;

    private Canceled() {
    }

    public static Canceled getInstance() {
        if (instance == null) {
            return new Canceled();
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
