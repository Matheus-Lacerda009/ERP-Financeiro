package net.financeiro.model;

public class Usuario {
    private String nome, senha;
    private int permissao;

    public Usuario(String nome, String senha, int permissao) {
        this.nome = nome;
        this.senha = senha;
        this.permissao = permissao;
    }

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public int getPermissao() {
        return permissao;
    }
}
