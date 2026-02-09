package com.liastudio.repository;

import com.liastudio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByTelefono(String telefono);  // ← Buscar por teléfono
    boolean existsByTelefono(String telefono);  // ← Verificar si existe
    Optional<Cliente> findByNombreContainingIgnoreCase(String nombre);
}