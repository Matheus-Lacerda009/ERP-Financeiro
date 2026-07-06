package main.financeiro.model;

public class Funcionario {
    private Long id_funcionario;
    private String nome, cpf, telefone, email;

    public Funcionario(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Funcionario(Long id_funcionario, String nome, String cpf, String telefone, String email) {
        this.id_funcionario = id_funcionario;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId_funcionario() {
        return id_funcionario;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setId_funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    @Override
    public String toString(){
        return "ID: " + id_funcionario
                + "\nNome: " + nome
                + "\nCPF: " + cpf
                + "\nTelefone: " + telefone
                + "\nEmail: " + email;
    }
}
