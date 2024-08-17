package br.ufscar.dc.pooa.Model.domain.users;


import java.util.Date;

public interface Person {



    public int getPersonId();

    public void setPersonId(int personId);

    public String getName();

    public void setName(String name);

    Date getBirthday();

    void setBirthday(Date birthday);

    public String getPhone();

    public void setPhone(String phone);
}