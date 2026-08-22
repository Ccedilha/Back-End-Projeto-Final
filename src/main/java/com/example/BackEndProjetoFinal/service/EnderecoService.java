package com.example.BackEndProjetoFinal.service;

import com.example.BackEndProjetoFinal.entity.EnderecoEntity;
import com.example.BackEndProjetoFinal.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<EnderecoEntity> listarTodos() {
        return enderecoRepository.findAll();
    }

    public EnderecoEntity salvar(EnderecoEntity endereco) {
        return enderecoRepository.save(endereco);
    }

    public EnderecoEntity atualizarApelido(int id, String novoApelido) {
        Optional<EnderecoEntity> enderecoExistente = enderecoRepository.findById(id);

        if (enderecoExistente.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado com id: " + id);
        }

        EnderecoEntity endereco = enderecoExistente.get();
        endereco.setApelido(novoApelido);
        return enderecoRepository.save(endereco);
    }

    public void remover(int id) {
        enderecoRepository.deleteById(id);
    }
}