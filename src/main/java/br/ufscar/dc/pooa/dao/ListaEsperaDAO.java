package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaEsperaDAO {

    public static HashMap<Integer,List<String>> readLista() throws SQLException, ClassNotFoundException {
        HashMap<Integer,List<String>> waitingList = new HashMap<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM lista_espera";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String email = rs.getString("email");
            if(waitingList.containsKey(id)){
                waitingList.get(id).add(email);
            }else{
                waitingList.put(id, List.of(email));
            }
        }
        connection.close();
        return waitingList;
    }

    public static void createLista(Integer id, String email) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO lista_espera (id, email) VALUES (?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.setString(2, email);
        pst.executeUpdate();
    }

    public static void deleteLista(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "DELETE FROM lista_espera WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
    }
}
