package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.model.Itens_Operacao;
import net.financeiro.repository.Itens_OperacaoRepository;

public class Itens_OperacaoService {
    private final Itens_OperacaoRepository repository = new Itens_OperacaoRepository();

    public Itens_Operacao inserir(Itens_Operacao ins) {
        try {
            //todo Finalizar verificação regras de negócio
            return repository.inserir(ins);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Itens_Operacao atualizar(Itens_Operacao atl) {
        try {
            //todo Finalizar verificação regras de negócio
            if(repository.buscarPorId(atl.getId_itens_operacao()) == null) {
                throw new IdNaoEncontradoException("Erro: ID não encontrado!");
            }

            return repository.atualizar(atl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id) {
        try {
            if(repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: ID não encontrado!");
            }
            return repository.deletar(id);
        } catch(IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Itens_Operacao buscarPorId(Long id) {
        try {
            Itens_Operacao io = repository.buscarPorId(id);

            if(io == null) {
                throw new IdNaoEncontradoException("ERRO: ID não encontrado!");
            }
            return io;
        } catch(IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
