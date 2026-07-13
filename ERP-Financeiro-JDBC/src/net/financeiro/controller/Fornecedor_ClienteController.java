package net.financeiro.controller;

import net.financeiro.model.Fornecedor_Cliente;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.Fornecedor_ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/fornecedor_cliente")
public class Fornecedor_ClienteController {
    private final Fornecedor_ClienteService service = new Fornecedor_ClienteService();

    @PostMapping
    public ResponseEntity<Fornecedor_Cliente> inserir(@RequestBody Fornecedor_Cliente ins) {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor_Cliente>> listarInfo(){
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
        return ResponseEntity.ok(service.mediaVenda());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor_Cliente> atualizar(@RequestBody Fornecedor_Cliente atl, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
