package main.net.financeiro.model;

public class Categoria_Item {
    private Long id_categoria_item;
    private String nome;

    public Categoria_Item(){}

    public Categoria_Item(String nome) {
        this.nome = nome;
    }

    public Categoria_Item(Long id_categoria_item, String nome) {
        this.id_categoria_item = id_categoria_item;
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

    @Override
    public String toString(){
        return "ID: " + id_categoria_item
                + "\nNome: " + nome;
    }
}
