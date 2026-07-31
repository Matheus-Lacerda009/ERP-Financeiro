package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Itens_Operacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Itens_OperacaoRepository {
    public Itens_Operacao inserir(Itens_Operacao ins) throws SQLException{
        String sql = "INSERT INTO Itens_Operacao (quantidade_produtos, id_produto, id_operacao) VALUES (?, ?, ?)";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pr.setInt(1, ins.getQuantidade_produtos());
        pr.setLong(2, ins.getId_produto());
        pr.setLong(3, ins.getId_operacao());
        pr.executeUpdate();

        ResultSet rs = pr.getGeneratedKeys();
        rs.next();

        ins.setId_itens_operacao(rs.getLong("GENERATED_KEY"));
        pr.close();
        return ins;
    }

    public Itens_Operacao atualizar(Itens_Operacao atl, Long id) throws SQLException {
        String sql = "UPDATE Itens_Operacao SET quantidade_produtos = ?, id_produto = ?, id_operacao = ? WHERE id_itens_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        pr.setInt(1, atl.getQuantidade_produtos());
        pr.setLong(2, atl.getId_produto());
        pr.setLong(3, atl.getId_operacao());
        pr.setLong(4, id);
        pr.executeUpdate();

        return atl;
    }

    public boolean deletar(Long id) throws SQLException {
        String sql = "update Itens_Operacao set ativo = false WHERE id_itens_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        pr.setLong(1, id);
        pr.executeUpdate();

        return true;
    }

    public boolean reativar(Long id) throws SQLException {
        String sql = "update Itens_Operacao set ativo = true WHERE id_itens_operacao = ?";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        pr.setLong(1, id);
        pr.executeUpdate();

        return true;
    }

    public List<Itens_Operacao> listarInfo() throws SQLException {
        String sql = "SELECT Itens_Operacao.*, Produto.nome FROM Itens_Operacao join Produto on Itens_Operacao.id_produto = Produto.id_produto where Itens_Operacao.ativo = true";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        List<Itens_Operacao> lista = new ArrayList<>();
        ResultSet rs = pr.executeQuery();

        while(rs.next()) {
            lista.add(new Itens_Operacao(
                    rs.getLong("id_itens_operacao"),
                    rs.getLong("id_produto"),
                    rs.getLong("id_operacao"),
                    rs.getInt("quantidade_produtos"),
                    rs.getString("Produto.nome")
            ));
        }

        pr.close();
        return lista;
    }

    public Itens_Operacao buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM Itens_Operacao WHERE id_itens_operacao = ? and ativo = true";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);

        pr.setLong(1, id);
        ResultSet rs = pr.executeQuery();

        rs.next();
        return new Itens_Operacao(id, rs.getLong("id_produto"), rs.getLong("id_operacao"), rs.getInt("quantidade_produtos"));
    }
}
