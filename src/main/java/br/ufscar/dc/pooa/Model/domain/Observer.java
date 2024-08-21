package br.ufscar.dc.pooa.Model.domain;


import java.sql.SQLException;
import java.util.List;

public interface Observer {
    void update(List<String> emails,Integer id) throws SQLException, ClassNotFoundException;
}
