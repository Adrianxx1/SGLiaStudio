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
    
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByTelefono(request.getTelefono())) {
            throw new RuntimeException("El teléfono ya está registrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setTelefono(request.getTelefono());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("ADMIN");
        
        usuarioRepository.save(usuario);
        
        String token = jwtUtil.generateToken(usuario.getTelefono(), usuario.getRol());
        
        return new AuthResponse(token, usuario.getTelefono(), usuario.getNombre(), usuario.getRol());
    }
    
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByTelefono(request.getTelefono())
                .orElseThrow(() -> new RuntimeException("Teléfono o contraseña incorrectos"));
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Teléfono o contraseña incorrectos");
        }
        
        String token = jwtUtil.generateToken(usuario.getTelefono(), usuario.getRol());
        
        return new AuthResponse(token, usuario.getTelefono(), usuario.getNombre(), usuario.getRol());
    }
}