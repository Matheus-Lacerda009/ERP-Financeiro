package net.financeiro.model;

public class Conta_Bancaria {
    private Long id_caixa;
    private String nome_banco;
    private int numero_conta;

    public Conta_Bancaria(String nome_banco, int numero_conta) {
        this.nome_banco = nome_banco;
        this.numero_conta = numero_conta;
    }
}
