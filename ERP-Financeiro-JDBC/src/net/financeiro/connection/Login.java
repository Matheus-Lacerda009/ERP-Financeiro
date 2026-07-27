package net.financeiro.connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static boolean validacao(String nome, String senha){
        String sql = "select * from Usuarios where nome = ? && senha = sha2(?, 256)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, nome);
            pr.setString(2, senha);
            ResultSet rs = pr.executeQuery();
            return rs.next();
        } catch(SQLException e){
            System.out.println("Erro ao logar: " + e.getMessage());
            return false;
        }
    }
}
