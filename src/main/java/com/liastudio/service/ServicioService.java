package com.liastudio.service;

import com.liastudio.dto.ServicioDTO;
import com.liastudio.model.Categoria;
import com.liastudio.model.Servicio;
import com.liastudio.repository.CategoriaRepository;
import com.liastudio.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<ServicioDTO> getAllServicios() {
        return servicioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ServicioDTO getServicioById(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        return convertToDTO(servicio);
    }
    
    public List<ServicioDTO> getServiciosByCategoria(Long idCategoria) {
        return servicioRepository.findByCategoriaIdCategoria(idCategoria).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ServicioDTO createServicio(ServicioDTO servicioDTO) {
        Categoria categoria = categoriaRepository.findById(servicioDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + servicioDTO.getIdCategoria()));
        
        Servicio servicio = convertToEntity(servicioDTO);
        servicio.setCategoria(categoria);
        
        Servicio savedServicio = servicioRepository.save(servicio);
        return convertToDTO(savedServicio);
    }
    
    public ServicioDTO updateServicio(Long id, ServicioDTO servicioDTO) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        
        Categoria categoria = categoriaRepository.findById(servicioDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + servicioDTO.getIdCategoria()));
        
        servicio.setNombre(servicioDTO.getNombre());
        servicio.setDescripcion(servicioDTO.getDescripcion());
        servicio.setPrecio(servicioDTO.getPrecio());
        servicio.setDuracionMinutos(servicioDTO.getDuracionMinutos());
        servicio.setCategoria(categoria);
        
        Servicio updatedServicio = servicioRepository.save(servicio);
        return convertToDTO(updatedServicio);
    }
    
    public void deleteServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        servicioRepository.deleteById(id);
    }
    
    private ServicioDTO convertToDTO(Servicio servicio) {
        return new ServicioDTO(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getDuracionMinutos(),
                servicio.getCategoria().getIdCategoria(),
                servicio.getCategoria().getNombreCategoria()
        );
    }
    
    private Servicio convertToEntity(ServicioDTO dto) {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(dto.getIdServicio());
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setDuracionMinutos(dto.getDuracionMinutos());
        return servicio;
    }
}