package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuncionarioRepository {

    public Funcionario inserir(Funcionario ins){
        String sql = "INSERT INTO Funcionario (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome());
            pr.setString(2, ins.getCpf());
            pr.setString(3, ins.getTelefone());
            pr.setString(4, ins.getEmail());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if(rs.next()){
                ins.setId_funcionario(rs.getLong(1));
            }
            return ins;
        } catch(SQLException e){
            System.out.println("ERRO ao inserir : " + e.getMessage());
            return null;
        }
    }

    public Funcionario atualizar(Funcionario alt, Long id){
        String sql = "UPDATE Funcionario SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id_funcionario = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, alt.getNome());
            pr.setString(2, alt.getCpf());
            pr.setString(3, alt.getTelefone());
            pr.setString(4, alt.getEmail());
            pr.setLong(5, id);
            pr.executeUpdate();
            return alt;
        } catch(SQLException e){
            System.out.println("ERRO ao atualizar : " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "update Funcionario set ativo = false WHERE id_funcionario = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        } catch(SQLException e){
            System.out.println("ERRO ao deletar : " + e.getMessage());
            return false;
        }
    }

    public boolean reativar(Long id){
        String sql = "update Funcionario set ativo = true WHERE id_funcionario = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        } catch(SQLException e){
            System.out.println("ERRO ao reativar : " + e.getMessage());
            return false;
        }
    }

    public List<Funcionario> listarInfo(){
        String sql = "SELECT * FROM Funcionario where ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Funcionario> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Funcionario(
                        rs.getLong("id_funcionario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
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

    public Funcionario buscarPorId(Long id_funcionario){
        String sql = "SELECT * FROM Funcionario WHERE id_funcionario = ? and ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_funcionario);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                return new Funcionario(
                        id_funcionario,
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
            }
            return null;
        } catch(SQLException e){
            System.out.println("ERRO de busca : " + e.getMessage());
            return null;
        }
    }
    public HashMap<String, List<String>> maiorVenda(){
        String sql = "SELECT sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Venda por funcionários', f.nome as 'Nome funcionario'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Funcionario` as f on f.id_funcionario = op.id_funcionario\n" +
                "where f.ativo = true" +
                "GROUP BY\n" +
                "    f.id_funcionario\n" +
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
                "    ) as 'Venda por funcionários', f.nome as 'Nome funcionario'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Funcionario` as f on f.id_funcionario = op.id_funcionario\n" +
                "where f.ativo = true" +
                "GROUP BY\n" +
                "    f.id_funcionario\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc;";
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

    public HashMap<String, List<String>> mediaVenda(){
        String sql = "SELECT avg(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Média Venda por funcionários', f.nome as 'Nome funcionario'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Funcionario` as f on f.id_funcionario = op.id_funcionario\n" +
                "where f.ativo = true" +
                "GROUP BY\n" +
                "    f.id_funcionario\n" +
                "ORDER BY sum(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) asc;";
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