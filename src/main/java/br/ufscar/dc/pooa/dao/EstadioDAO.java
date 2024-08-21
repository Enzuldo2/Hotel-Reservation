package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstadioDAO {


    public static void createEstadia(Client cliente, DefaultRoom quarto, Date dataEntrada, Date dataSaida, int pessoas) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO estadia (id_cliente, id_quarto, data_entrada, data_saida,pessoas) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, cliente.getId());
        pst.setInt(2, quarto.getId());
        pst.setDate(3, new java.sql.Date(dataEntrada.getTime()));
        pst.setDate(4, new java.sql.Date(dataSaida.getTime()));
        pst.setInt(5, pessoas);
        pst.executeUpdate();
        connection.close();
    }

    public static List<Estadia> readEstadias() throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        List<Estadia> estadias = new ArrayList<>();
        String query = "SELECT * FROM estadia";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Estadia estadia = new Estadia();
            estadia.setId(rs.getInt("id"));
            estadia.setCliente(ClientDAO.readClient(rs.getInt("id_cliente")));
            estadia.setQuarto(QuartoDAO.readRoom(rs.getInt("id_quarto")));
            estadia.setDataEntrada(rs.getDate("data_entrada"));
            estadia.setDataSaida(rs.getDate("data_saida"));
            estadia.setPessoas(rs.getInt("pessoas"));
            estadias.add(estadia);
        }
        rs.close();
        pst.close();
        connection.close();
        return estadias;
    }

    public static Estadia readEstadia(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM estadia WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Estadia estadia = null;
        if (rs.next()) {
            estadia = new Estadia();
            estadia.setId(rs.getInt("id"));
            estadia.setCliente(ClientDAO.readClient(rs.getInt("id_cliente")));
            estadia.setQuarto(QuartoDAO.readRoom(rs.getInt("id_quarto")));
            estadia.setDataEntrada(rs.getDate("data_entrada"));
            estadia.setDataSaida(rs.getDate("data_saida"));
            estadia.setPessoas(rs.getInt("pessoas"));
        }
        rs.close();
        pst.close();
        connection.close();
        return estadia;
    }

    public static void updateEstadia(int id, Client cliente, DefaultRoom quarto, Date dataEntrada, Date dataSaida,int pessoas) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE estadia SET id_cliente = ?, id_quarto = ?, data_entrada = ?, data_saida = ?, pessoas = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, cliente.getId());
        pst.setInt(2, quarto.getId());
        pst.setDate(3, new java.sql.Date(dataEntrada.getTime()));
        pst.setDate(4, new java.sql.Date(dataSaida.getTime()));
        pst.setInt(5, id);
        pst.setInt(6, pessoas);
        pst.executeUpdate();
        connection.close();
    }

    public static void deleteEstadia(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "DELETE FROM estadia WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        connection.close();
    }


}
