package br.ufscar.dc.pooa.Model.domain;


import java.sql.SQLException;

public interface Observer {
    void update(String tipo) throws SQLException, ClassNotFoundException;
}
