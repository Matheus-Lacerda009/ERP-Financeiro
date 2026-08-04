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
    public ResponseEntity<Categoria_Item> inserir(@RequestBody Categoria_Item ins) {
        try {
            return ResponseEntity.ok(service.inserir(ins));
        } catch(Exception e){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Categoria_Item>> listarInfo()  {
        try {
            return ResponseEntity.ok(service.listarInfo());
        } catch (SQLException | NadaInseridoException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/maiorVenda")
    public ResponseEntity<HashMap<String, List<String>>> maiorVenda()  {
        try {
            return ResponseEntity.ok(service.maiorVenda());
        } catch (SQLException | NadaInseridoException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/menorVenda")
    public ResponseEntity<HashMap<String, List<String>>> menorVenda() throws NadaInseridoException, SQLException {
        try {
            return ResponseEntity.ok(service.menorVenda());
        } catch (SQLException | NadaInseridoException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/mediaVenda")
    public ResponseEntity<HashMap<String, List<String>>> mediaVenda() throws NadaInseridoException, SQLException {
        try{
            return ResponseEntity.ok(service.mediaVenda());
        } catch (SQLException | NadaInseridoException e) {
            return ResponseEntity.ok(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria_Item> atualizar(@RequestBody Categoria_Item atl, @PathVariable Long id)   {
        try{
            return ResponseEntity.ok(service.atualizar(atl, id));
        } catch (SQLException | IdNaoEncontradoException | ValorInvalidoException e) {
            return ResponseEntity.ok(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)   {
        try{
            service.deletar(id);
            return ResponseEntity.noContent().build();

        }catch (NadaInseridoException| IdNaoEncontradoException| SQLException e){
            return ResponseEntity.ok(null);
        }



    }
}
