package main.net.financeiro.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {

    static Dotenv env = Dotenv.load();

    private static final String URL = env.get("URL");
    private static final String USER = env.get("USER");
    private static final String PASSWORD = env.get("PASSWORD");

    public static Connection connecting(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(SQLException e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}

