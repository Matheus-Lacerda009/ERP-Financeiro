package net.financeiro.model;

public class Itens_Operacao {
    private Long id_itens_operacao, id_produto, id_operacao;
    private int quantidade_produtos;

    public Itens_Operacao(Long id_produto, Long id_operacao, int quantidade_produtos) {
        this.id_produto = id_produto;
        this.id_operacao = id_operacao;
        this.quantidade_produtos = quantidade_produtos;
    }

    public Long getId_itens_operacao() {
        return id_itens_operacao;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public Long getId_operacao() {
        return id_operacao;
    }

    public int getQuantidade_produtos() {
        return quantidade_produtos;
    }

    public void setId_itens_operacao(Long id_itens_operacao) {
        this.id_itens_operacao = id_itens_operacao;
    }
}
