package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        // arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "João", "joao@example.com");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // act
        Usuario resultado = usuarioService.buscarUsuarioPorId(id);

        // assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("João", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        // arrange
        Long idInexistente = 99L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // act + assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.buscarUsuarioPorId(idInexistente));

        assertEquals("Usuário não encontrado", ex.getMessage());
        verify(usuarioRepository, times(1)).findById(idInexistente);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        // arrange
        Usuario usuarioParaSalvar = new Usuario(null, "Maria", "maria@example.com");
        Usuario usuarioSalvo = new Usuario(1L, "Maria", "maria@example.com");

        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        // act
        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        // assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maria", resultado.getNome());
        assertEquals("maria@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }
}