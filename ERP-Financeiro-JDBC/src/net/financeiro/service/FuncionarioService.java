package net.financeiro.service;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Funcionario;
import net.financeiro.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FuncionarioService {
    private final FuncionarioRepository repository = new FuncionarioRepository();


    public Funcionario inserir(Funcionario ins) {
        try {
            if (ins.getNome() == null || ins.getNome().trim().isEmpty()) {
                throw new ValorInvalidoException("Erro: Nome está vazio");
            }
            if (ins.getCpf() == null  ) {
                throw new ValorInvalidoException("Erro: CPF Invaido ");
            }
            if (ins.getEmail() == null ) {
                throw new ValorInvalidoException("Erro: E-mail Invalido");
            }
            return repository.inserir(ins);
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Funcionario atualizar(Funcionario atl, Long id) {
        try {
            if (atl.getNome() == null || atl.getNome().trim().isEmpty()) {
                throw new ValorInvalidoException("ERRO: Nome inválido, não pode ser vazio");
            } else if (repository.buscarPorId(atl.getId_funcionario()) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.atualizar(atl, id);
        } catch (ValorInvalidoException | IdNaoEncontradoException e) {
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
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.maiorVenda();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> menorVenda(){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.menorVenda();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> mediaVenda(){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.mediaVendas();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}