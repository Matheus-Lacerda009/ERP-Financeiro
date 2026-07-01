package net.financeiro.model;

public class Fluxo_Caixa {
    private Long id_fluxo_caixa, id_caixa, id_forma_pagamento;
    private String tipo_operacao;
    private int parcelas;

    public Fluxo_Caixa(Long id_caixa, Long id_forma_pagamento, String tipo_operacao, int parcelas) {
        this.id_caixa = id_caixa;
        this.id_forma_pagamento = id_forma_pagamento;
        this.tipo_operacao = tipo_operacao;
        this.parcelas = parcelas;
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
}
