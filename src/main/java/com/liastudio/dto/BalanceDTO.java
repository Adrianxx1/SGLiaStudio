package com.liastudio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private Double totalIngresos;
    private Double totalEgresos;
    private Double balance; // totalIngresos - totalEgresos
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer cantidadIngresos;
    private Integer cantidadEgresos;
}