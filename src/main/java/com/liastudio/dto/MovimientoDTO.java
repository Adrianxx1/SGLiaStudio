package com.liastudio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private Long idMovimiento;
    private String tipo; // INGRESO o EGRESO
    private Double monto;
    private String descripcion;
    private LocalDateTime fecha;
    
    // Datos de la cita (si es un ingreso por servicio)
    private Long idCita;
    private String nombreCliente;
    private String nombreServicio;
    
    // Datos del usuario que registró el movimiento
    private Long idUsuario;
    private String nombreUsuario;
}