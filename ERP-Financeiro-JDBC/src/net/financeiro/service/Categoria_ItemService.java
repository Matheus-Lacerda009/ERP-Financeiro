package net.financeiro.service;

import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.repository.Categoria_ItemRepository;

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
}
