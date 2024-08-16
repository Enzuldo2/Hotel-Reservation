package br.ufscar.dc.pooa.Model.domain.users;

import br.ufscar.dc.pooa.Model.interfaces.ReservationState;


import java.util.Date;

public class Client implements Person {
    private int id;
    private String username;
    private String password;
    private String email;
    private ReservationState reservation;
    private String name;
    private Date birthday;
    private String phone;




    public Client(String username, String password, String email, Date birthday) {
        super();
        this.birthday = birthday;
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public Client() {
        super();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setActive(int active) {
        boolean isActive = active == 1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int getPersonId() {
        return id;
    }

    @Override
    public void setPersonId(int personId) {
        id = personId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
