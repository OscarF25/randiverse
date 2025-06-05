package com.randiverse.auth_system;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.randiverse.authsystem.model.Usuario;
import com.randiverse.authsystem.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testRegistroUsuario() throws Exception {
        MockitoAnnotations.openMocks(this);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario("testuser");
        nuevoUsuario.setNombre("Test User");
        nuevoUsuario.setEmail("test@example.com");
        nuevoUsuario.setPassword("password123");

        when(usuarioRepository.existsByNombreUsuario(any())).thenReturn(false);
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.save(any())).thenReturn(nuevoUsuario);

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value(true));
    }
    }
