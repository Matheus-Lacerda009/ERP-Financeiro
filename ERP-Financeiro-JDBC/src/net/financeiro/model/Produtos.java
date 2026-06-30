package net.financeiro.model;

public class Produtos {
    private Long id_produto, id_categoria_item;
    private String nome, descricao;
    private double valor;
    private int quantidade_estoque;

    public Produtos(Long id_categoria_item, String nome, String descricao, double valor, int quantidade_estoque) {
        this.id_categoria_item = id_categoria_item;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade_estoque = quantidade_estoque;
    }
}
