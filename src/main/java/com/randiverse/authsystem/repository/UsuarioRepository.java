package com.randiverse.authsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.randiverse.authsystem.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByEmail(String email);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
}
