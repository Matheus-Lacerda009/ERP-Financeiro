package net.financeiro.model;

public class Fornecedor_Cliente {
    private Long id_fornecedor_cliente;
    private String razao_social_nome, cnpj_cpf, telefone, email;

    public Fornecedor_Cliente(String razao_social_nome, String cnpj_cpf, String telefone, String email) {
        this.razao_social_nome = razao_social_nome;
        this.cnpj_cpf = cnpj_cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Fornecedor_Cliente(Long id_fornecedor_cliente, String razao_social_nome, String cnpj_cpf, String telefone, String email) {
        this.id_fornecedor_cliente = id_fornecedor_cliente;
        this.razao_social_nome = razao_social_nome;
        this.cnpj_cpf = cnpj_cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId_fornecedor_cliente() {
        return id_fornecedor_cliente;
    }

    public String getRazao_social_nome() {
        return razao_social_nome;
    }

    public String getCnpj_cpf() {
        return cnpj_cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setId_fornecedor_cliente(Long id_fornecedor_cliente) {
        this.id_fornecedor_cliente = id_fornecedor_cliente;
    }

    @Override
    public String toString(){
        return "ID: " + id_fornecedor_cliente
                + "\nNome: " + razao_social_nome
                + "\nCNPJ/CPF: " + cnpj_cpf
                + "\nTelefone: " + telefone
                + "\nEmail: " + email;
    }
}
