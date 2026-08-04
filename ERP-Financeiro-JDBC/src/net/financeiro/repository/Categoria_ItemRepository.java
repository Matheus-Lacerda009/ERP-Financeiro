package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categoria_ItemRepository {

    public Categoria_Item inserir(Categoria_Item ins) throws SQLException {
        String sql = "INSERT INTO Categoria_Item (nome) VALUES (?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_categoria_item(rs.getLong("GENERATED_KEY"));
            return ins;
        }
    }

    public Categoria_Item atualizar(Categoria_Item atl, Long id) throws SQLException {
        String sql = "UPDATE Categoria_Item SET nome = ? WHERE id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setString(1, atl.getNome());
            pr.setLong(2, id);
            pr.executeUpdate();
            return atl;
        }
    }

    public boolean deletar(Long id) throws SQLException {
        String sql = "update Categoria_Item set ativo = false WHERE id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public boolean reativar(Long id) throws SQLException {
        String sql = "update Categoria_Item set ativo = true WHERE id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public List<Categoria_Item> listarInfo() throws SQLException {
        String sql = "SELECT * FROM Categoria_Item where ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            List<Categoria_Item> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                lista.add(new Categoria_Item(rs.getLong("id_categoria_item"), rs.getString("nome")));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> maiorVenda() throws SQLException {
        String sql = "select sum(\n" +
                "                        p.valor * io.quantidade_produtos\n" +
                "                    ) as VendaPorCategoria, ci.nome as NomeCategoria, ci.ativo\n" +
                "                from\n" +
                "                    Produto as p\n" +
                "                    join Itens_Operacao as io on io.id_produto = p.id_produto\n" +
                "                    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item\n" +
                "                    where ci.ativo = 1\n" +
                "                group by\n" +
                "                    ci.id_categoria_item\n" +
                "                ORDER BY VendaPorCategoria desc;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeCategoria").add(rs.getString("NomeCategoria"));
                lista.get("VendaCategoria").add(rs.getString("VendaPorCategoria"));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> menorVenda() throws SQLException {
        String sql = "select sum(\n" +
                "                        p.valor * io.quantidade_produtos\n" +
                "                    ) as VendaPorCategoria, ci.nome as NomeCategoria, ci.ativo\n" +
                "                from\n" +
                "                    Produto as p\n" +
                "                    join Itens_Operacao as io on io.id_produto = p.id_produto\n" +
                "                    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item\n" +
                "                    where ci.ativo = 1\n" +
                "                group by\n" +
                "                    ci.id_categoria_item\n" +
                "                ORDER BY VendaPorCategoria asc;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeCategoria").add(rs.getString("NomeCategoria"));
                lista.get("VendaCategoria").add(rs.getString("VendaPorCategoria"));
            }
            return lista;
        }
    }

    public HashMap<String, List<String>> mediaVenda() throws SQLException {
        String sql = "select avg(\n" +
                "                        p.valor * io.quantidade_produtos\n" +
                "                    ) as VendaPorCategoria, ci.nome as NomeCategoria, ci.ativo\n" +
                "                from\n" +
                "                    Produto as p\n" +
                "                    join Itens_Operacao as io on io.id_produto = p.id_produto\n" +
                "                    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item\n" +
                "                    where ci.ativo = 1\n" +
                "                group by\n" +
                "                    ci.id_categoria_item\n" +
                "                ORDER BY VendaPorCategoria desc;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while (rs.next()) {
                lista.get("NomeCategoria").add(rs.getString("NomeCategoria"));
                lista.get("VendaCategoria").add(rs.getString("VendaPorCategoria"));
            }
            return lista;
        }
    }

    public Categoria_Item buscarPorId(Long id_categoria_item) throws SQLException {
        String sql = "SELECT * FROM Categoria_Item where id_categoria_item = ? and ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id_categoria_item);
            ResultSet rs = pr.executeQuery();
            if(rs.next()) {
                return new Categoria_Item(id_categoria_item, rs.getString("nome"));
            }
            return null;
        }
    }
}
