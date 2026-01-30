package com.liastudio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(length = 20)
    private String estado; // PENDIENTE, CONFIRMADA, CANCELADA, REALIZADA
    
    // Relación: Una cita pertenece a UN servicio
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
    
    // Relación: Una cita pertenece a UN cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    // Relación: Una cita es programada por UN usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}