package com.liastudio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer duracionMinutos;
    private Long idCategoria;
    private String nombreCategoria; 
}