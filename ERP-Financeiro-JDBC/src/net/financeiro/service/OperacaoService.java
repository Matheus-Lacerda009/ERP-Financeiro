package net.financeiro.service;

import net.financeiro.exceptions.AtributoInvalidoException;
import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Operacao;
import net.financeiro.repository.OperacaoRepository;

import java.sql.SQLException;
import java.util.List;

public class OperacaoService {
    private final OperacaoRepository repository = new OperacaoRepository();

    public Operacao inserir(Operacao op) throws AtributoInvalidoException, FkNaoEncontradaException, SQLException {
        if(!op.getStatus_operacao().equals("Pendente") || !op.getStatus_operacao().equals("Concluída")){
            throw new AtributoInvalidoException("Erro: atributo inválido!");
        }
        if(repository.buscarPorId(op.getId_fornecedor_cliente()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        if(repository.buscarPorId(op.getId_funcionario()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        return repository.inserir(op);
    }

    public Operacao atualizar(Operacao op, Long id) throws IdNaoEncontradoException, FkNaoEncontradaException, SQLException {
        if(repository.buscarPorId(id) == null){
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if(!op.getStatus_operacao().equals("Pendente") || !op.getStatus_operacao().equals("Concluída")){
            throw new AtributoInvalidoException("Erro: atributo inválido!");
        }
        if(repository.buscarPorId(op.getId_fornecedor_cliente()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        if(repository.buscarPorId(op.getId_funcionario()) == null){
            throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
        }
        return repository.atualizar(op, id);
    }

    public List<Operacao> listarInfo() throws NadaInseridoException, SQLException {
        List<Operacao> lista = repository.listarInfo();
        if(lista.isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }

    public boolean deletar(Long id) throws IdNaoEncontradoException, NadaInseridoException, SQLException {
        if(repository.buscarPorId(id) == null){
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.deletar(id);
    }

    public boolean reativar(Long id) throws IdNaoEncontradoException, NadaInseridoException, SQLException {
        if(repository.buscarPorId(id) == null){
            throw new IdNaoEncontradoException("Erro: id não encontrado!");
        }
        if(repository.listarInfo().isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return repository.reativar(id);
    }
}
