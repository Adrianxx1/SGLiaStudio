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
public class Movimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;
    
    @Column(nullable = false, length = 10)
    private String tipo; // INGRESO o GASTO
    
    @Column(nullable = false)
    private Double monto;
    
    @Column(columnDefinition = "TEXT")
    private String concepto;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    // Relación opcional: Un movimiento puede estar relacionado a una cita
    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;
}