package main.financeiro.model;

public class SaldoAtual {
    private double valor;

    public SaldoAtual(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString(){
        return "Saldo: " + valor;
    }
}
