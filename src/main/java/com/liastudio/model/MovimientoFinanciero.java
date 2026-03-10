package com.liastudio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_financiero")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoFinanciero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;
    
    @Column(nullable = false, length = 20)
    private String tipo; // INGRESO o EGRESO
    
    @Column(nullable = false)
    private Double monto;
    
    @Column(length = 255)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}