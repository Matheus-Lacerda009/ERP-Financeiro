package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.PermissaoNegadaException;
import net.financeiro.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public boolean cadastrando(Usuario ins){
        String sql = "insert into Usuarios(nome, senha, permissao) values (?, sha2(?, 256), ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, ins.getNome());
            pr.setString(2, ins.getSenha());
            pr.setInt(3, ins.getPermissao());
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        } catch(PermissaoNegadaException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isAdm(Usuario adm){
        String sqlVal = "select id from Usuarios where nome = ? && senha = sha2(?, 256)";
        try(PreparedStatement prVal = Conexao.connecting().prepareStatement(sqlVal)){
            prVal.setString(1, adm.getNome());
            prVal.setString(2, adm.getSenha());
            ResultSet rs = prVal.executeQuery();
            rs.next();
            if(rs.getLong("id") == 1L){
                return true;
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
        return false;
    }
}
