package com.example.BackEndProjetoFinal.controller;

import com.example.BackEndProjetoFinal.entity.HistoricoEntity;
import com.example.BackEndProjetoFinal.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/historico")
@CrossOrigin(origins = "*")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    // GET /api/historico
    @GetMapping
    public ResponseEntity<List<HistoricoEntity>> listar() {
        return ResponseEntity.ok(historicoService.listarTodos());
    }

    // POST /api/historico
    @PostMapping
    public ResponseEntity<HistoricoEntity> registrar(@RequestBody Map<String, String> body) {
        String cep = body.get("cep");
        HistoricoEntity salvo = historicoService.salvar(cep);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // DELETE /api/historico
    @DeleteMapping
    public ResponseEntity<Void> limpar() {
        historicoService.limparTudo();
        return ResponseEntity.noContent().build();
    }
}