package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static Connection[] pool = new Connection[10];

    public static Connection getConnection() throws SQLException {

        for (int i = 0; i < pool.length; i++) {
            if (pool[i] == null || pool[i].isClosed()) {
                //pool[i] = DriverManager.getConnection(URL_BANCO);
                pool[i] = DriverManager.getConnection("jdbc:mysql://infoprojetos.com.br:3132/tads17_siqueira?useLegacyDatetimeCode=false&serverTimezone=UTC","tads17_siqueira","12345");
                return pool[i];
            }
        }

        throw new SQLException("Sem conexão disponíveis!!!");
    }
}