package com.liastudio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaDTO {
    private Long idCita;
    private LocalDateTime fecha;
    private String estado; 
    // Datos del servicio
    private Long idServicio;
    private String nombreServicio;
    private Double precioServicio;
    private Integer duracionMinutos;
    
    // Datos del cliente
    private Long idCliente;
    private String nombreCliente;
    private String telefonoCliente;
    
    // Datos del usuario que agenda
    private Long idUsuario;
    private String nombreUsuario;
}