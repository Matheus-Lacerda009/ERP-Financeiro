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

    public Produtos(Long id_produto, Long id_categoria_item, String nome, String descricao, double valor, int quantidade_estoque) {
        this.id_produto = id_produto;
        this.id_categoria_item = id_categoria_item;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade_estoque = quantidade_estoque;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public Long getId_categoria_item() {
        return id_categoria_item;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    @Override
    public String toString(){
        return "ID: " + id_produto
                + "\nNome: " + nome
                + "\nDescrição: " + descricao
                + "\nValor: " + valor
                + "\nQuantidade em estoque: " + quantidade_estoque
                + "\nID da categoria do item: " + id_categoria_item;
    }
}
