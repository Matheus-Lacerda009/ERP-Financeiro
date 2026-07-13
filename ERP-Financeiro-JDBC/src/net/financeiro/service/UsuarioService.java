package net.financeiro.service;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.PermissaoNegadaException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Usuario;
import net.financeiro.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository = new UsuarioRepository();
    public boolean cadastrando(Usuario adm, Usuario ins){
        try{
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
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean validacao(Usuario log){
        try{
            if(log.getNome().trim().isEmpty()){
                throw new NadaInseridoException("Erro: nome vazio!");
            }
            if(log.getSenha().trim().isEmpty()){
                throw new NadaInseridoException("Erro: senha vazia!");
            }
            return repository.validacao(log);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
