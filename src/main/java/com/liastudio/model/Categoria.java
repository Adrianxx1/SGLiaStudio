package com.liastudio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "categoria_servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;
    
    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;
    
    // Relación con Servicio: Una categoría tiene muchos servicios
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Servicio> servicios;
}