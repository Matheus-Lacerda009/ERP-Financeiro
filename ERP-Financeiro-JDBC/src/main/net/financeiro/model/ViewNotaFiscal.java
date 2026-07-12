package main.net.financeiro.model;

import java.time.LocalDateTime;

public class ViewNotaFiscal {
    private Long numero_nota_fiscal, id_fluxo_caixa, id_parceiro, id_produto;
    private LocalDateTime data_emissao;
    private String nome_parceiro, documento_parceiro, email_parceiro, nome_produto, forma_pagamento, banco_recebimento;
    private double valor_unitario, valor_total_item;
    private int quantidade, parcelas;

    public ViewNotaFiscal(Long id_fluxo_caixa, Long id_parceiro, Long id_produto, LocalDateTime data_emissao, String nome_parceiro, String documento_parceiro, String email_parceiro, String nome_produto, String forma_pagamento, String banco_recebimento, double valor_unitario, double valor_total_item, int quantidade, int parcelas) {
        this.id_fluxo_caixa = id_fluxo_caixa;
        this.id_parceiro = id_parceiro;
        this.id_produto = id_produto;
        this.data_emissao = data_emissao;
        this.nome_parceiro = nome_parceiro;
        this.documento_parceiro = documento_parceiro;
        this.email_parceiro = email_parceiro;
        this.nome_produto = nome_produto;
        this.forma_pagamento = forma_pagamento;
        this.banco_recebimento = banco_recebimento;
        this.valor_unitario = valor_unitario;
        this.valor_total_item = valor_total_item;
        this.quantidade = quantidade;
        this.parcelas = parcelas;
    }

    public ViewNotaFiscal(Long numero_nota_fiscal, Long id_fluxo_caixa, Long id_parceiro, Long id_produto, LocalDateTime data_emissao, String nome_parceiro, String documento_parceiro, String email_parceiro, String nome_produto, String forma_pagamento, String banco_recebimento, double valor_unitario, double valor_total_item, int quantidade, int parcelas) {
        this.numero_nota_fiscal = numero_nota_fiscal;
        this.id_fluxo_caixa = id_fluxo_caixa;
        this.id_parceiro = id_parceiro;
        this.id_produto = id_produto;
        this.data_emissao = data_emissao;
        this.nome_parceiro = nome_parceiro;
        this.documento_parceiro = documento_parceiro;
        this.email_parceiro = email_parceiro;
        this.nome_produto = nome_produto;
        this.forma_pagamento = forma_pagamento;
        this.banco_recebimento = banco_recebimento;
        this.valor_unitario = valor_unitario;
        this.valor_total_item = valor_total_item;
        this.quantidade = quantidade;
        this.parcelas = parcelas;
    }

    public Long getNumero_nota_fiscal() {
        return numero_nota_fiscal;
    }

    public Long getId_fluxo_caixa() {
        return id_fluxo_caixa;
    }

    public Long getId_parceiro() {
        return id_parceiro;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public LocalDateTime getData_emissao() {
        return data_emissao;
    }

    public String getNome_parceiro() {
        return nome_parceiro;
    }

    public String getDocumento_parceiro() {
        return documento_parceiro;
    }

    public String getEmail_parceiro() {
        return email_parceiro;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public String getBanco_recebimento() {
        return banco_recebimento;
    }

    public double getValor_unitario() {
        return valor_unitario;
    }

    public double getValor_total_item() {
        return valor_total_item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setNumero_nota_fiscal(Long numero_nota_fiscal) {
        this.numero_nota_fiscal = numero_nota_fiscal;
    }

    @Override
    public String toString() {
        return "Número da nota fiscal:" + numero_nota_fiscal
                + "\nID do fluxo de caixa:" + id_fluxo_caixa
                + "\nID do parceiro:" + id_parceiro
                + "\nData de emissão:" + data_emissao
                + "\nNome do parceiro:" + nome_parceiro
                + "\nDocumentação do parceiro:" + documento_parceiro
                + "\nEmail do parceiro:" + email_parceiro
                + "\nForma de pagamento:" + forma_pagamento
                + "\nNome do banco:" + banco_recebimento
                + "\nNúmero de parcelas:" + parcelas;
    }
}
