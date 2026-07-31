package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Operacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperacaoRepository {
    public Operacao inserir(Operacao op) throws SQLException{
        String sql = "INSERT INTO Operacao (id_operacao, data_operacao, status_operacao, id_fornecedor_cliente, id_funcionario) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pr.setLong(1, op.getId_operacao());
        pr.setString(2, op.getData_operacao());
        pr.setString(3, op.getStatus_operacao());
        pr.setLong(4, op.getId_fornecedor_cliente());
        pr.setLong(5, op.getId_funcionario());
        pr.executeUpdate();
        ResultSet rs = pr.getGeneratedKeys();
        rs.next();
        op.setId_operacao(rs.getLong("GENERATED_KEY"));
        pr.close();
        return op;
    }

    public Operacao atualizar(Operacao op, Long id) throws SQLException{
        String sql = "UPDATE Operacao SET id_operacao = ?, data_operacao = ?, status_operacao = ?, id_fornecedor_cliente = ?, id_funcionario = ? WHERE id_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setLong(1, op.getId_operacao());
        pr.setString(2, op.getData_operacao());
        pr.setString(3, op.getStatus_operacao());
        pr.setLong(4, op.getId_fornecedor_cliente());
        pr.setLong(5, op.getId_funcionario());
        pr.setLong(6, id);
        pr.executeUpdate();
        pr.close();
        return op;
    }

    public boolean deletar(Long id_digitado) throws SQLException{
        String sql = "update Operacao set ativo = false WHERE id_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setLong(1, id_digitado);
        pr.executeUpdate();
        pr.close();
        return true;
    }

    public boolean reativar(Long id_digitado) throws SQLException{
        String sql = "update Operacao set ativo = true WHERE id_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setLong(1, id_digitado);
        pr.executeUpdate();
        pr.close();
        return true;
    }

    public List<Operacao> listarInfo() throws SQLException{
        String sql = "SELECT * FROM Operacao where ativo = true";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        List<Operacao> lista = new ArrayList<>();
        ResultSet rs = pr.executeQuery();
        while(rs.next()){
            lista.add(new Operacao(rs.getLong("id_operacao"), rs.getLong("id_fornecedor_cliente"), rs.getLong("id_funcionario"), rs.getString("data_operacao"), rs.getString("status_operacao")));
        }
        pr.close();
        return lista;
    }

    public Operacao buscarPorId(Long id_digitado) throws SQLException{
        String sql = "SELECT * FROM Operacao WHERE id_Operacao = ? and ativo = true";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        pr.setLong(1, id_digitado);
        ResultSet rs = pr.executeQuery();
        rs.next();
        pr.close();
        return new Operacao(id_digitado, rs.getLong("id_fornecedor_cliente"), rs.getLong("id_funcionario"), rs.getString("data_operacao"), rs.getString("status_operacao"));
    }
}
