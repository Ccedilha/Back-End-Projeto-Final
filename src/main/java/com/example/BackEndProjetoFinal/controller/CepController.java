package com.example.BackEndProjetoFinal.controller;
import com.example.BackEndProjetoFinal.entity.CepEntity;
import com.example.BackEndProjetoFinal.repository.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*")
public class CepController {
    @Autowired
    private CepRepository comandos;

    @PostMapping
    private CepEntity salvarEndereco(
            @RequestBody CepEntity endereco){
        return comandos.save(endereco);
    }

    @GetMapping
    public List<CepEntity> listarEndereco(){
        return comandos.findAll();
    }

    @PutMapping("/{id}")
    public CepEntity atualizarEndereco(
            @PathVariable Integer id,
            @RequestBody CepEntity enderecoAtualizado
    ){
        CepEntity enderecoAtual = comandos.findById(id).orElseThrow();
        enderecoAtual.setCep(enderecoAtualizado.getCep());

        return comandos.save(enderecoAtual);
    }

    @DeleteMapping("/{id}")
    public String apagarEndereco(
            @PathVariable Integer id
    ){
        CepEntity endereco = comandos.findById(id).orElseThrow();
        String cep = endereco.getCep();

        comandos.deleteById(id);

        return "Cep "+cep+" deletado com sucesso!";
    }
}
