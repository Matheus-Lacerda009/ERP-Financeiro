package net.financeiro.controller;

import net.financeiro.exceptions.IdNaoEncontradoException;
import net.financeiro.exceptions.NadaInseridoException;
import net.financeiro.model.Funcionario;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.Forma_PagamentoService;
import net.financeiro.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/funcionario")
public class FuncionarioController {
    private final FuncionarioService service = new FuncionarioService();

    @PostMapping
    public ResponseEntity<Funcionario> inserir(@RequestBody Funcionario ins)  {


        try{
            return ResponseEntity.ok(service.inserir(ins));
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarInfo()   {

        try{
            return ResponseEntity.ok(service.listarInfo());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping("/maiorVenda")
    public ResponseEntity<HashMap<String, List<String>>> maiorVenda() {

        try{
            return ResponseEntity.ok(service.maiorVenda());
        } catch ( NadaInseridoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @GetMapping("/menorVenda")
    public ResponseEntity<HashMap<String, List<String>>> menorVenda()  {

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
    public ResponseEntity<Funcionario> atualizar(@RequestBody Funcionario atl, @PathVariable Long id)  {

        try{
            return ResponseEntity.ok(service.atualizar(atl, id));
        } catch ( NadaInseridoException| IdNaoEncontradoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {


        try{
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (  IdNaoEncontradoException| SQLException e ){
            return ResponseEntity.ok(null);
        }

    }
}
