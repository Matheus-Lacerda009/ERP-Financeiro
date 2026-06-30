package net.financeiro.model;

public class Funcionario {
    private Long id_funcionario;
    private String nome, cpf, telefone, email;

    public Funcionario(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }
}
