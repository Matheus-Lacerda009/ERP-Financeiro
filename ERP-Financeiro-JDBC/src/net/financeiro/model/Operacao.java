package net.financeiro.model;

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
}
