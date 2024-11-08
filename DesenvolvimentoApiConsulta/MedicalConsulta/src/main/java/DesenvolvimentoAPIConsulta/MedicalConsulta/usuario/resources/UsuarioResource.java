package DesenvolvimentoAPIConsulta.MedicalConsulta.usuario.resources;

import DesenvolvimentoAPIConsulta.MedicalConsulta.usuario.UsuarioService;
import DesenvolvimentoAPIConsulta.MedicalConsulta.usuario.domain.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping(value = "/usuario")
@RestController
public class UsuarioResource {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario Usuarionovo = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Usuarionovo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarClientes() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok().body(usuario);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Usuario> atualizarCliente(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario upCliente = usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.ok().body(upCliente);

    }
}