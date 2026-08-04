package net.financeiro.controller;

import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.financeiro.service.UsuarioService;

import java.sql.SQLException;

@RestController
@CrossOrigin("*")
@RequestMapping("login")
public class UsuarioController {
    private final UsuarioService service = new UsuarioService();

    @PostMapping
    public ResponseEntity<Boolean> cadastrando(@RequestBody Usuario adm, @RequestBody Usuario ins) throws NadaInseridoException, SQLException, ValorInvalidoException {
        return ResponseEntity.ok(service.cadastrando(adm, ins));
    }

    @GetMapping
    public ResponseEntity<Boolean> validacao(@RequestParam String nome, @RequestParam String senha) throws NadaInseridoException, SQLException {
        Usuario log = new Usuario(nome, senha);
        return ResponseEntity.ok(service.validacao(log));
    }
}
