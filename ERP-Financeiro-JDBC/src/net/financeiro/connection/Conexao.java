package net.financeiro.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {

    static Dotenv env = Dotenv.load();

    private static final String URL = env.get("URL");

    public static Connection connecting(String USER, String PASSWORD){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(SQLException e){
            System.out.println("Erro ao conectar!");
            return null;
        }
    }
}

