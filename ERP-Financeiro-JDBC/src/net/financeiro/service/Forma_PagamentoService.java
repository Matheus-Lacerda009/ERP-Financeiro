package net.financeiro.service;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Forma_Pagamento;
import net.financeiro.repository.Forma_PagamentoRepository;

import java.util.List;

public class Forma_PagamentoService {
    private final Forma_PagamentoRepository repository = new Forma_PagamentoRepository();

    public Forma_Pagamento inserir(Forma_Pagamento ins) {
        try {
            if (ins.getNome().trim().isEmpty()) {
                throw new NadaInseridoException("Erro: Nome está vazio");
            }
            return repository.inserir(ins);
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Forma_Pagamento atualizar(Forma_Pagamento atl, Long id) {
        try {
            if (atl.getNome().trim().isEmpty()) {
                throw new NadaInseridoException("ERRO: Nome inválido, não pode ser vazio");
            } else if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.atualizar(atl, id);
        } catch (NadaInseridoException | IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Forma_Pagamento> listarInfo() {
        List<Forma_Pagamento> lista = repository.listarInfo();
        try {
            if (lista == null || lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;
        } catch (NadaInseridoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id) {
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.deletar(id);
        } catch (IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean reativar(Long id) {
        try {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("ERRO: Id não encontrado");
            }
            return repository.reativar(id);
        } catch (IdNaoEncontradoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}