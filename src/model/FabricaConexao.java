package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FabricaConexao {

    private static Connection[] pool;


    private static String CONNECTION_STR = "jdbc:mysql:"+
            "//infoprojetos.com.br:3132/tads17_siqueira" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String USERNAME = "tads17_siqueira";
    private static String PASSWORD = "12345";

    private static int MAX_CONNECTIONS=5;

    static {
        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{

        for(int i=0;i<pool.length;i++){
            if((pool[i]==null) || (pool[i].isClosed())){

                pool[i] = DriverManager
                        .getConnection(CONNECTION_STR,USERNAME,PASSWORD);

                return pool[i];
            }
        }

        throw new SQLException("Muitas conexões abertas!!!");

    }

}