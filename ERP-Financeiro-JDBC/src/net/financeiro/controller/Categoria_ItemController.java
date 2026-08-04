package net.financeiro.controller;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.exceptions.ValorInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.service.Categoria_ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoria_item")
public class Categoria_ItemController {
    private final Categoria_ItemService service = new Categoria_ItemService();

    @PostMapping
    public ResponseEntity<Categoria_Item> inserir(@RequestBody Categoria_Item ins) throws SQLException, ValorInvalidoException {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Categoria_Item>> listarInfo() throws NadaInseridoException, SQLException {
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
    public ResponseEntity<Categoria_Item> atualizar(@RequestBody Categoria_Item atl, @PathVariable Long id) throws IdNaoEncontradoException, SQLException, ValorInvalidoException {
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws NadaInseridoException, IdNaoEncontradoException, SQLException {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
