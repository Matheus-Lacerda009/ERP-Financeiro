package main.net.financeiro.model;

public class Valor_pVenda {
    private Long id_operacao;
    private double valor;

    public Valor_pVenda(double valor) {
        this.valor = valor;
    }

    public Valor_pVenda(Long id_operacao, double valor) {
        this.id_operacao = id_operacao;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public Long getId_operacao() {
        return id_operacao;
    }

    public void setId_operacao(Long id_operacao) {
        this.id_operacao = id_operacao;
    }

    @Override
    public String toString(){
        return "Valor da venda de ID " + id_operacao
                + "Valor da venda: " + valor;
    }
}
