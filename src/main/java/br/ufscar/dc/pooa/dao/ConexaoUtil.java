package br.ufscar.dc.pooa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConexaoUtil {
    private final String caminho = "localhost";
    private final String porta = "3306";
    private final String nome = "hotel";
    private String usuario = "root";
    private String senha = "";
    private String url = "jdbc:mysql://" + caminho + ":" + porta + "/" + nome+"?useTimezone=true&serverTimezone=UTC";
    private static ConexaoUtil conexaoUtil = null;

    private ConexaoUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver JDBC");
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
        return DriverManager.getConnection(url, usuario, senha);

    }

}
