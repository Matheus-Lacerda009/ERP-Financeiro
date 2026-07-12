package main.net.financeiro.model;

import java.time.LocalDateTime;

public class Operacao {
    private Long id_operacao, id_fornecedor_cliente, id_funcionario;
    private LocalDateTime data_operacao;
    private String status_operacao;

    public Operacao(Long id_fornecedor_cliente, Long id_funcionario, LocalDateTime data_operacao, String status_operacao) {
        this.id_fornecedor_cliente = id_fornecedor_cliente;
        this.id_funcionario = id_funcionario;
        this.data_operacao = data_operacao;
        this.status_operacao = status_operacao;
    }

    public Operacao(Long id_operacao, Long id_fornecedor_cliente, Long id_funcionario, LocalDateTime data_operacao, String status_operacao) {
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

    public LocalDateTime getData_operacao() {
        return data_operacao;
    }

    public String getStatus_operacao() {
        return status_operacao;
    }

    public void setId_operacao(Long id_operacao) {
        this.id_operacao = id_operacao;
    }

    @Override
    public String toString(){
        return "ID: " + id_operacao
                + "\nData da operação: " + data_operacao
                + "\nStatus da operação: " + status_operacao
                + "\nID do fornecedor/cliente: " + id_fornecedor_cliente
                + "\nID do funcionário: " + id_funcionario;
    }
}
