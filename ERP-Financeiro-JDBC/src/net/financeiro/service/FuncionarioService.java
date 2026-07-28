package net.financeiro.service;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Funcionario;
import net.financeiro.repository.FuncionarioRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class FuncionarioService {
    private final FuncionarioRepository repository = new FuncionarioRepository();


    public Funcionario inserir(Funcionario ins) {
        try {
            if (ins.getNome() == null || ins.getNome().trim().isEmpty()) {
                throw new NadaInseridoException("Erro: Nome está vazio");
            }
            if (ins.getCpf() == null  ) {
                throw new NadaInseridoException("Erro: CPF Invaido ");
            }
            if (ins.getEmail() == null ) {
                throw new NadaInseridoException("Erro: E-mail Invalido");
            }
            return repository.inserir(ins);
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Funcionario atualizar(Funcionario atl) {
        try {
            if (atl.getNome() == null || atl.getNome().trim().isEmpty()) {
                throw new NadaInseridoException("ERRO: Nome inválido, não pode ser vazio");
            } else if (repository.buscarPorId(atl.getId_funcionario()) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.atualizar(atl);
        } catch (NadaInseridoException | IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Funcionario> listarInfo() {
        List<Funcionario> lista = repository.listarInfo();
        try {
            if (lista == null || lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id) {
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.deletar(id);
        } catch (IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return false;
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

    public HashMap<String, List<String>> mediaVendas(){
        String sql = "SELECT avg(\n" +
                "        p.valor * i.quantidade_produtos\n" +
                "    ) as 'Média Venda por funcionários', f.nome as 'Nome funcionario'\n" +
                "from\n" +
                "    Produto as p\n" +
                "    join `Itens_Operacao` as i on i.id_produto = p.id_produto\n" +
                "    join `Operacao` as op on op.id_operacao = i.id_operacao\n" +
                "    join `Funcionario` as f on f.id_funcionario = op.id_funcionario\n" +
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