package com.example.BackEndProjetoFinal.controller;

import com.example.BackEndProjetoFinal.entity.EnderecoEntity;
import com.example.BackEndProjetoFinal.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // GET /api/enderecos
    @GetMapping
    public ResponseEntity<List<EnderecoEntity>> listar() {
        return ResponseEntity.ok(enderecoService.listarTodos());
    }

    // POST /api/enderecos
    @PostMapping
    public ResponseEntity<EnderecoEntity> criar(@RequestBody EnderecoEntity endereco) {
        EnderecoEntity salvo = enderecoService.salvar(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // PUT /api/enderecos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoEntity> atualizar(@PathVariable int id, @RequestBody Map<String, String> body) {
        String novoApelido = body.get("apelido");
        EnderecoEntity atualizado = enderecoService.atualizarApelido(id, novoApelido);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /api/enderecos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        enderecoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}