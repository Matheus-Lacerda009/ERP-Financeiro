package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection connecting(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(SQLException e){
            return null;
        }
    }
}

