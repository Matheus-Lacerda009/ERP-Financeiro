package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.PermissaoNegadaException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Usuario;
import net.financeiro.repository.UsuarioRepository;

import java.sql.SQLException;

public class UsuarioService {
    private final UsuarioRepository repository = new UsuarioRepository();

    public boolean cadastrando(Usuario adm, Usuario ins) throws NadaInseridoException, ValorInvalidoException, SQLException {
        if(adm.getNome().trim().isEmpty() || ins.getNome().trim().isEmpty()){
            throw new NadaInseridoException("Erro: nome vazio!");
        }
        if(adm.getSenha().trim().isEmpty() || ins.getSenha().trim().isEmpty()){
            throw new NadaInseridoException("Erro: senha vazia!");
        }
        if(ins.getPermissao() < 1 || ins.getPermissao() > 3){
            throw new ValorInvalidoException("Erro: permissão inválida!");
        }
        if(!repository.isAdm(adm)){
            throw new PermissaoNegadaException("Erro: permissão negada!");
        }
        return repository.cadastrando(ins);
    }

    public boolean validacao(Usuario user) throws SQLException, NadaInseridoException {
        if(user.getNome().trim().isEmpty()){
            throw new NadaInseridoException("Erro: nome vazio!");
        }
        if(user.getSenha().trim().isEmpty()){
            throw new NadaInseridoException("Erro: senha vazia!");
        }
        return repository.validacao(user);
    }
}
