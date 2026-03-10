package com.liastudio.controller;

import com.liastudio.dto.CitaDTO;
import com.liastudio.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
    // GET /api/citas - Listar todas
    @GetMapping
    public ResponseEntity<List<CitaDTO>> getAllCitas() {
        List<CitaDTO> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }
    
    // GET /api/citas/{id} - Obtener una
    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> getCitaById(@PathVariable Long id) {
        try {
            CitaDTO cita = citaService.getCitaById(id);
            return ResponseEntity.ok(cita);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET /api/citas/cliente/{idCliente} - Por cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<CitaDTO>> getCitasByCliente(@PathVariable Long idCliente) {
        List<CitaDTO> citas = citaService.getCitasByCliente(idCliente);
        return ResponseEntity.ok(citas);
    }
    
    // GET /api/citas/servicio/{idServicio} - Por servicio
    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<CitaDTO>> getCitasByServicio(@PathVariable Long idServicio) {
        List<CitaDTO> citas = citaService.getCitasByServicio(idServicio);
        return ResponseEntity.ok(citas);
    }
    
    // GET /api/citas/estado/{estado} - Por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> getCitasByEstado(@PathVariable String estado) {
        List<CitaDTO> citas = citaService.getCitasByEstado(estado);
        return ResponseEntity.ok(citas);
    }
    
    // GET /api/citas/fecha?inicio=2026-03-07T10:00:00&fin=2026-03-07T18:00:00
    @GetMapping("/fecha")
    public ResponseEntity<List<CitaDTO>> getCitasByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<CitaDTO> citas = citaService.getCitasByFechaRange(inicio, fin);
        return ResponseEntity.ok(citas);
    }
    
    // POST /api/citas - Crear
    @PostMapping
    public ResponseEntity<CitaDTO> createCita(@RequestBody CitaDTO citaDTO) {
        try {
            CitaDTO nuevaCita = citaService.createCita(citaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /api/citas/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        try {
            CitaDTO citaActualizada = citaService.updateCita(id, citaDTO);
            return ResponseEntity.ok(citaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PUT /api/citas/{id}/estado - Cambiar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaDTO> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            CitaDTO citaActualizada = citaService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(citaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/citas/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        try {
            citaService.deleteCita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
