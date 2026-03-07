package com.liastudio.controller;

import com.liastudio.dto.ServicioDTO;
import com.liastudio.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {
    
    @Autowired
    private ServicioService servicioService;
    
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        List<ServicioDTO> servicios = servicioService.getAllServicios();
        return ResponseEntity.ok(servicios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
        try {
            ServicioDTO servicio = servicioService.getServicioById(id);
            return ResponseEntity.ok(servicio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ServicioDTO>> getServiciosByCategoria(@PathVariable Long idCategoria) {
        List<ServicioDTO> servicios = servicioService.getServiciosByCategoria(idCategoria);
        return ResponseEntity.ok(servicios);
    }
    
    @PostMapping
    public ResponseEntity<ServicioDTO> createServicio(@RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO nuevoServicio = servicioService.createServicio(servicioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> updateServicio(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO servicioActualizado = servicioService.updateServicio(id, servicioDTO);
            return ResponseEntity.ok(servicioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        try {
            servicioService.deleteServicio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
