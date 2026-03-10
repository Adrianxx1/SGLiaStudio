package com.liastudio.service;

import com.liastudio.dto.CitaDTO;
import com.liastudio.model.Cita;
import com.liastudio.model.Cliente;
import com.liastudio.model.Servicio;
import com.liastudio.model.Usuario;
import com.liastudio.repository.CitaRepository;
import com.liastudio.repository.ClienteRepository;
import com.liastudio.repository.ServicioRepository;
import com.liastudio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Listar todas las citas
    public List<CitaDTO> getAllCitas() {
        return citaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener cita por ID
    public CitaDTO getCitaById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        return convertToDTO(cita);
    }
    
    // Obtener citas por cliente
    public List<CitaDTO> getCitasByCliente(Long idCliente) {
        return citaRepository.findByClienteIdCliente(idCliente).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener citas por servicio
    public List<CitaDTO> getCitasByServicio(Long idServicio) {
        return citaRepository.findByServicioIdServicio(idServicio).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener citas por estado
    public List<CitaDTO> getCitasByEstado(String estado) {
        return citaRepository.findByEstado(estado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener citas por rango de fechas
    public List<CitaDTO> getCitasByFechaRange(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Crear cita
    public CitaDTO createCita(CitaDTO citaDTO) {
        // Validar que existan cliente, servicio y usuario
        Cliente cliente = clienteRepository.findById(citaDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + citaDTO.getIdCliente()));
        
        Servicio servicio = servicioRepository.findById(citaDTO.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + citaDTO.getIdServicio()));
        
        Usuario usuario = usuarioRepository.findById(citaDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + citaDTO.getIdUsuario()));
        
        // Crear nueva cita
        Cita cita = new Cita();
        cita.setFecha(citaDTO.getFecha());
        cita.setEstado(citaDTO.getEstado() != null ? citaDTO.getEstado() : "PENDIENTE");
        cita.setCliente(cliente);
        cita.setServicio(servicio);
        cita.setUsuario(usuario);
        
        Cita savedCita = citaRepository.save(cita);
        return convertToDTO(savedCita);
    }
    
    // Actualizar cita
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        // Actualizar fecha si se proporciona
        if (citaDTO.getFecha() != null) {
            cita.setFecha(citaDTO.getFecha());
        }
        
        // Actualizar estado si se proporciona
        if (citaDTO.getEstado() != null) {
            cita.setEstado(citaDTO.getEstado());
        }
        
        // Actualizar servicio si se proporciona
        if (citaDTO.getIdServicio() != null) {
            Servicio servicio = servicioRepository.findById(citaDTO.getIdServicio())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + citaDTO.getIdServicio()));
            cita.setServicio(servicio);
        }
        
        // Actualizar cliente si se proporciona
        if (citaDTO.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(citaDTO.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + citaDTO.getIdCliente()));
            cita.setCliente(cliente);
        }
        
        Cita updatedCita = citaRepository.save(cita);
        return convertToDTO(updatedCita);
    }
    
    // Cambiar estado de cita
    public CitaDTO cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        cita.setEstado(nuevoEstado);
        Cita updatedCita = citaRepository.save(cita);
        return convertToDTO(updatedCita);
    }
    
    // Eliminar cita
    public void deleteCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }
    
    // Convertir Entidad a DTO
    private CitaDTO convertToDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setFecha(cita.getFecha());
        dto.setEstado(cita.getEstado());
        
        // Datos del servicio
        dto.setIdServicio(cita.getServicio().getIdServicio());
        dto.setNombreServicio(cita.getServicio().getNombre());
        dto.setPrecioServicio(cita.getServicio().getPrecio());
        dto.setDuracionMinutos(cita.getServicio().getDuracionMinutos());
        
        // Datos del cliente
        dto.setIdCliente(cita.getCliente().getIdCliente());
        dto.setNombreCliente(cita.getCliente().getNombre());
        dto.setTelefonoCliente(cita.getCliente().getTelefono());
        
        // Datos del usuario
        dto.setIdUsuario(cita.getUsuario().getIdUsuario());
        dto.setNombreUsuario(cita.getUsuario().getNombre());
        
        return dto;
    }
}