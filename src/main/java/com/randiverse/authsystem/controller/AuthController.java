package com.randiverse.authsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.HashMap;
import java.util.Map;
import com.randiverse.authsystem.repository.UsuarioRepository;
import com.randiverse.authsystem.model.Usuario;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        
        String nombreUsuario = credentials.get("nombreUsuario");
        String password = credentials.get("password");
        
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        
        if (usuario == null) {
            response.put("estado", false);
            response.put("respuesta", "Usuario no encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            response.put("estado", false);
            response.put("respuesta", "Contraseña incorrecta");
            return ResponseEntity.badRequest().body(response);
        }
        
        response.put("estado", true);
        response.put("respuesta", usuario.getId());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registro(@RequestBody Usuario nuevoUsuario) {
        Map<String, Object> response = new HashMap<>();
        
        // Verificar si el nombre de usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            response.put("estado", false);
            response.put("respuesta", "El nombre de usuario ya está en uso");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail())) {
            response.put("estado", false);
            response.put("respuesta", "El email ya está en uso");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Encriptar la contraseña
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        // Establecer valores por defecto
        nuevoUsuario.setVerificado(false);
        nuevoUsuario.setRango(0);
        
        // Guardar el nuevo usuario
        usuarioRepository.save(nuevoUsuario);
        
        response.put("estado", true);
        response.put("respuesta", "Usuario registrado con éxito");
        return ResponseEntity.ok(response);
    }
}