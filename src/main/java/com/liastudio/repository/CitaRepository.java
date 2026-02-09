package com.liastudio.repository;

import com.liastudio.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClienteIdCliente(Long idCliente);
    List<Cita> findByServicioIdServicio(Long idServicio);
    List<Cita> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByEstado(String estado);
}