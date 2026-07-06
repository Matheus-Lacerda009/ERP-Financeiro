package net.financeiro.service;

import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Produto;
import net.financeiro.repository.Categoria_ItemRepository;
import net.financeiro.repository.ProdutosRepository;

import java.util.HashMap;
import java.util.List;

public class ProdutosService {

    private ProdutosRepository repository;
    private Categoria_ItemRepository categoriaItemRepository;

    public Produto inserir(Produto ins) {
        try{
            if(ins.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: nome vazio!");
            }
            if(ins.getValor() < 0){
                throw new NomeInvalidoException("Erro: valor é negativo!");
            }
            if(ins.getQuantidade_estoque() < 0){
                throw new NomeInvalidoException("Erro: quantidade de produtos é negativa");
            }
            if(categoriaItemRepository.buscarPorId(ins.getId_categoria_item()) == null){
                throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
            }
            return repository.inserir(ins);
        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        } catch (FkNaoEncontradaException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Produto atualizar(Produto atl){
        try{
            if(atl.getNome().trim().isEmpty()){
                throw new NomeInvalidoException("Erro: nome vazio!");
            }
            if(atl.getValor() < 0){
                throw new NomeInvalidoException("Erro: valor é negativo!");
            }
            if(atl.getQuantidade_estoque() < 0){
                throw new NomeInvalidoException("Erro: quantidade de produtos é negativa");
            }
            if(repository.buscarPorId(atl.getId_produto()) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if(categoriaItemRepository.buscarPorId(atl.getId_categoria_item()) == null){
                throw new FkNaoEncontradaException("Erro: chave estrangeira não encontrada!");
            }
            return repository.atualizar(atl);
        } catch(NomeInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(IdNaoEncontradoException e){
            System.out.println(e.getMessage());
            return null;
        } catch(FkNaoEncontradaException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Produto> listarInfo(){
        try {
            List<Produto> lista = repository.listarInfo();
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
