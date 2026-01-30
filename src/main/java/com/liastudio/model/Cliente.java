package com.liastudio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 15)
    private String telefono;
    
    @Column(length = 100)
    private String email;
}