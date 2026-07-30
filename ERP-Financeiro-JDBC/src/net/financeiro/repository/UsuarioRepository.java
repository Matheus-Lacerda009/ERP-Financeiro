package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.PermissaoNegadaException;
import net.financeiro.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public boolean cadastrando(Usuario ins) throws SQLException {
        String sql = "insert into Usuarios(nome, senha, permissao) values (?, sha2(?, 256), ?)";
        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setString(1, ins.getNome());
        pr.setString(2, ins.getSenha());
        pr.setInt(3, ins.getPermissao());
        if(pr.executeUpdate() != 0){
            return true;
        }
        return false;
    }

    public boolean isAdm(Usuario adm) throws SQLException {
        String sqlVal = "select id from Usuarios where nome = ? && senha = sha2(?, 256)";
        PreparedStatement prVal = Conexao.connecting().prepareStatement(sqlVal);
        prVal.setString(1, adm.getNome());
        prVal.setString(2, adm.getSenha());
        ResultSet rs = prVal.executeQuery();
        rs.next();
        prVal.close();
        if (rs.getLong("id") == 1L) {
            return true;
        }
        return false;
    }
}
