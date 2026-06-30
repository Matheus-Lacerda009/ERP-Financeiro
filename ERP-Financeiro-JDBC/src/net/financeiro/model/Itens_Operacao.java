package net.financeiro.model;

public class Itens_Operacao {
    private Long id_itens_operacao, id_produto, id_operacao;
    private int quantidade_produtos;

    public Itens_Operacao(Long id_produto, Long id_operacao, int quantidade_produtos) {
        this.id_produto = id_produto;
        this.id_operacao = id_operacao;
        this.quantidade_produtos = quantidade_produtos;
    }
}
