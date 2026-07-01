package net.financeiro.model;

public class Categoria_Item {
    private Long id_categoria_item;
    private String nome;

    public Categoria_Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Long getId_categoria_item() {
        return id_categoria_item;
    }

    public void setId_categoria_item(Long id_categoria_item) {
        this.id_categoria_item = id_categoria_item;
    }
}
