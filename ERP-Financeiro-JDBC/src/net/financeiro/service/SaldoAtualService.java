package net.financeiro.service;

import net.financeiro.exceptions.AvisoException;
import net.financeiro.model.SaldoAtual;
import net.financeiro.repository.SaldoAtualRepository;

public class SaldoAtualService {
    private final SaldoAtualRepository repository = new SaldoAtualRepository();

    public SaldoAtual visualizar() {
        SaldoAtual result = repository.visualizar();
        if(result.getValor() < 0) {
            System.out.println("Aviso: Saldo negativo!");
        }
        return result;
    }
}
