package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Valor_Salario;
import net.financeiro.repository.Valor_SalarioRepository;

import java.sql.SQLException;
import java.util.List;

public class Valor_SalarioService {
    private final Valor_SalarioRepository  repository = new Valor_SalarioRepository();

    public List<Valor_Salario> listarInfo() throws NadaInseridoException, SQLException {
        List<Valor_Salario> lista = repository.listarInfo();
        if(lista.isEmpty()){
            throw new NadaInseridoException("Erro: nada inserido no banco");
        }
        return lista;
    }
}