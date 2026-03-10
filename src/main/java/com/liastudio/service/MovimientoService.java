package com.liastudio.service;

import com.liastudio.dto.BalanceDTO;
import com.liastudio.dto.MovimientoDTO;
import com.liastudio.model.Cita;
import com.liastudio.model.MovimientoFinanciero;
import com.liastudio.model.Usuario;
import com.liastudio.repository.CitaRepository;
import com.liastudio.repository.MovimientoRepository;
import com.liastudio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Listar todos los movimientos
    public List<MovimientoDTO> getAllMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener movimiento por ID
    public MovimientoDTO getMovimientoById(Long id) {
        MovimientoFinanciero movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        return convertToDTO(movimiento);
    }
    
    // Obtener movimientos por tipo
    public List<MovimientoDTO> getMovimientosByTipo(String tipo) {
        return movimientoRepository.findByTipo(tipo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener movimientos por rango de fechas
    public List<MovimientoDTO> getMovimientosByFechaRange(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Calcular balance total
    public BalanceDTO getBalanceTotal() {
        List<MovimientoFinanciero> ingresos = movimientoRepository.findByTipo("INGRESO");
        List<MovimientoFinanciero> egresos = movimientoRepository.findByTipo("EGRESO");
        
        Double totalIngresos = ingresos.stream()
                .mapToDouble(MovimientoFinanciero::getMonto)
                .sum();
        
        Double totalEgresos = egresos.stream()
                .mapToDouble(MovimientoFinanciero::getMonto)
                .sum();
        
        BalanceDTO balance = new BalanceDTO();
        balance.setTotalIngresos(totalIngresos);
        balance.setTotalEgresos(totalEgresos);
        balance.setBalance(totalIngresos - totalEgresos);
        balance.setCantidadIngresos(ingresos.size());
        balance.setCantidadEgresos(egresos.size());
        
        return balance;
    }
    
    // Calcular balance por rango de fechas
    public BalanceDTO getBalanceByFechaRange(LocalDateTime inicio, LocalDateTime fin) {
        List<MovimientoFinanciero> movimientos = movimientoRepository.findByFechaBetween(inicio, fin);
        
        List<MovimientoFinanciero> ingresos = movimientos.stream()
                .filter(m -> "INGRESO".equals(m.getTipo()))
                .collect(Collectors.toList());
        
        List<MovimientoFinanciero> egresos = movimientos.stream()
                .filter(m -> "EGRESO".equals(m.getTipo()))
                .collect(Collectors.toList());
        
        Double totalIngresos = ingresos.stream()
                .mapToDouble(MovimientoFinanciero::getMonto)
                .sum();
        
        Double totalEgresos = egresos.stream()
                .mapToDouble(MovimientoFinanciero::getMonto)
                .sum();
        
        BalanceDTO balance = new BalanceDTO();
        balance.setTotalIngresos(totalIngresos);
        balance.setTotalEgresos(totalEgresos);
        balance.setBalance(totalIngresos - totalEgresos);
        balance.setFechaInicio(inicio);
        balance.setFechaFin(fin);
        balance.setCantidadIngresos(ingresos.size());
        balance.setCantidadEgresos(egresos.size());
        
        return balance;
    }
    
    // Crear movimiento
    public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {
        Usuario usuario = usuarioRepository.findById(movimientoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + movimientoDTO.getIdUsuario()));
        
        MovimientoFinanciero movimiento = new MovimientoFinanciero();
        movimiento.setTipo(movimientoDTO.getTipo());
        movimiento.setMonto(movimientoDTO.getMonto());
        movimiento.setDescripcion(movimientoDTO.getDescripcion());
        movimiento.setFecha(movimientoDTO.getFecha() != null ? movimientoDTO.getFecha() : LocalDateTime.now());
        movimiento.setUsuario(usuario);
        
        // Si hay una cita asociada (ingreso por servicio)
        if (movimientoDTO.getIdCita() != null) {
            Cita cita = citaRepository.findById(movimientoDTO.getIdCita())
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + movimientoDTO.getIdCita()));
            movimiento.setCita(cita);
        }
        
        MovimientoFinanciero savedMovimiento = movimientoRepository.save(movimiento);
        return convertToDTO(savedMovimiento);
    }
    
    // Actualizar movimiento
    public MovimientoDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO) {
        MovimientoFinanciero movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        
        if (movimientoDTO.getTipo() != null) {
            movimiento.setTipo(movimientoDTO.getTipo());
        }
        
        if (movimientoDTO.getMonto() != null) {
            movimiento.setMonto(movimientoDTO.getMonto());
        }
        
        if (movimientoDTO.getDescripcion() != null) {
            movimiento.setDescripcion(movimientoDTO.getDescripcion());
        }
        
        if (movimientoDTO.getFecha() != null) {
            movimiento.setFecha(movimientoDTO.getFecha());
        }
        
        MovimientoFinanciero updatedMovimiento = movimientoRepository.save(movimiento);
        return convertToDTO(updatedMovimiento);
    }
    
    // Eliminar movimiento
    public void deleteMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RuntimeException("Movimiento no encontrado con ID: " + id);
        }
        movimientoRepository.deleteById(id);
    }
    
    // Convertir Entidad a DTO
    private MovimientoDTO convertToDTO(MovimientoFinanciero movimiento) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setIdMovimiento(movimiento.getIdMovimiento());
        dto.setTipo(movimiento.getTipo());
        dto.setMonto(movimiento.getMonto());
        dto.setDescripcion(movimiento.getDescripcion());
        dto.setFecha(movimiento.getFecha());
        
        // Datos del usuario
        dto.setIdUsuario(movimiento.getUsuario().getIdUsuario());
        dto.setNombreUsuario(movimiento.getUsuario().getNombre());
        
        // Datos de la cita (si existe)
        if (movimiento.getCita() != null) {
            dto.setIdCita(movimiento.getCita().getIdCita());
            dto.setNombreCliente(movimiento.getCita().getCliente().getNombre());
            dto.setNombreServicio(movimiento.getCita().getServicio().getNombre());
        }
        
        return dto;
    }
}