package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Valor_pVenda;
import net.financeiro.repository.Valor_pVendaRepository;

import java.sql.SQLException;
import java.util.List;

public class Valor_pVendaService {
    Valor_pVendaRepository repository = new Valor_pVendaRepository();

    public List<Valor_pVenda> visualizar() throws SQLException, NadaInseridoException {
        List<Valor_pVenda> result = repository.visualizar();

        if(result.isEmpty()) {
            throw new NadaInseridoException("Erro: A view não possui valores!");
        }

        return result;
    }
}
