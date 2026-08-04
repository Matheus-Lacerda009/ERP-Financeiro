package net.financeiro.model;

public class Fluxo_Caixa {
    private Long id_fluxo_caixa, id_caixa, id_forma_pagamento, id_folha_pagamento, id_operacao ;
    private String tipo_operacao;
    private int parcelas;
    private String nome_caixa, nome_forma_pagamento, nome_funcionario_folha, status_operacao;

    public Fluxo_Caixa(Long id_caixa, Long id_forma_pagamento, String tipo_operacao, int parcelas, Long id_folha_pagamento, Long id_operacao) {
        this.id_caixa = id_caixa;
        this.id_forma_pagamento = id_forma_pagamento;
        this.tipo_operacao = tipo_operacao;
        this.parcelas = parcelas;
        this.id_folha_pagamento = id_folha_pagamento;
        this.id_operacao = id_operacao;

    }

    public Fluxo_Caixa(Long id_fluxo_caixa, Long id_caixa, Long id_forma_pagamento, String tipo_operacao, int parcelas, Long id_folha_pagamento, Long id_operacao,
                       String nome_caixa, String nome_forma_pagamento, String nome_funcionario_folha, String status_operacao) {
        this.id_fluxo_caixa = id_fluxo_caixa;
        this.id_caixa = id_caixa;
        this.id_forma_pagamento = id_forma_pagamento;
        this.tipo_operacao = tipo_operacao;
        this.parcelas = parcelas;
        this.id_folha_pagamento = id_folha_pagamento;
        this.id_operacao = id_operacao;
        this.nome_caixa = nome_caixa;
        this.nome_forma_pagamento = nome_forma_pagamento;
        this.nome_funcionario_folha = nome_funcionario_folha;
        this.status_operacao = status_operacao;
    }

    public Long getId_fluxo_caixa() {
        return id_fluxo_caixa;
    }

    public Long getId_caixa() {
        return id_caixa;
    }

    public Long getId_forma_pagamento() {
        return id_forma_pagamento;
    }

    public String getTipo_operacao() {
        return tipo_operacao;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setId_fluxo_caixa(Long id_fluxo_caixa) {
        this.id_fluxo_caixa = id_fluxo_caixa;
    }

    public Long getId_folha_pagamento() {
        return id_folha_pagamento;
    }

    public Long getId_operacao() {
        return id_operacao;
    }

    public String getNome_caixa() {
        return nome_caixa;
    }

    public String getNome_forma_pagamento() {
        return nome_forma_pagamento;
    }

    public String getNome_funcionario_folha() {
        return nome_funcionario_folha;
    }

    public String getStatus_operacao() {
        return status_operacao;
    }
}
