package net.financeiro.service;

import net.financeiro.exceptions.AvisoException;
import net.financeiro.model.Valor_pVenda;
import net.financeiro.repository.Valor_pVendaRepository;

public class Valor_pVendaService {
    Valor_pVendaRepository repository = new Valor_pVendaRepository();

    public Valor_pVenda visualizar() {
        try {
            Valor_pVenda result = repository.visualizar();
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
