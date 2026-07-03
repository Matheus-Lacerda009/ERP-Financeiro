package net.financeiro.service;

import net.financeiro.exceptions.AvisoException;
import net.financeiro.model.SaldoAtual;
import net.financeiro.repository.SaldoAtualRepository;

public class SaldoAtualService {
    private final SaldoAtualRepository repository = new SaldoAtualRepository();

    public SaldoAtual visualizar() {
        try {
            SaldoAtual result = repository.visualizar();
            if(result.getValor() < 0) {
                throw new AvisoException("Aviso: Saldo negativo!");
            }

            return result;
        } catch(AvisoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
