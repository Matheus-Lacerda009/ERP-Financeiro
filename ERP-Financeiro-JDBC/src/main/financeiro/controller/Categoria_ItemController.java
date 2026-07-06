package main.financeiro.controller;

import main.financeiro.model.Categoria_Item;
import main.financeiro.service.Categoria_ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoria_item")
public class Categoria_ItemController {
    private final Categoria_ItemService service = new Categoria_ItemService();

    @PostMapping
    public ResponseEntity<Categoria_Item> inserir(@RequestBody Categoria_Item ins) {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Categoria_Item>> listarInfo(){
        return ResponseEntity.ok(service.listarInfo());
    }

    @GetMapping("/maiorVenda")
    public ResponseEntity<HashMap<String, List<String>>> maiorVenda(){
        return ResponseEntity.ok(service.maiorVenda());
    }

    @GetMapping("/menorVenda")
    public ResponseEntity<HashMap<String, List<String>>> menorVenda(){
        return ResponseEntity.ok(service.menorVenda());
    }

    @GetMapping("/mediaVenda")
    public ResponseEntity<HashMap<String, List<String>>> mediaVenda(){
        return ResponseEntity.ok(service.maiorVenda());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria_Item> atualizar(@RequestBody Categoria_Item atl, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
