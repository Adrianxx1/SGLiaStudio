package com.liastudio.service;

import com.liastudio.config.JwtUtil;
import com.liastudio.dto.AuthResponse;
import com.liastudio.dto.LoginRequest;
import com.liastudio.dto.RegisterRequest;
import com.liastudio.model.Usuario;
import com.liastudio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Registrar nuevo usuario
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el teléfono ya existe
        if (usuarioRepository.existsByTelefono(request.getTelefono())) {
            throw new RuntimeException("El teléfono ya está registrado");
        }
        
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setTelefono(request.getTelefono());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("ADMIN");
        
        // Guardar en BD
        usuarioRepository.save(usuario);
        
        // Generar token
        String token = jwtUtil.generateToken(usuario.getTelefono(), usuario.getRol());
        
        // Retornar respuesta
        return new AuthResponse(token, usuario.getTelefono(), usuario.getNombre(), usuario.getRol());
    }
    
    // Login
    public AuthResponse login(LoginRequest request) {
        // Buscar usuario por teléfono
        Usuario usuario = usuarioRepository.findByTelefono(request.getTelefono())
                .orElseThrow(() -> new RuntimeException("Teléfono o contraseña incorrectos"));
        
        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Teléfono o contraseña incorrectos");
        }
        
        // Generar token
        String token = jwtUtil.generateToken(usuario.getTelefono(), usuario.getRol());
        
        // Retornar respuesta
        return new AuthResponse(token, usuario.getTelefono(), usuario.getNombre(), usuario.getRol());
    }
}