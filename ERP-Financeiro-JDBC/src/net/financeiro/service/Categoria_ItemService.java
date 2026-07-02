package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.repository.Categoria_ItemRepository;

import java.util.List;

public class Categoria_ItemService {
    private final Categoria_ItemRepository repository = new Categoria_ItemRepository();

    public Categoria_Item inserir(Categoria_Item ins) throws NomeInvalidoException{
        try{
            if(ins.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: nome vazio!");
            }
            return repository.inserir(ins);
        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Categoria_Item atualizar(Categoria_Item atl){
        try{
            if(atl.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: nome vazio!");
            }
            if(repository.buscarPorId(atl.getId_categoria_item()) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            return repository.atualizar(atl);
        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Categoria_Item> listarInfo(){
        try {
            List<Categoria_Item> lista = repository.listarInfo();
            if (lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deletar(Long id){
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            if(repository.deletar(id)){
                System.out.println("Deletado com sucesso!");
            }
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
        }
    }
}
