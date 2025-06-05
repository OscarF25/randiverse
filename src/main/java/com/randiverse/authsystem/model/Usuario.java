package com.randiverse.authsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;
    
    @Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
    private String nombreUsuario;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean verificado;
    
    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;
    
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer rango;
}