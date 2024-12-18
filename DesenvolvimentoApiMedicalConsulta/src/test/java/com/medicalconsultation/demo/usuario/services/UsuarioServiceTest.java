package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.apimedicalconsulta.usuario.domain.Usuario;
import com.medicalconsultation.apimedicalconsulta.usuario.repository.UsuarioRepository;
import com.medicalconsultation.apimedicalconsulta.usuario.services.UsuarioService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("Arsidney");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Arsidney", resultado.getNomeUsuario());

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void buscarUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdUsuario());
    }

    @Test
    void buscarUsuarioInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> usuarioService.buscarUsuario(1L));
    }

    @Test
    void listarUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setNomeUsuario("Pedro");
        Usuario usuario2 = new Usuario();
        usuario2.setNomeUsuario("Henrique");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Pedro", resultado.get(0).getNomeUsuario());
        assertEquals("Henrique", resultado.get(1).getNomeUsuario());
    }

    @Test
    void deletarUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void atualizarUsuario() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(1L);
        usuarioExistente.setNomeUsuario("Antigo");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNomeUsuario("Novo");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.atualizarUsuario(usuarioAtualizado, 1L);

        assertNotNull(resultado);
        assertEquals("Novo", resultado.getNomeUsuario());
    }
}