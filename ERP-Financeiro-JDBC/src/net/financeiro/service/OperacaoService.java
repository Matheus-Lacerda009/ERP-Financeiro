package net.financeiro.service;

import net.financeiro.exceptions.AtributoInvalidoException;
import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.model.Operacao;
import net.financeiro.repository.OperacaoRepository;

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
}
