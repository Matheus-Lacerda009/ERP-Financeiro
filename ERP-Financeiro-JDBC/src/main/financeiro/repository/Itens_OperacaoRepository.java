package main.financeiro.repository;

import main.financeiro.model.Itens_Operacao;

import java.sql.Connection;

public class Itens_OperacaoRepository {
    private Connection conn;

    public Itens_OperacaoRepository(Connection conn) {
        this.conn = conn;
    }

    public Itens_Operacao buscarPorId(){
        return new Itens_Operacao(1L, 1L, 1);
    }
}
