package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Model.domain.users.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ClientDAO {

    public static ArrayList<Client> readClientslist() throws SQLException, ClassNotFoundException {
        ArrayList<Client> clients = new ArrayList<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM client";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setPassword(rs.getString("password"));
            client.setEmail(rs.getString("email"));
            client.setBirthday(rs.getDate("birthday"));
            clients.add(client);
        }

        rs.close();
        pst.close();
        connection.close();
        return clients;
    }

    public static void createClient(String name, String password, String email, Date birthday) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO client (name, password, email, birthday) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setDate(4, new java.sql.Date(birthday.getTime()));
        pst.executeUpdate();
        connection.close();
    }

    public static void update(int id, String name, String password, String email, Date birthday) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE client SET name = ?, password = ?, email = ?, birthday = ?, isSuperUser = ?, isActive = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setDate(4, new java.sql.Date(birthday.getTime()));
        pst.setInt(5, id);
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

    public static boolean userExists(String username, String password, String email, Date birthday) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT COUNT(*) FROM client WHERE name = ? AND password = ? AND email = ? AND birthday = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setDate(4, new java.sql.Date(birthday.getTime()));

        ResultSet rs = pst.executeQuery();
        rs.next();
        boolean clientExists = rs.getInt(1) > 0;
        connection.close();
        return clientExists;
    }

    public static Person readClient(int idCliente) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM client WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, idCliente);
        ResultSet rs = pst.executeQuery();
        Client client = null;
        if (rs.next()) {
            client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setPassword(rs.getString("password"));
            client.setEmail(rs.getString("email"));
            client.setBirthday(rs.getDate("birthday"));
        }
        connection.close();
        return client;
    }
}
