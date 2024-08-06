package br.ufscar.dc.pooa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConexaoUtil {
    private String caminho = "localhost";
    private String porta = "3306";
    private String nome = "hotel";
    private String usuario = "root";
    private String senha = "";
    private String url = "jdbc:mysql://" + caminho + ":" + porta + "/" + nome+"?useTimezone=true&serverTimezone=UTC";
    private static ConexaoUtil conexaoUtil = null;

    private ConexaoUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Singleton
    public static synchronized ConexaoUtil getInstance() {
        if (conexaoUtil == null) {
            conexaoUtil = new ConexaoUtil();
        }
        return conexaoUtil;
    }


    public Connection Connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, usuario, senha);
        return connection;

    }

}
