package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Forma_Pagamento;
import net.financeiro.repository.Folha_PagamentoRepository;
import net.financeiro.repository.Forma_PagamentoRepository;

import java.util.List;

public class Folha_PagamentoService {
    private final Forma_PagamentoRepository repository = new Forma_PagamentoRepository();

    private Forma_Pagamento inserir(Forma_Pagamento ins) throws NomeInvalidoException {
        try{
            if(ins.getNome().trim().isEmpty()){
                throw  new NomeInvalidoException("Erro : Nome esta vazio");
            }
            return repository.inserir(ins);

        }catch (NomeInvalidoException e){
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public  Forma_Pagamento atualizar(Forma_Pagamento atl) {
        try{
            if(atl.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("ERRO : Nome invalido nao pode ser vazio");
            }else if(repository.buscarPorId(atl.getId_forma_pagamento()) == null){
                throw  new IdNaoEncontradoException("ERRO : Id nao encontrado");
            }
            return repository.atualizar(atl);
        }catch (NomeInvalidoException e){
            System.out.println(e.getMessage());
            return  null;
        }catch (IdNaoEncontradoException e){
            e.getMessage();
            return  null;
        }
    }

    public List<Forma_Pagamento> listarInfo(){
        try{
            List<Forma_Pagamento> lista = repository.listarInfo();
            if (lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return  lista;
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public  boolean deletar(Long Id){
        try {
            if(repository.buscarPorId(Id) == null){
                throw new IdNaoEncontradoException("ERRO : Id delet nao encontrado ");
            }if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return  true;

        }catch (IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  false;

        }catch (NadaInseridoException e){
            System.out.println(e.getMessage());
            return  false;
        }
    }
}
