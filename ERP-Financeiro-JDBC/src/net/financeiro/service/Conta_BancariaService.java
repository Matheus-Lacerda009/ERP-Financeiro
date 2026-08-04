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


    public Conta_Bancaria inserir(Conta_Bancaria ins) throws NadaInseridoException, NadaInseridoException, SQLException {

            if (ins.getNome_banco().trim().isEmpty()) {
                throw new NadaInseridoException("Erro: nome banco vazio!");
            }
        if (ins.getNumero_conta() < 0) {
            throw new NadaInseridoException("Erro: número banco vazio!");
        }
        return repository.inserir(ins);


    }

    public Conta_Bancaria atualizar(Conta_Bancaria atl, Long id) throws IdNaoEncontradoException, SQLException, NadaInseridoException {

            if(atl.getNome_banco().trim().isEmpty()){
                throw new NadaInseridoException("Erro: nome banco vazio!");
            }
            if(atl.getNumero_conta() < 0){
                throw new NadaInseridoException("Erro: número banco vazio!");
            }
            if(repository.buscarPorId(id) == null){
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            return repository.atualizar(atl, id);

    }

    public List<Conta_Bancaria> listarInfo() throws NadaInseridoException, SQLException {
            List<Conta_Bancaria> lista = repository.listarInfo();
            if (lista.isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return lista;

    }

    public boolean deletar(Long id) throws SQLException, IdNaoEncontradoException, NadaInseridoException {
            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            repository.deletar(id);
            return true;

    }

    public boolean reativar(Long id) throws SQLException, IdNaoEncontradoException, NadaInseridoException {

            if (repository.buscarPorId(id) == null) {
                throw new IdNaoEncontradoException("Erro: id não encontrado!");
            }
            if (repository.listarInfo().isEmpty()) {
                throw new NadaInseridoException("Erro: nada inserido no banco");
            }
            return repository.reativar(id);

    }


}
