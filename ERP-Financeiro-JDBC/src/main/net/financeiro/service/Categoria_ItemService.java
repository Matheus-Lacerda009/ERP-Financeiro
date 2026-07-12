package main.net.financeiro.service;

import main.net.financeiro.exceptions.IdNaoEncontradoException;
import main.net.financeiro.exceptions.NadaInseridoException;
import main.net.financeiro.exceptions.NomeInvalidoException;
import main.net.financeiro.model.Categoria_Item;
import main.net.financeiro.repository.Categoria_ItemRepository;
import main.net.financeiro.repository.ProdutosRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Categoria_ItemService {

    private final Categoria_ItemRepository repository = new Categoria_ItemRepository();
    private final ProdutosRepository produtosRepository = new ProdutosRepository();

    public Categoria_Item inserir(Categoria_Item ins){
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

    public Categoria_Item atualizar(Categoria_Item atl, Long id){
        try{
            if(atl.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: nome vazio!");
            }
            if(repository.buscarPorId(id) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            return repository.atualizar(atl, id);
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

    public HashMap<String, List<String>> maiorVenda(){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.maiorVenda();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> menorVenda(){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.menorVenda();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> mediaVendas(){
        try{
            if(repository.listarInfo().isEmpty()){
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.mediaVendas();
        } catch(NadaInseridoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
