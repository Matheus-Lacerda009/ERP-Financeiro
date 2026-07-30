package net.financeiro.service;

import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Produto;
import net.financeiro.repository.Categoria_ItemRepository;
import net.financeiro.repository.ProdutosRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ProdutosService {

    private ProdutosRepository repository = new ProdutosRepository();
    private Categoria_ItemRepository categoriaItemRepository = new Categoria_ItemRepository();

    public Produto inserir(Produto ins) throws ValorInvalidoException, FkNaoEncontradaException, SQLException {
        if(ins.getNome().trim().isEmpty()){
            throw new ValorInvalidoException("Erro: nome vazio!");
        }
        if(ins.getValor() < 0){
            throw new ValorInvalidoException("Erro: valor é negativo!");
        }
        if(ins.getQuantidade_estoque() < 0){
            throw new ValorInvalidoException("Erro: quantidade de produtos é negativa");
        }
        if(categoriaItemRepository.buscarPorId(ins.getId_categoria_item()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        return repository.inserir(ins);
    }

    public Produto atualizar(Produto atl, Long id) throws ValorInvalidoException, IdNaoEncontradoException, FkNaoEncontradaException, SQLException {
        if(atl.getNome().trim().isEmpty()){
            throw new ValorInvalidoException("Erro: nome vazio!");
        }
        if(atl.getValor() < 0){
            throw new ValorInvalidoException("Erro: valor é negativo!");
        }
        if(atl.getQuantidade_estoque() < 0){
            throw new ValorInvalidoException("Erro: quantidade de produtos é negativa");
        }
        if(repository.buscarPorId(id) == null){
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if(categoriaItemRepository.buscarPorId(atl.getId_categoria_item()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        return repository.atualizar(atl, id);
    }

    public List<Produto> listarInfo() throws NadaInseridoException, SQLException {
        List<Produto> lista = repository.listarInfo();
        if (lista.isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }

    public boolean deletar(Long id) throws SQLException, IdNaoEncontradoException, NadaInseridoException {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if (repository.listarInfo().isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.deletar(id);
    }

    public boolean reativar(Long id) throws SQLException, NadaInseridoException, IdNaoEncontradoException {
        if (repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if (repository.listarInfo().isEmpty()) {
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.reativar(id);
    }

    public HashMap<String, List<String>> maiorVenda() throws SQLException, NadaInseridoException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.maiorVenda();
    }

    public HashMap<String, List<String>> menorVenda() throws SQLException, NadaInseridoException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.menorVenda();
    }

    public HashMap<String, List<String>> mediaVenda() throws SQLException, NadaInseridoException {
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.mediaVenda();
    }
}
