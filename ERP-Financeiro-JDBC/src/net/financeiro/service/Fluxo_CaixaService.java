package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Fluxo_Caixa;
import net.financeiro.repository.*;

import java.util.HashMap;
import java.util.List;

public class Fluxo_CaixaService {

    private final Fluxo_CaixaRepository repository = new Fluxo_CaixaRepository();
    private final Conta_BancariaRepository repositoryA = new Conta_BancariaRepository();
    private final Forma_PagamentoRepository repositoryB = new Forma_PagamentoRepository();
    private final Folha_PagamentoRepository repositoryC = new Folha_PagamentoRepository();
    private final OperacaoRepository repositoryD = new OperacaoRepository();

    public Fluxo_Caixa inserir(Fluxo_Caixa ins) throws NadaInseridoException, IdNaoEncontradoException, NadaInseridoException {
        try{

            if(repositoryA.buscarPorId(ins.getId_caixa()) == null){
                throw new IdNaoEncontradoException("Erro: id caixa não encontrado!");
            }
            if(repositoryB.buscarPorId(ins.getId_forma_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id forma_pagamento não encontrado!");
            }
            if(ins.getTipo_operacao().trim().isEmpty()){
                throw new NadaInseridoException("Erro: Tipo da operação vazio!");
            }
            if(ins.getParcelas() < 0){
                throw new NadaInseridoException("Erro: parcelas nulo!");
            }
            if(repositoryC.buscarPorId(ins.getId_folha_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id folha_pagamento não encontrado!");
            }
            if(repositoryD.buscarPorId(ins.getId_operacao()) == null){
                throw new IdNaoEncontradoException("Erro: id operação não encontrado!");
            }
            return repository.inserir(ins);

        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Fluxo_Caixa atualizar(Fluxo_Caixa atl, Long id){

        try{

            if(repositoryA.buscarPorId(atl.getId_caixa()) == null){
                throw new IdNaoEncontradoException("Erro: id caixa não encontrado!");
            }
            if(repositoryB.buscarPorId(atl.getId_forma_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id forma_pagamento não encontrado!");
            }
            if(atl.getTipo_operacao().trim().isEmpty()){
                throw new NadaInseridoException("Erro: Tipo da operação vazio!");
            }
            if(atl.getParcelas() < 0){
                throw new NadaInseridoException("Erro: parcelas nulo!");
            }
            if(repositoryC.buscarPorId(atl.getId_folha_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id folha_pagamento não encontrado!");
            }
            if(repositoryD.buscarPorId(atl.getId_operacao()) == null){
                throw new IdNaoEncontradoException("Erro: id operação não encontrado!");
            }
            if(repository.buscarPorId(id) == null){
                throw new IdNaoEncontradoException("Erro: id fluxo de caixa não encontrado!");
            }
            return repository.atualizar(atl, id);

        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Fluxo_Caixa> listarInfo(){
        try {
            List<Fluxo_Caixa> lista = repository.listarInfo();
            if (lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.deletar(id);
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return false;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean reativar(Long id){
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.reativar(id);
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return false;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*MÉTODOS QUERIES*/

    public HashMap<String, List<String>> entradas_realizadas(int dias ) {
        try {
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.entradas_realizadas(dias);
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> entradas_previstas(int dias ){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.entradas_previstas( dias);
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> saidas_realizadas(int dias ){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.saidas_realizadas( dias);
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> saidas_previstas(int dias ){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.saidas_previstas( dias);
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
