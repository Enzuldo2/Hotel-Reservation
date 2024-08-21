package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO {

    public static List<DefaultRoom> readRooms() throws SQLException, ClassNotFoundException {
        ArrayList<DefaultRoom> quartos = new ArrayList<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM quarto";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            DefaultRoom quarto = new DefaultRoom();
            quarto.setRoomId(rs.getInt("id"));
            rs.getString("tipo");
            tipo_categoria(rs, quarto);
            quarto.setDescription(rs.getString("descricao"));
            quarto.setCapacity(rs.getInt("capacidade"));
            quarto.setHeight(rs.getFloat("altura"));
            quarto.setLength(rs.getFloat("comprimento"));
            quarto.setWidth(rs.getFloat("largura"));
            boolean reserved = rs.getInt("reservado") == 1;
            quarto.setReserved(reserved);

            quartos.add(quarto);
        }
        connection.close();
        return quartos;
    }

    private static void tipo_categoria(ResultSet rs, DefaultRoom quarto) throws SQLException {
        if (rs.getString("tipo").equals("Suite")) {
            quarto.setBridge_room(new SuiteRoom());
        } else if (rs.getString("tipo").equals("Familia")) {
            quarto.setBridge_room(new FamilyRoom());
        } else if (rs.getString("tipo").equals("Single")) {
            quarto.setBridge_room(new SingleRoom());
        }
    }

    public static void createRoom(String tipo, String descricao, int capacidade, float altura, float comprimento, float largura,boolean reservado) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO quarto (tipo, descricao, capacidade, altura, comprimento, largura, reservado) VALUES (?, ?, ?, ?, ?, ? , ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, tipo);
        pst.setString(2, descricao);
        pst.setInt(3, capacidade);
        pst.setFloat(4, altura);
        pst.setFloat(5, comprimento);
        pst.setFloat(6, largura);
        var intReservado = reservado ? 1 : 0;
        pst.setInt(7, intReservado);
        pst.executeUpdate();
        connection.close();
    }

    public static void update(int id, String tipo, String descricao, int capacidade, float altura, float comprimento, float largura , boolean reservado) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE quarto SET tipo = ?, descricao = ?, capacidade = ?, altura = ?, comprimento = ?, largura = ? , reservado = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, tipo);
        pst.setString(2, descricao);
        pst.setInt(3, capacidade);
        pst.setFloat(4, altura);
        pst.setFloat(5, comprimento);
        pst.setFloat(6, largura);
        var intReservado = reservado ? 1 : 0;
        pst.setInt(7, intReservado);
        pst.setInt(8, id);

        pst.executeUpdate();
        connection.close();
    }

    public static void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "DELETE FROM quarto WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        connection.close();
    }

    public static DefaultRoom readRoom(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM quarto WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        DefaultRoom quarto = new DefaultRoom();
        if (rs.next()) {
            quarto.setRoomId(rs.getInt("id"));
            rs.getString("tipo");
            tipo_categoria(rs, quarto);
            quarto.setDescription(rs.getString("descricao"));
            quarto.setCapacity(rs.getInt("capacidade"));
            quarto.setHeight(rs.getFloat("altura"));
            quarto.setLength(rs.getFloat("comprimento"));
            quarto.setWidth(rs.getFloat("largura"));
            boolean reserved = rs.getInt("reservado") == 1;
            quarto.setReserved(reserved);
        }
        connection.close();
        return quarto;
    }


}
