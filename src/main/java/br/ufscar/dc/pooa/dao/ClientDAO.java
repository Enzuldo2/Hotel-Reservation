package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.domain.users.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO {

    public static ArrayList<Client> readClientslist() throws SQLException, ClassNotFoundException {
        ArrayList<Client> clients = new ArrayList<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM client WHERE id = 1";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int isSuperUser = rs.getInt("isSuperUser");
            if(isSuperUser == 1) {
                continue;
            }
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setPassword(rs.getString("password"));
            client.setEmail(rs.getString("email"));
            client.setSuperUser(isSuperUser);
            client.setActive(rs.getInt("isActive"));
            clients.add(client);
        }
        connection.close();
        return clients;
    }


    public static void createClient(String name, String password , String email, int isSuperUser , int isActive) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO client (name, password, email, isSuperuser, isActive) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setInt(4, isSuperUser);
        pst.setInt(5, isActive);
        pst.executeUpdate();

        connection.close();
    }

    public static void update(int id,String name , String password , String email, int isSuperUser , int isActive ) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE client SET name = ?, password = ?, email = ?, isSuperUser = ?, isActive = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setInt(4, isSuperUser);
        pst.setInt(5, isActive);
        pst.setInt(6, id);
        pst.executeUpdate();
        connection.close();
    }

    public static void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "DELETE FROM client WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        connection.close();
    }

}

