package net.financeiro.repository;

import net.financeiro.model.Itens_Operacao;

import java.sql.Connection;

public class Itens_OperacaoRepository {

    public Itens_Operacao buscarPorId(){
        return new Itens_Operacao(1L, 1L, 1);
    }
}
