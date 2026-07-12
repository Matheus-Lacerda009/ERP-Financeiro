package main.net.financeiro.model;

public class Conta_Bancaria {
    private Long id_caixa;
    private String nome_banco;
    private int numero_conta;

    public Conta_Bancaria(String nome_banco, int numero_conta) {
        this.nome_banco = nome_banco;
        this.numero_conta = numero_conta;
    }

    public Conta_Bancaria(Long id_caixa, String nome_banco, int numero_conta) {
        this.id_caixa = id_caixa;
        this.nome_banco = nome_banco;
        this.numero_conta = numero_conta;
    }

    public int getNumero_conta() {
        return numero_conta;
    }

    public String getNome_banco() {
        return nome_banco;
    }

    public Long getId_caixa() {
        return id_caixa;
    }

    public void setId_caixa(Long id_caixa) {
        this.id_caixa = id_caixa;
    }

    @Override
    public String toString(){
        return "ID: " + id_caixa
                + "\nNome do banco: " + nome_banco
                + "\nNúmero da conta: " + numero_conta;
    }
}
