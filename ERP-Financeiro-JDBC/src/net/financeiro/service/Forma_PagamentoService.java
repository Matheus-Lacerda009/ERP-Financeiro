//package net.financeiro.service;
//
//import net.financeiro.exceptions.IdNaoEncontradoException;
//import net.financeiro.exceptions.NadaInseridoException;
//import net.financeiro.exceptions.NadaInseridoException;
//import net.financeiro.model.Forma_Pagamento;
//import net.financeiro.repository.Forma_PagamentoRepository;
//
//import java.util.List;
//
//public class Forma_PagamentoService {
//    private final Forma_PagamentoRepository repository = new Forma_PagamentoRepository();
//
//    public Forma_Pagamento inserir(Forma_Pagamento ins) throws NadaInseridoException {
//        if (ins.getNome().trim().isEmpty()) {
//            throw new NadaInseridoException("Erro: Nome está vazio");
//        }
//        return repository.inserir(ins);
//    }
//
//    public Forma_Pagamento atualizar(Forma_Pagamento atl, Long id) {
//        if (atl.getNome().trim().isEmpty()) {
//            throw new NadaInseridoException("ERRO: Nome inválido, não pode ser vazio");
//        } else if (repository.buscarPorId(id) == null) {
//            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
//        }
//        return repository.atualizar(atl, id);
//    }
//
//    public List<Forma_Pagamento> listarInfo() {
//    List<Forma_Pagamento> lista = repository.listarInfo();
//        if (lista == null || lista.isEmpty()) {
//            throw new NadaInseridoException("Erro: nada inserido no banco");
//        }
//        return lista;
//
//    }
//
//    public boolean deletar(Long id) {
//        if (repository.buscarPorId(id) == null) {
//            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
//        }
//        return repository.deletar(id);
//    }
//
//    public boolean reativar(Long id) {
//        if (repository.buscarPorId(id) == null) {
//            throw new IdNaoEncontradoException("ERRO: Id não encontrado");
//        }
//        return repository.reativar(id);
//    }
//}