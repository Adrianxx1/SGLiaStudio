package com.liastudio.repository;

import com.liastudio.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByTipo(String tipo);
    List<Movimiento> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT SUM(CASE WHEN m.tipo = 'INGRESO' THEN m.monto ELSE -m.monto END) FROM Movimiento m")
    Double calcularBalance();
}