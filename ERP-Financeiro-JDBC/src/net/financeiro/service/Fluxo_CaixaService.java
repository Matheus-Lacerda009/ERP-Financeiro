package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Fluxo_Caixa;
import net.financeiro.repository.Categoria_ItemRepository;
import net.financeiro.repository.Fluxo_CaixaRepository;

import java.util.List;

public class Fluxo_CaixaService {

    private final Fluxo_CaixaRepository repository = new Fluxo_CaixaRepository();

    public Fluxo_Caixa inserir(Fluxo_Caixa ins) throws NomeInvalidoException, IdNaoEncontradoException, NadaInseridoException {
        try{

            if(repository.buscarPorId(ins.getId_caixa()) == null){
                throw new IdNaoEncontradoException("Erro: id caixa não encontrado!");
            }
            if(repository.buscarPorId(ins.getId_forma_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id forma_pagamento não encontrado!");
            }
            if(ins.getTipo_operacao().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: Tipo da operação vazio!");
            }
            if(ins.getParcelas() < 0){
                throw new NadaInseridoException("Erro: parcelas nulo!");
            }
            if(repository.buscarPorId(ins.getId_folha_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id folha_pagamento não encontrado!");
            }
            if(repository.buscarPorId(ins.getId_operacao()) == null){
                throw new IdNaoEncontradoException("Erro: id operação não encontrado!");
            }
            return repository.inserir(ins);

        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Fluxo_Caixa atualizar(Fluxo_Caixa atl){



        try{

            if(repository.buscarPorId(atl.getId_caixa()) == null){
                throw new IdNaoEncontradoException("Erro: id caixa não encontrado!");
            }
            if(repository.buscarPorId(atl.getId_forma_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id forma_pagamento não encontrado!");
            }
            if(atl.getTipo_operacao().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: Tipo da operação vazio!");
            }
            if(atl.getParcelas() < 0){
                throw new NadaInseridoException("Erro: parcelas nulo!");
            }
            if(repository.buscarPorId(atl.getId_folha_pagamento()) == null){
                throw new IdNaoEncontradoException("Erro: id folha_pagamento não encontrado!");
            }
            if(repository.buscarPorId(atl.getId_operacao()) == null){
                throw new IdNaoEncontradoException("Erro: id operação não encontrado!");
            }
            return repository.inserir(atl);

        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(NadaInseridoException e){
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
            repository.deletar(id);
            return true;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return false;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
