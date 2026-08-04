package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Fornecedor_Cliente;
import net.financeiro.repository.FornecedorRepository;

import java.util.HashMap;
import java.util.List;

public class FornecedorService {
    private final FornecedorRepository repository = new FornecedorRepository();

    public Fornecedor_Cliente inserir(Fornecedor_Cliente ins) {
        if (ins.getRazao_social_nome() == null || ins.getRazao_social_nome().trim().isEmpty()) {
            throw new NadaInseridoException("Erro: Nome/Razão Social está vazio");
        }
        return repository.inserir(ins);
    }

    public Fornecedor_Cliente atualizar(Fornecedor_Cliente atl, Long id) {
        if (atl.getRazao_social_nome() == null || atl.getRazao_social_nome().trim().isEmpty()) {
            throw new NadaInseridoException("ERRO: Nome/Razão Social inválido, não pode ser vazio");
        } else if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
        }
        return repository.atualizar(atl, id);
    }

    public List<Fornecedor_Cliente> listarInfo() {
        List<Fornecedor_Cliente> lista = repository.listarInfo();
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