package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.ViewNotaFiscal;
import net.financeiro.repository.ViewNotaFiscalRepository;

import java.util.List;

public class ViewNotaFiscalService {
    private final ViewNotaFiscalRepository repository = new ViewNotaFiscalRepository();

    public List<ViewNotaFiscal> listarInfo(){
        try{
            List<ViewNotaFiscal> lista = repository.listarInfo();
            if(lista.isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        }catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}