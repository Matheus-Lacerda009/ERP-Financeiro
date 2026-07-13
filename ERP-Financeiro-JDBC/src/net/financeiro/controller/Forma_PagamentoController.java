package net.financeiro.controller;

import net.financeiro.model.Forma_Pagamento;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.Forma_PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/forma_pagamento")
public class Forma_PagamentoController {
    private final Forma_PagamentoService service = new Forma_PagamentoService();

    @PostMapping
    public ResponseEntity<Forma_Pagamento> inserir(@RequestBody Forma_Pagamento ins) {
        return ResponseEntity.ok(service.inserir(ins));
    }

    @GetMapping
    public ResponseEntity<List<Forma_Pagamento>> listarInfo(){
        return ResponseEntity.ok(service.listarInfo());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Forma_Pagamento> atualizar(@RequestBody Forma_Pagamento atl, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(atl, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
