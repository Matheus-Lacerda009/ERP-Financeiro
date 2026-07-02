package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Categoria_ItemRepository {
    public Categoria_Item inserir(Categoria_Item ins){
        String sql = "INSERT INTO Categoria_Item (nome) VALUES (?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_categoria_item(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Categoria_Item atualizar(Categoria_Item atl){
        String sql = "UPDATE Categoria_Item SET nome = ? WHERE id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, atl.getNome());
            pr.setLong(2, atl.getId_categoria_item());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "DELETE FROM Categoria_Item WHERE id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public List<Categoria_Item> listarInfo(){
        String sql = "SELECT * FROM Categoria_Item";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Categoria_Item> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Categoria_Item(rs.getLong("id_categoria_item"), rs.getString("nome")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Categoria_Item buscarPorId(Long id_categoria_item){
        String sql = "SELECT * FROM Categoria_Item where id_categoria_item = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_categoria_item);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Categoria_Item(id_categoria_item, rs.getString("nome"));
        } catch(SQLException e){
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }
}
