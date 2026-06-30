package net.financeiro.model;

import java.time.LocalDateTime;

public class Folha_Pagamento {
    private Long id_folha_pagamento, id_funcionario;
    private double descontos, valor_hora;
    private int horas_trabalhadas;
    private LocalDateTime data_entrada;

    public Folha_Pagamento(Long id_funcionario, double descontos, double valor_hora, int horas_trabalhadas, LocalDateTime data_entrada) {
        this.id_funcionario = id_funcionario;
        this.descontos = descontos;
        this.valor_hora = valor_hora;
        this.horas_trabalhadas = horas_trabalhadas;
        this.data_entrada = data_entrada;
    }
}
