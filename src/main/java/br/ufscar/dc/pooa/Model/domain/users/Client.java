package br.ufscar.dc.pooa.Model.domain.users;


import java.util.Date;

class Client implements Person {
    private int id;
    private String password;
    private String email;
    private String name;
    private Date birthday;
    private String phone;




    public Client(String username, String password, String email, Date birthday, String phone) {
        super();
        this.birthday = birthday;
        this.setName(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setPhone(phone);
    }


    public void setId(int id) {
        setPersonId(id);
    }


    public int getId() {
        return getPersonId();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
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


}
