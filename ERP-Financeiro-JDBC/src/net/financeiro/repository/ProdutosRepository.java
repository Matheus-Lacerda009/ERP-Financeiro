package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProdutosRepository {
    public Produto inserir(Produto ins) throws SQLException {
        String sql = "INSERT INTO Produto (nome, valor, descricao, quantidade_estoque, id_categoria_item) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, ins.getNome());
            pr.setDouble(2, ins.getValor());
            pr.setString(3, ins.getDescricao());
            pr.setInt(4, ins.getQuantidade_estoque());
            pr.setLong(5, ins.getId_categoria_item());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_produto(rs.getLong("GENERATED_KEY"));
            return ins;
        }
    }

    public Produto atualizar(Produto atl, Long id) throws SQLException {
        String sql = "UPDATE Produto SET nome = ?, valor = ?, descricao = ?, quantidade_estoque = ?, id_categoria_item = ? WHERE id_produto = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setString(1, atl.getNome());
            pr.setDouble(2, atl.getValor());
            pr.setString(3, atl.getDescricao());
            pr.setInt(4, atl.getQuantidade_estoque());
            pr.setLong(5, atl.getId_categoria_item());
            pr.setLong(6, id);
            pr.executeUpdate();
            return atl;
        }
    }

    public boolean deletar(Long id) throws SQLException {
        String sql = "update Produto set ativo = false WHERE id_produto = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public boolean reativar(Long id) throws SQLException {
        String sql = "update Produto set ativo = true WHERE id_produto = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public List<Produto> listarInfo() throws SQLException {
        String sql = "SELECT Produto.*, Categoria_Item.nome FROM Produto join Categoria_Item on Categoria_Item.id_categoria_item = Produto.id_categoria_item where Produto.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            List<Produto> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                lista.add(new Produto(rs.getLong("id_produto"), rs.getLong("id_categoria_item"), rs.getString("Produto.nome"), rs.getString("descricao"), rs.getDouble("valor"), rs.getInt("quantidade_estoque"), rs.getString("Categoria_Item.nome")));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> maiorVenda() throws SQLException {
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as VendaPorProduto, p.nome as NomeProduto\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "where p.ativo = true" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY VendaPorProduto desc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeProduto").add(rs.getString("NomeProduto"));
                lista.get("VendaProduto").add(rs.getString("VendaPorProduto"));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> menorVenda() throws SQLException {
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as VendaPorProduto, p.nome as NomeProduto\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "where p.ativo = true" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY VendaPorProduto asc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeProduto").add(rs.getString("Nome produto"));
                lista.get("VendaProduto").add(rs.getString("Venda por produto"));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> mediaVenda() throws SQLException {
        String sql = "SELECT avg(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as VendaPorProduto, p.nome as NomeProduto\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "where p.ativo = true" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY VendaPorProduto desc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeProduto").add(rs.getString("NomeProduto"));
                lista.get("VendaProduto").add(rs.getString("VendaPorProduto"));
            }
            return lista;
        }
    }

    public Produto buscarPorId(Long id) throws SQLException {
        String sql = "select * from Produto where id = ? where Produto.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Produto(rs.getLong("id_produto"), rs.getLong("id_categoria_item"), rs.getString("nome"), rs.getString("descricao"), rs.getDouble("valor"), rs.getInt("quantidade_estoque"));
        }
    }
}