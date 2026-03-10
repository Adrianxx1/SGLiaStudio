package com.liastudio.controller;

import com.liastudio.dto.BalanceDTO;
import com.liastudio.dto.MovimientoDTO;
import com.liastudio.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {
    
    @Autowired
    private MovimientoService movimientoService;
    
    // GET /api/movimientos - Listar todos
    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        List<MovimientoDTO> movimientos = movimientoService.getAllMovimientos();
        return ResponseEntity.ok(movimientos);
    }
    
    // GET /api/movimientos/{id} - Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> getMovimientoById(@PathVariable Long id) {
        try {
            MovimientoDTO movimiento = movimientoService.getMovimientoById(id);
            return ResponseEntity.ok(movimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET /api/movimientos/tipo/{tipo} - Por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimientoDTO>> getMovimientosByTipo(@PathVariable String tipo) {
        List<MovimientoDTO> movimientos = movimientoService.getMovimientosByTipo(tipo);
        return ResponseEntity.ok(movimientos);
    }
    
    // GET /api/movimientos/fecha?inicio=...&fin=...
    @GetMapping("/fecha")
    public ResponseEntity<List<MovimientoDTO>> getMovimientosByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<MovimientoDTO> movimientos = movimientoService.getMovimientosByFechaRange(inicio, fin);
        return ResponseEntity.ok(movimientos);
    }
    
    // GET /api/movimientos/balance - Balance total
    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getBalanceTotal() {
        BalanceDTO balance = movimientoService.getBalanceTotal();
        return ResponseEntity.ok(balance);
    }
    
    // GET /api/movimientos/balance/rango?inicio=...&fin=...
    @GetMapping("/balance/rango")
    public ResponseEntity<BalanceDTO> getBalanceByRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        BalanceDTO balance = movimientoService.getBalanceByFechaRange(inicio, fin);
        return ResponseEntity.ok(balance);
    }
    
    // POST /api/movimientos - Crear
    @PostMapping
    public ResponseEntity<MovimientoDTO> createMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        try {
            MovimientoDTO nuevoMovimiento = movimientoService.createMovimiento(movimientoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /api/movimientos/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> updateMovimiento(@PathVariable Long id, @RequestBody MovimientoDTO movimientoDTO) {
        try {
            MovimientoDTO movimientoActualizado = movimientoService.updateMovimiento(id, movimientoDTO);
            return ResponseEntity.ok(movimientoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/movimientos/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.deleteMovimiento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
