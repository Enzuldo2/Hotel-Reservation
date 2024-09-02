package br.ufscar.dc.pooa.Model.users;


import java.util.Date;

public interface Person {



    int getPersonId();

    void setPersonId(int personId);

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getPhone();

    void setPhone(String phone);

    Date getBirthday();

    void setBirthday(Date birthday);

}