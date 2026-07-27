package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Fornecedor_Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fornecedor_ClienteRepository {

    public Fornecedor_Cliente inserir(Fornecedor_Cliente ins){
        String sql = "INSERT INTO Fornecedor_Cliente (razao_social_nome, cnpj_cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getRazao_social_nome());
            pr.setString(2, ins.getCnpj_cpf());
            pr.setString(3, ins.getTelefone());
            pr.setString(4, ins.getEmail());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_fornecedor_cliente(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("ERRO ao inserir : " + e.getMessage());
            return null;
        }
    }

    public Fornecedor_Cliente atualizar(Fornecedor_Cliente alt){
        String sql = "UPDATE Fornecedor_Cliente SET razao_social_nome = ?, cnpj_cpf = ?, telefone = ?, email = ? WHERE id_fornecedor_cliente = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, alt.getRazao_social_nome());
            pr.setString(2, alt.getCnpj_cpf());
            pr.setString(3, alt.getTelefone());
            pr.setString(4, alt.getEmail());
            pr.setLong(5, alt.getId_fornecedor_cliente());
            pr.executeUpdate();
            return alt;
        } catch(SQLException e){
            System.out.println("ERRO ao atualizar : " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "DELETE FROM Fornecedor_Cliente WHERE id_fornecedor_cliente = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("ERRO ao deletar : " + e.getMessage());
            return false;
        }
    }

    public List<Fornecedor_Cliente> listarInfo(){
        String sql = "SELECT * FROM Fornecedor_Cliente";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Fornecedor_Cliente> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Fornecedor_Cliente(
                        rs.getLong("id_fornecedor_cliente"),
                        rs.getString("razao_social_nome"),
                        rs.getString("cnpj_cpf"),
                        rs.getString("telefone"),
                        rs.getString("email")
                ));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("ERRO ao listar : " + e.getMessage());
            return null;
        }
    }

    public Fornecedor_Cliente buscarPorId(Long id_fornecedor_cliente){
        String sql = "SELECT * FROM Fornecedor_Cliente WHERE id_fornecedor_cliente = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_fornecedor_cliente);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Fornecedor_Cliente(
                    id_fornecedor_cliente,
                    rs.getString("razao_social_nome"),
                    rs.getString("cnpj_cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
            );
        } catch(SQLException e){
            System.out.println("ERRO de busca : " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> maiorVenda(){
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente\n" +
                "GROUP BY\n" +
                "    fc.id_fornecedor_cliente\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) desc;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while(rs.next()){
                lista.get("NomeCategoria").add(rs.getString("Nome categoria"));
                lista.get("VendaCategoria").add(rs.getString("Venda por categoria"));
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
                "    ) as 'Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente '\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente\n" +
                "GROUP BY\n" +
                "    fc.id_fornecedor_cliente\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc;\n";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while(rs.next()){
                lista.get("NomeCategoria").add(rs.getString("Nome categoria"));
                lista.get("VendaCategoria").add(rs.getString("Venda por categoria"));
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
                "    ) as 'Média Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente '\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente\n" +
                "GROUP BY\n" +
                "    fc.id_fornecedor_cliente\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc;\n";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            ResultSet rs = pr.executeQuery();
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> nomeCategoria = new ArrayList<>();
            List<String> vendaCategoria = new ArrayList<>();
            lista.put("NomeCategoria", nomeCategoria);
            lista.put("VendaCategoria", vendaCategoria);
            while(rs.next()){
                lista.get("NomeCategoria").add(rs.getString("Nome categoria"));
                lista.get("VendaCategoria").add(rs.getString("Média Venda por categoria"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }
}