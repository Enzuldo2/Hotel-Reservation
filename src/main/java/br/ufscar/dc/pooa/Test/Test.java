package br.ufscar.dc.pooa.Test;

import br.ufscar.dc.pooa.dao.ClientDAO;
import br.ufscar.dc.pooa.dao.ConexaoUtil;
import br.ufscar.dc.pooa.View.VagasView;
import br.ufscar.dc.pooa.domain.users.Admin;


public class Test {
    public static void main(String[] args) {
        try {
            Admin admin = Admin.getInstance();
            admin.createUser("Enzo","123","enzo@gmail.com",false,true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
