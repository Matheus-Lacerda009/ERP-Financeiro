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
    }

    public Funcionario atualizar(Funcionario atl, Long id) {
        if (atl.getNome() == null || atl.getNome().trim().isEmpty()) {
            throw new NadaInseridoException("ERRO: Nome inválido, não pode ser vazio");
        } else if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
        }
        return repository.atualizar(atl, id);
    }

    public List<Funcionario> listarInfo() {
        List<Funcionario> lista = repository.listarInfo();
        if (lista == null || lista.isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }

    public boolean deletar(Long id) {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
        }
        return repository.deletar(id);
    }

    public boolean reativar(Long id) {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
        }
        return repository.reativar(id);
    }

    public HashMap<String, List<String>> maiorVenda(){
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.maiorVenda();
    }

    public HashMap<String, List<String>> menorVenda(){
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.menorVenda();
    }

    public HashMap<String, List<String>> mediaVenda(){
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.mediaVenda();
    }
}