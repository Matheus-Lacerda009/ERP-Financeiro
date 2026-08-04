package net.financeiro.model;

import java.time.LocalDateTime;

public class Folha_Pagamento {
    private Long id_folha_pagamento, id_funcionario;
    private double descontos, valor_hora;
    private int horas_trabalhadas;
    private String data_entrada;
    private String nome_funcionario;

    public Folha_Pagamento(Long id_funcionario, double descontos, double valor_hora, int horas_trabalhadas, String data_entrada) {
        this.id_funcionario = id_funcionario;
        this.descontos = descontos;
        this.valor_hora = valor_hora;
        this.horas_trabalhadas = horas_trabalhadas;
        this.data_entrada = data_entrada;
    }

    public Folha_Pagamento(Long id_folha_pagamento, Long id_funcionario, double descontos, double valor_hora, int horas_trabalhadas, String data_entrada) {
        this.id_folha_pagamento = id_folha_pagamento;
        this.id_funcionario = id_funcionario;
        this.descontos = descontos;
        this.valor_hora = valor_hora;
        this.horas_trabalhadas = horas_trabalhadas;
        this.data_entrada = data_entrada;
    }

    public Folha_Pagamento(Long id_folha_pagamento, Long id_funcionario, double descontos, double valor_hora, int horas_trabalhadas, String data_entrada, String nome_funcionario) {
        this.id_folha_pagamento = id_folha_pagamento;
        this.id_funcionario = id_funcionario;
        this.descontos = descontos;
        this.valor_hora = valor_hora;
        this.horas_trabalhadas = horas_trabalhadas;
        this.data_entrada = data_entrada;
        this.nome_funcionario = nome_funcionario;
    }

    public Long getId_folha_pagamento() {
        return id_folha_pagamento;
    }

    public Long getId_funcionario() {
        return id_funcionario;
    }

    public double getDescontos() {
        return descontos;
    }

    public double getValor_hora() {
        return valor_hora;
    }

    public int getHoras_trabalhadas() {
        return horas_trabalhadas;
    }

    public String getData_entrada() {
        return data_entrada;
    }

    public void setId_folha_pagamento(Long id_folha_pagamento) {
        this.id_folha_pagamento = id_folha_pagamento;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }
}
