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
}
