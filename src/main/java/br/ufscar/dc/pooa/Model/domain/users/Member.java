package br.ufscar.dc.pooa.Model.domain.users;


import java.util.Date;

public class Member implements Person {
    private int id;
    private String name;
    private Date birthday;
    private String phone;

    @Override
    public int getPersonId() {
        return id;
    }

    @Override
    public void setPersonId(int personId) {
        this.id = personId;
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