package net.financeiro.controller;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Fornecedor_Cliente;
import net.financeiro.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService service = new ClienteService();

    @PostMapping
    public ResponseEntity<Fornecedor_Cliente> inserir(@RequestBody Fornecedor_Cliente ins) throws NadaInseridoException, SQLException {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor_Cliente>> listarInfo() throws NadaInseridoException, SQLException {
        return ResponseEntity.ok(service.listarInfo());
    }

    @GetMapping("/maiorVenda")
    public ResponseEntity<HashMap<String, List<String>>> maiorVenda() throws NadaInseridoException, SQLException {
        return ResponseEntity.ok(service.maiorVenda());
    }

    @GetMapping("/menorVenda")
    public ResponseEntity<HashMap<String, List<String>>> menorVenda() throws NadaInseridoException, SQLException {
        return ResponseEntity.ok(service.menorVenda());
    }

    @GetMapping("/mediaVenda")
    public ResponseEntity<HashMap<String, List<String>>> mediaVenda() throws NadaInseridoException, SQLException {
        return ResponseEntity.ok(service.mediaVenda());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor_Cliente> atualizar(@RequestBody Fornecedor_Cliente atl, @PathVariable Long id) throws NadaInseridoException, IdNaoEncontradoException, SQLException {
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws SQLException, IdNaoEncontradoException {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
