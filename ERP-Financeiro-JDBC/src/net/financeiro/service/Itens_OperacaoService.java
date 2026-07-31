package net.financeiro.service;

import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Itens_Operacao;
import net.financeiro.repository.Itens_OperacaoRepository;
import net.financeiro.repository.OperacaoRepository;
import net.financeiro.repository.ProdutosRepository;

import java.sql.SQLException;
import java.util.List;

public class Itens_OperacaoService {
    private final Itens_OperacaoRepository repository = new Itens_OperacaoRepository();
    private final ProdutosRepository produtosRepository = new ProdutosRepository();
    private final OperacaoRepository operacaoRepository = new OperacaoRepository();

    public Itens_Operacao inserir(Itens_Operacao ins) throws NadaInseridoException, FkNaoEncontradaException, SQLException {
        if(ins.getQuantidade_produtos() < 0) {
            throw new NadaInseridoException("Erro: quantidade de produtos negativa!");
        }
        if(produtosRepository.buscarPorId(ins.getId_produto()) == null) {
            throw new FkNaoEncontradaException("Erro: chave estrangeira inválida!");
        }
        if(operacaoRepository.buscarPorId(ins.getId_operacao()) == null) {
            throw new FkNaoEncontradaException("Erro: chave estrangeira inválida!");
        }
        return repository.inserir(ins);
    }

    public Itens_Operacao atualizar(Itens_Operacao atl, Long id) throws NadaInseridoException, FkNaoEncontradaException, IdNaoEncontradoException, SQLException {
        if(atl.getQuantidade_produtos() < 0) {
            throw new NadaInseridoException("Erro: quantidade de produtos negativa!");
        }
        if(produtosRepository.buscarPorId(atl.getId_produto()) == null) {
            throw new FkNaoEncontradaException("Erro: chave estrangeira inválida!");
        }
        if(operacaoRepository.buscarPorId(atl.getId_operacao()) == null) {
            throw new FkNaoEncontradaException("Erro: chave estrangeira inválida!");
        }
        if(repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: ID não encontrado!");
        }

        return repository.atualizar(atl, id);
    }

    public boolean deletar(Long id) throws IdNaoEncontradoException, SQLException {
        if(repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: ID não encontrado!");
        }
        return repository.deletar(id);
    }

    public boolean reativar(Long id) throws IdNaoEncontradoException, SQLException {
        if(repository.buscarPorId(id) == null) {
            throw new IdNaoEncontradoException("Erro: ID não encontrado!");
        }
        return repository.reativar(id);
    }

    public List<Itens_Operacao> listarInfo() throws NadaInseridoException, SQLException {
        List<Itens_Operacao> lista = repository.listarInfo();

        if(lista.isEmpty()) {
            throw new NadaInseridoException("Erro: Nada inserido no banco");
        }

        return lista;
    }

    public Itens_Operacao buscarPorId(Long id) throws IdNaoEncontradoException, SQLException {
        Itens_Operacao io = repository.buscarPorId(id);

        if(io == null) {
            throw new IdNaoEncontradoException("Erro: ID não encontrado!");
        }
        return io;
    }
}
