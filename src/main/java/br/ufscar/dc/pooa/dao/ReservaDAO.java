package br.ufscar.dc.pooa.dao;

import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.FamilyRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SingleRoom;
import br.ufscar.dc.pooa.Model.domain.rooms.SuiteRoom;
import br.ufscar.dc.pooa.Model.domain.users.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public static List<Reserva> readReservas() throws SQLException, ClassNotFoundException {
        ArrayList<Reserva> reservas = new ArrayList<>();
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM reserva";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Reserva reserva = new Reserva();
            reserva.setId(rs.getInt("id"));
            var id_cliente = rs.getInt("id_client");
            Setting_data(rs, reserva, id_cliente);
            reservas.add(reserva);
        }
        rs.close();
        pst.close();
        connection.close();
        return reservas;
    }

    private static void Setting_data(ResultSet rs, Reserva reserva, int id_cliente) throws SQLException, ClassNotFoundException {
        var client = ClientDAO.readClient(id_cliente);
        reserva.setCliente((Person) client);
        reserva.setDataReserva(rs.getDate("data_reserva"));
        reserva.setDataEntrada(rs.getDate("data_entrada"));
        reserva.setDataSaida(rs.getDate("data_saida"));
        var categoria = rs.getString("categoria");
        tipo_categoria(rs, reserva);
        var bol = (rs.getInt("reservado") == 1);
        reserva.setReserved(bol);
    }

    private static void tipo_categoria(ResultSet rs, Reserva reserva) throws SQLException {
        if (rs.getString("categoria").equals("Suite")) {
            reserva.setCategoria(new SuiteRoom());
        } else if (rs.getString("categoria").equals("Familia")) {
            reserva.setCategoria(new FamilyRoom());
        } else if (rs.getString("categoria").equals("Single")) {
            reserva.setCategoria(new SingleRoom());
        }
    }

    public static void createReserva(int id_client, Date data_reserva, Date data_entrada, Date data_saida, String categoria, boolean b) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "INSERT INTO reserva (id_client, data_reserva, data_entrada, data_saida,categoria,reservado) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id_client);
        pst.setDate(2, data_reserva);
        pst.setDate(3, data_entrada);
        pst.setDate(4, data_saida);
        pst.setString(5, categoria);
        int a = b ? 1 : 0;
        pst.setInt(6, a);

        pst.executeUpdate();
        connection.close();
    }

    public static void update(int id, int id_cliente, java.sql.Date data_reserva, java.sql.Date data_entrada, java.sql.Date data_saida, String categoria,boolean reservado) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "UPDATE reserva SET id_cliente = ?, data_reserva = ?, data_entrada = ?, data_saida = ?, categoria = ? , reservado = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id_cliente);
        pst.setDate(2, data_reserva);
        pst.setDate(3, data_entrada);
        pst.setDate(4, data_saida);
        pst.setString(5, categoria);
        pst.setInt(6, reservado ? 1 : 0);
        pst.setInt(6, id);
        pst.executeUpdate();
        connection.close();
    }

    public static void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "DELETE FROM reserva WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        connection.close();
    }

    public static Reserva readReserva(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT * FROM reserva WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Reserva reserva = new Reserva();
        if (rs.next()) {
            reserva.setId(rs.getInt("id"));
            var id_cliente = rs.getInt("id_cliente");
            Setting_data(rs, reserva, id_cliente);
        }
        connection.close();
        return reserva;
    }

    public static boolean reservaExists(int id_cliente, java.sql.Date data_reserva, java.sql.Date data_entrada, java.sql.Date data_saida,String categoria,boolean reservado) throws SQLException, ClassNotFoundException {
        Connection connection = ConexaoUtil.getInstance().Connection();
        String query = "SELECT COUNT(*) FROM reserva WHERE id_cliente = ? AND data_reserva = ? AND data_entrada = ? AND data_saida = ? AND categoria = ? AND reservado = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, id_cliente);
        pst.setDate(2, data_reserva);
        pst.setDate(3, data_entrada);
        pst.setDate(4, data_saida);
        pst.setString(5, categoria);
        pst.setInt(6, reservado ? 1 : 0);

        ResultSet rs = pst.executeQuery();
        rs.next();
        boolean reservaExists = rs.getInt(1) > 0;
        connection.close();
        return reservaExists;
    }



}
