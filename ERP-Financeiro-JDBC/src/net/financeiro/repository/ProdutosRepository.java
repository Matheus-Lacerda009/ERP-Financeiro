package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutosRepository {
    public Produto inserir(Produto ins){
        String sql = "INSERT INTO Produto (nome, valor, descricao, quantidade_estoque, id_categoria_item) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
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
        } catch(SQLException e){
            System.out.println("Erro ao inserir: " + e.getMessage());
            return null;
        }
    }

    public Produto atualizar(Produto atl){
        String sql = "UPDATE Produto SET nome = ?, valor = ?, descricao = ?, quantidade_estoque = ?, id_categoria_item = ? WHERE id_produto = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, atl.getNome());
            pr.setDouble(2, atl.getValor());
            pr.setString(3, atl.getDescricao());
            pr.setInt(4, atl.getQuantidade_estoque());
            pr.setLong(5, atl.getId_categoria_item());
            pr.setLong(6, atl.getId_produto());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "DELETE FROM Produto WHERE id_produto = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listar(){
        String sql = "SELECT * FROM Produto";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Produto> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Produto(rs.getLong("id_produto"), rs.getLong("id_categoria_item"), rs.getString("nome"), rs.getString("descricao"), rs.getDouble("valor"), rs.getInt("quantidade_estoque")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }
}