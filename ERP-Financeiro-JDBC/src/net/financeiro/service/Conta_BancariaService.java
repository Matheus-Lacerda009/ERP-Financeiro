package net.financeiro.service;

import net.financeiro.connection.Conexao;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Conta_Bancaria;
import net.financeiro.repository.Conta_BancariaRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Conta_BancariaService {

    private final Conta_BancariaRepository repository = new Conta_BancariaRepository();

    public Conta_Bancaria inserir(Conta_Bancaria ins) throws NadaInseridoException, NadaInseridoException {
        try{
            if(ins.getNome_banco().trim().isEmpty()){
                throw new NadaInseridoException("Erro: nome banco vazio!");
            }
            if(ins.getNumero_conta() < 0){
                throw new NadaInseridoException("Erro: número banco vazio!");
            }
            return repository.inserir(ins);
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Conta_Bancaria atualizar(Conta_Bancaria atl){
        try{
            if(atl.getNome_banco().trim().isEmpty()){
                throw new NadaInseridoException("Erro: nome banco vazio!");
            }
            if(atl.getNumero_conta() < 0){
                throw new NadaInseridoException("Erro: número banco vazio!");
            }
            if(repository.buscarPorId(atl.getId_caixa()) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            return repository.atualizar(atl);
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
        catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Conta_Bancaria> listarInfo(){
        try {
            List<Conta_Bancaria> lista = repository.listarInfo();
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

    public boolean reativar(Long id){
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            repository.reativar(id);
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
