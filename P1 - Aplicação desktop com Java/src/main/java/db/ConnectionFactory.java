package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar conexões com o banco
 */
public abstract class ConnectionFactory {
    private static final String dbPath = "src/main/resources/banco_eleicao";
    private static Connection cnx;

    public static Connection getConnection() {
            try {

                Class.forName("org.sqlite.JDBC");
                cnx = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return cnx;
    }
}
