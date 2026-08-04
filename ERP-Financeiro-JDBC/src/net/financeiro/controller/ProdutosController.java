package net.financeiro.controller;

import net.financeiro.exceptions.FkNaoEncontradaException;
import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Produto;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.ProdutosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/produto")
public class ProdutosController {
    private final ProdutosService service = new ProdutosService();

    @PostMapping
    public ResponseEntity<Produto> inserir(@RequestBody Produto ins) throws SQLException, ValorInvalidoException, FkNaoEncontradaException {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarInfo() throws NadaInseridoException, SQLException {
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
    public ResponseEntity<Produto> atualizar(@RequestBody Produto atl, @PathVariable Long id) throws IdNaoEncontradoException, SQLException, ValorInvalidoException, FkNaoEncontradaException {
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws NadaInseridoException, SQLException, IdNaoEncontradoException {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
