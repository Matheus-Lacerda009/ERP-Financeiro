package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Fornecedor_Cliente;
import net.financeiro.repository.Fornecedor_ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Fornecedor_ClienteService {
    private final Fornecedor_ClienteRepository repository = new Fornecedor_ClienteRepository();

    public Fornecedor_Cliente inserir(Fornecedor_Cliente ins) {
        try {
            if (ins.getRazao_social_nome() == null || ins.getRazao_social_nome().trim().isEmpty()) {
                throw new NomeInvalidoException("Erro: Nome/Razão Social está vazio");
            }
            return repository.inserir(ins);
        } catch (NomeInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Fornecedor_Cliente atualizar(Fornecedor_Cliente atl, Long id) {
        try {
            if (atl.getRazao_social_nome() == null || atl.getRazao_social_nome().trim().isEmpty()) {
                throw new NomeInvalidoException("ERRO: Nome/Razão Social inválido, não pode ser vazio");
            } else if (repository.buscarPorId(atl.getId_fornecedor_cliente()) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.atualizar(atl, id);
        } catch (NomeInvalidoException | IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Fornecedor_Cliente> listarInfo() {
        List<Fornecedor_Cliente> lista = repository.listarInfo();
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