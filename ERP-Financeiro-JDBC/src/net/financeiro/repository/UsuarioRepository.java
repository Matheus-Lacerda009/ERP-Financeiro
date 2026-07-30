package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.PermissaoNegadaException;
import net.financeiro.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public boolean cadastrando(Usuario ins) throws SQLException {
        String sql = "insert into Usuarios(nome, senha, permissao) values (?, ?, ?)";
        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setString(1, ins.getNome());
        pr.setString(2, BCrypt.hashpw(ins.getSenha(), BCrypt.gensalt()));
        pr.setInt(3, ins.getPermissao());
        if(pr.executeUpdate() != 0){
            return true;
        }
        pr.close();
        return false;
    }

    public boolean isAdm(Usuario adm) throws SQLException {
        String sqlVal = "select id, senha from Usuarios where nome = ?";
        PreparedStatement prVal = Conexao.connecting().prepareStatement(sqlVal);
        prVal.setString(1, adm.getNome());
        ResultSet rs = prVal.executeQuery();
        rs.next();
        boolean resultado = rs.getLong("id") == 1L && BCrypt.checkpw(adm.getSenha(), rs.getString("senha"));
        prVal.close();
        return resultado;
    }

    public boolean validacao(Usuario user) throws SQLException {
        String sql = "select senha from Usuarios where nome = ?";
        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setString(1, user.getNome());
        ResultSet rs = pr.executeQuery();
        rs.next();
        boolean resultado = BCrypt.checkpw(user.getSenha(), rs.getString("senha"));
        pr.close();
        return resultado;
    }
}
