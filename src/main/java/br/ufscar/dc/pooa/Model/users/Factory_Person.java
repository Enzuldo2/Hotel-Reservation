package br.ufscar.dc.pooa.Model.users;

import java.util.Date;

public class Factory_Person {

    public static Person createClient(String username, String password, String email, Date birthday, String phone) {
        return new Client(username, password, email, birthday,phone);
    }
}
