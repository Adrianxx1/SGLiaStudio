package com.liastudio.repository;

import com.liastudio.model.MovimientoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoFinanciero, Long> {
    List<MovimientoFinanciero> findByTipo(String tipo);
    List<MovimientoFinanciero> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}