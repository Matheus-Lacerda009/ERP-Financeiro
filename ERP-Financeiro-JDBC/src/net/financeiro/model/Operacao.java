package net.financeiro.model;

public class Operacao {
    private Long id_operacao, id_fornecedor_cliente, id_funcionario;
    private String status_operacao, data_operacao, nome_fornecedor_cliente, nome_funcionario;

    public Operacao(Long id_operacao, Long id_fornecedor_cliente, Long id_funcionario, String status_operacao, String data_operacao, String nome_fornecedor_cliente, String nome_funcionario) {
        this.id_operacao = id_operacao;
        this.id_fornecedor_cliente = id_fornecedor_cliente;
        this.id_funcionario = id_funcionario;
        this.status_operacao = status_operacao;
        this.data_operacao = data_operacao;
        this.nome_fornecedor_cliente = nome_fornecedor_cliente;
        this.nome_funcionario = nome_funcionario;
    }

    public Operacao(Long id_fornecedor_cliente, Long id_funcionario, String data_operacao, String status_operacao) {
        this.id_fornecedor_cliente = id_fornecedor_cliente;
        this.id_funcionario = id_funcionario;
        this.data_operacao = data_operacao;
        this.status_operacao = status_operacao;
    }

    public Operacao(Long id_operacao, Long id_fornecedor_cliente, Long id_funcionario, String data_operacao, String status_operacao) {
        this.id_operacao = id_operacao;
        this.id_fornecedor_cliente = id_fornecedor_cliente;
        this.id_funcionario = id_funcionario;
        this.data_operacao = data_operacao;
        this.status_operacao = status_operacao;
    }

    public Long getId_operacao() {
        return id_operacao;
    }

    public Long getId_fornecedor_cliente() {
        return id_fornecedor_cliente;
    }

    public Long getId_funcionario() {
        return id_funcionario;
    }

    public String getData_operacao() {
        return data_operacao;
    }

    public String getStatus_operacao() {
        return status_operacao;
    }

    public void setId_operacao(Long id_operacao) {
        this.id_operacao = id_operacao;
    }

    public String getNome_fornecedor_cliente() {
        return nome_fornecedor_cliente;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }
}
