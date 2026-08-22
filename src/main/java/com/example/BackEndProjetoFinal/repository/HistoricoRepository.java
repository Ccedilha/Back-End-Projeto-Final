package com.example.BackEndProjetoFinal.repository;

import com.example.BackEndProjetoFinal.entity.HistoricoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<HistoricoEntity, Long> {
}