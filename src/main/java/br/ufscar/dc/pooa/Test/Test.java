package br.ufscar.dc.pooa.Test;


import br.ufscar.dc.pooa.Model.domain.users.Admin;
import br.ufscar.dc.pooa.View.VagasView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {
    public static void main(String[] args) {
        Admin admim = Admin.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date birthDate = dateFormat.parse("12/08/2001");
            admim.createUser("Enzo", "123456", "123456789", birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
