package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.repository.Categoria_ItemRepository;
import net.financeiro.repository.Itens_OperacaoRepository;
import net.financeiro.repository.ProdutosRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Categoria_ItemService {

    private final Categoria_ItemRepository repository = new Categoria_ItemRepository();
    private final Itens_OperacaoRepository itensOperacaoRepository = new Itens_OperacaoRepository();
    private final ProdutosRepository produtosRepository = new ProdutosRepository();

    public Categoria_Item inserir(Categoria_Item ins) throws ValorInvalidoException, SQLException {
        if(ins.getNome().trim().isEmpty()){
            throw new ValorInvalidoException("Erro: nome vazio!");
        }
        return repository.inserir(ins);
    }

    public Categoria_Item atualizar(Categoria_Item atl, Long id) throws ValorInvalidoException, IdNaoEncontradoException, SQLException {
        if(atl.getNome().trim().isEmpty()){
            throw new ValorInvalidoException("Erro: nome vazio!");
        }
        if(repository.buscarPorId(atl.getId_categoria_item()) == null){
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        return repository.atualizar(atl, id);
    }

    public List<Categoria_Item> listarInfo() throws NadaInseridoException, SQLException {
        List<Categoria_Item> lista = repository.listarInfo();
        if (lista.isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }

    public boolean deletar(Long id) throws IdNaoEncontradoException, NadaInseridoException, SQLException {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if (repository.listarInfo().isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        if(repository.deletar(id)){
            System.out.println("Deletado com sucesso!");
        }
        return repository.deletar(id);
    }

    public boolean reativar(Long id) throws IdNaoEncontradoException, NadaInseridoException, SQLException {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if (repository.listarInfo().isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        if(repository.reativar(id)){
            System.out.println("Reativado com sucesso!");
        }
        return repository.reativar(id);
    }

    public HashMap<String, List<String>> maiorVenda() throws NadaInseridoException, SQLException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.maiorVenda();
    }

    public HashMap<String, List<String>> menorVenda() throws NadaInseridoException, SQLException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.menorVenda();
    }

    public HashMap<String, List<String>> mediaVenda() throws NadaInseridoException, SQLException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.mediaVenda();
    }
}
