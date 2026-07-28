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

    public boolean reativar(Long id) {
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.reativar(id);
        } catch (IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}