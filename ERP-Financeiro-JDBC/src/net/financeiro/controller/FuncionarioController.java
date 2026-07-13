package net.financeiro.controller;

import net.financeiro.model.Funcionario;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.Forma_PagamentoService;
import net.financeiro.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/funcionario")
public class FuncionarioController {
    private final FuncionarioService service = new FuncionarioService();

    @PostMapping
    public ResponseEntity<Funcionario> inserir(@RequestBody Funcionario ins) {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarInfo(){
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
    public ResponseEntity<Funcionario> atualizar(@RequestBody Funcionario atl, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
