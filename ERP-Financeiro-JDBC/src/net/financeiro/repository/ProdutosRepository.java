package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Produto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
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

    public Produto atualizar(Produto atl, Long id){
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

    public List<Produto> listarInfo(){
        String sql = "SELECT Produto.*, Categoria_Item.nome FROM Produto join Categoria_Item on Categoria_Item.id_categoria_item = Produto.id_categoria_item";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Produto> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Produto(rs.getLong("id_produto"), rs.getLong("id_categoria_item"), rs.getString("Produto.nome"), rs.getString("descricao"), rs.getDouble("valor"), rs.getInt("quantidade_estoque"), rs.getString("Categoria_Item.nome")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> maiorVenda(){
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Venda por produto', p.nome as 'Nome produto'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) desc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while(rs.next()){
                lista.get("NomeProduto").add(rs.getString("Nome produto"));
                lista.get("VendaProduto").add(rs.getString("Venda por produto"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> menorVenda(){
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Venda por produto', p.nome as 'Nome produto'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while(rs.next()){
                lista.get("NomeProduto").add(rs.getString("Nome produto"));
                lista.get("VendaProduto").add(rs.getString("Venda por produto"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> mediaVendas(){
        String sql = "SELECT avg(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Média Venda por produto', p.nome as 'Nome produto'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "GROUP BY\n" +
                "    p.id_produto\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeProduto", nomeCategoria);
            lista.put("VendaProduto", vendaCategoria);
            while(rs.next()){
                lista.get("NomeProduto").add(rs.getString("Nome produto"));
                lista.get("VendaProduto").add(rs.getString("Média Venda por produto"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Produto buscarPorId(Long id){
        String sql = "select * from Produto where id = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                return new Produto(rs.getLong("id_produto"), rs.getLong("id_categoria_item"), rs.getString("nome"), rs.getString("descricao"), rs.getDouble("valor"), rs.getInt("quantidade_estoque"));
            } else {
                return null;
            }
        } catch(SQLException e){
            System.out.println("Erro na busca por ID: " + e.getMessage());
            return null;
        }
    }
}