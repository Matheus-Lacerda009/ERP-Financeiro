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
    public ResponseEntity<Produto> inserir(@RequestBody Produto ins){


        try{
            return ResponseEntity.ok(service.inserir(ins));
        } catch ( FkNaoEncontradaException |ValorInvalidoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarInfo()  {

        try{
            return ResponseEntity.ok(service.listarInfo());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping("/maiorVenda")
    public ResponseEntity<HashMap<String, List<String>>> maiorVenda()  {

        try{
            return ResponseEntity.ok(service.maiorVenda());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping("/menorVenda")
    public ResponseEntity<HashMap<String, List<String>>> menorVenda() {

        try{
            return ResponseEntity.ok(service.menorVenda());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping("/mediaVenda")
    public ResponseEntity<HashMap<String, List<String>>> mediaVenda() {

        try{
            return ResponseEntity.ok(service.mediaVenda());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@RequestBody Produto atl, @PathVariable Long id) {

        try{
            return ResponseEntity.ok(service.atualizar(atl, id));
        } catch ( ValorInvalidoException| FkNaoEncontradaException| IdNaoEncontradoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws NadaInseridoException, SQLException, IdNaoEncontradoException {


        try{
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch ( NadaInseridoException| IdNaoEncontradoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }
}
