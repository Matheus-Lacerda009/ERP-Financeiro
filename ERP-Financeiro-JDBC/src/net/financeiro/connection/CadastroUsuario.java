package net.financeiro.connection;

import net.financeiro.exceptions.PermissaoNegadaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroUsuario {
    public boolean cadastrando(String usuarioAdm, String senhaAdm, String nome, String senha, int permissao){
        String sqlVal = "select id from Usuarios where nome = ? && senha = sha2(?, 256)";
        String sql = "insert into Usuarios(nome, senha, permissao) values (?, sha2(?, 256), ?)";
        try(PreparedStatement prVal = Conexao.connecting().prepareStatement(sqlVal);
            PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            prVal.setString(1, usuarioAdm);
            prVal.setString(2, senhaAdm);
            ResultSet rs = prVal.executeQuery();
            rs.next();
            if(rs.getLong("id") == 1L){
                pr.setString(1, nome);
                pr.setString(2, senha);
                pr.setInt(3, permissao);
                pr.executeUpdate();
                return true;
            } else {
                throw new PermissaoNegadaException("Erro: você não tem permissão para criar usuários!");
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        } catch(PermissaoNegadaException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
