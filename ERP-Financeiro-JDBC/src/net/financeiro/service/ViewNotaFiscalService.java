package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.ViewNotaFiscal;
import net.financeiro.repository.ViewNotaFiscalRepository;

import java.sql.SQLException;
import java.util.List;

public class ViewNotaFiscalService {
    private final ViewNotaFiscalRepository repository = new ViewNotaFiscalRepository();

    public List<ViewNotaFiscal> listarInfo() throws NadaInseridoException, SQLException {
        List<ViewNotaFiscal> lista = repository.listarInfo();
        if(lista.isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }
}