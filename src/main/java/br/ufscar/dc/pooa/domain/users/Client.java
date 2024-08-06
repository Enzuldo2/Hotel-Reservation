package br.ufscar.dc.pooa.domain.users;

import br.ufscar.dc.pooa.interfaces.ReservationState;


import java.util.List;

import java.util.ArrayList;

public class Client extends DefaultUser {

    private ReservationState reservation;

    private List<Member> members;


    public Client(String username, String password, String email, boolean isSuperUser, boolean isActive) {
        super();
        this.members = new ArrayList<>();
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        int valorConvertido_super = isSuperUser ? 1 : 0;
        int valorConvertido_active = isActive ? 1 : 0;
        this.setSuperUser(valorConvertido_super);
        this.setActive(valorConvertido_active);
    }

    public Client() {
        super();
        this.members = new ArrayList<>();
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void removeMember(Member member) {
        this.members.remove(member);
    }

    public void setId(int id) {
        setPersonId(id);
    }

    public ReservationState getReservation() {
        return reservation;
    }

    public void setReservation(ReservationState reservation) {
        this.reservation = reservation;
    }

    public void UpdateReservation() {
        this.reservation.updateReservation();

    }


    public int getId() {
        return getPersonId();
    }


}
