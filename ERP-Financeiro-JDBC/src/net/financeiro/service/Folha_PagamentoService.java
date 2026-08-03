package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Fluxo_Caixa;
import net.financeiro.model.Folha_Pagamento;
import net.financeiro.repository.*;

import java.sql.SQLException;
import java.util.List;

public class Folha_PagamentoService {



    private final Folha_PagamentoRepository repository = new Folha_PagamentoRepository();
    private final FuncionarioRepository repositoryA = new FuncionarioRepository();

    public Folha_Pagamento inserir(Folha_Pagamento ins) throws NadaInseridoException, IdNaoEncontradoException, NadaInseridoException, SQLException {


            if(repositoryA.buscarPorId(ins.getId_funcionario()) == null){
                throw new IdNaoEncontradoException("Erro: id funcionário não encontrado!");
            }
            if(ins.getDescontos() < 0.0){
                throw new NadaInseridoException("Erro: Desconto vazio!");
            }
            if(ins.getValor_hora() < 0.0){
                throw new NadaInseridoException("Erro: Valor Hora vazio!");
            }
            if(ins.getHoras_trabalhadas() < 0){
                throw new NadaInseridoException("Erro: Horas trabalhadas nulo!");
            }
            if(ins.getData_entrada().trim().isEmpty()){
                throw new NadaInseridoException("Erro: id folha_pagamento não encontrado!");
            }

            return repository.inserir(ins);



    }

    public Folha_Pagamento atualizar(Folha_Pagamento atl, Long id) throws SQLException, NadaInseridoException, IdNaoEncontradoException {


            if(repositoryA.buscarPorId(atl.getId_funcionario()) == null){
                throw new IdNaoEncontradoException("Erro: id funcionário não encontrado!");
            }
            if(repository.buscarPorId(id) == null){
                throw new IdNaoEncontradoException("Erro: id folha pagamento não encontrado!");
            }
            if(atl.getDescontos() < 0.0){
                throw new NadaInseridoException("Erro: Desconto vazio!");
            }
            if(atl.getValor_hora() < 0.0){
                throw new NadaInseridoException("Erro: Valor Hora vazio!");
            }
            if(atl.getHoras_trabalhadas() < 0){
                throw new NadaInseridoException("Erro: Horas trabalhadas nulo!");
            }
            if(atl.getData_entrada().trim().isEmpty()){
                throw new NadaInseridoException("Erro: id folha_pagamento não encontrado!");
            }
            return repository.atualizar(atl, id);




    }

    public List<Folha_Pagamento> listarInfo() throws NadaInseridoException, SQLException {

            List<Folha_Pagamento> lista = repository.listarInfo();
            if (lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;

    }

    public boolean deletar(Long id) throws SQLException, NadaInseridoException, IdNaoEncontradoException {

            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            repository.deletar(id);
            return true;

    }

    public boolean reativar(Long id) throws SQLException, NadaInseridoException, IdNaoEncontradoException {

            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.reativar(id);

    }


}
