package br.ufscar.dc.pooa.dao;


import br.ufscar.dc.pooa.Model.users.Factory_Person;
import br.ufscar.dc.pooa.Model.users.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientDAO {

    public static List<Person> readClientslist() throws SQLException, ClassNotFoundException {
        ArrayList<Person> clients = new ArrayList<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM client";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Person client = Factory_Person.createClient("null", "null", "null", new Date(), "null");
            settingAtributes(rs, client);
            clients.add(client);
        }

        rs.close();
        pst.close();
        connection.close();
        return clients;
    }

    private static void settingAtributes(ResultSet rs, Person client) throws SQLException {
        client.setPersonId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setPassword(rs.getString("password"));
        client.setEmail(rs.getString("email"));
        client.setBirthday(rs.getDate("birthday"));
        client.setPhone(rs.getString("telefone"));
    }

    public static void createClient(String name, String password, String email, Date birthday,String phone) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO client (name, password, email, birthday,telefone) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        preparingStatment(pst , name, password, email, birthday, phone);
        pst.executeUpdate();
        connection.close();
    }

    private static void preparingStatment(PreparedStatement pst,String name, String password, String email, Date birthday, String phone) throws SQLException {
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, email);
        pst.setDate(4, new java.sql.Date(birthday.getTime()));
        pst.setString(5, phone);
    }

    public static void update(int id, String name, String password, String email, Date birthday,String phone) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE client SET name = ?, password = ?, email = ?, birthday = ?, telefone = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        preparingStatment(pst,name, password, email, birthday, phone);
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

    public static boolean userExists(String username, String password, String email, Date birthday,String phone) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT COUNT(*) FROM client WHERE name = ? AND password = ? AND email = ? AND birthday = ? AND telefone = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        preparingStatment(pst ,username, password, email, birthday, phone);

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
        Person client = null;
        if (rs.next()) {
            client = Factory_Person.createClient("null", "null", "null", new Date(), "null");
            settingAtributes(rs, client);
        }
        connection.close();
        return client;
    }
}
