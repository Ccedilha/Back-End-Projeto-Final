package com.example.BackEndProjetoFinal.service;

import com.example.BackEndProjetoFinal.entity.HistoricoEntity;
import com.example.BackEndProjetoFinal.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    public List<HistoricoEntity> listarTodos() {
        return historicoRepository.findAll();
    }

    public HistoricoEntity salvar(String cep) {
        HistoricoEntity novoRegistro = new HistoricoEntity(cep, LocalDateTime.now());
        return historicoRepository.save(novoRegistro);
    }

    public void limparTudo() {
        historicoRepository.deleteAll();
    }
}