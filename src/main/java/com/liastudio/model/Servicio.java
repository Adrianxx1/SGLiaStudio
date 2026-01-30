package com.liastudio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    private Double precio;
    
    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;
    
    // Relación con Categoria: Muchos servicios pertenecen a UNA categoría
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}