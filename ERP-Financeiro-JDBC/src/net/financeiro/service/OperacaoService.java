package net.financeiro.service;

import net.financeiro.exceptions.AtributoInvalidoException;
import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Operacao;
import net.financeiro.repository.OperacaoRepository;

import java.util.List;

public class OperacaoService {
    private final OperacaoRepository repository = new OperacaoRepository();

    public Operacao inserir(Operacao op){
        try{
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
        }catch(AtributoInvalidoException | FkNaoEncontradaException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Operacao atualizar(Operacao op){
        try{
            if(repository.buscarPorId(op.getId_operacao()) == null){
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
            return op;
        }catch(IdNaoEncontradoException | AtributoInvalidoException | FkNaoEncontradaException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Operacao> listarInfo(){
        try{
            List<Operacao> lista = repository.listarInfo();
            if(lista.isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        }catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        try{
            if(repository.buscarPorId(id) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return true;
        }catch(IdNaoEncontradoException | NadaInseridoException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
