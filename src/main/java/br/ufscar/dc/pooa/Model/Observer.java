package br.ufscar.dc.pooa.Model;


import java.sql.SQLException;

public interface Observer {
    void update(String tipo) throws SQLException, ClassNotFoundException;
}
