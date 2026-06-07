package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.dto.LoginRequest;
import ni.edu.uam.FindUAMapi.entity.Usuario;
import ni.edu.uam.FindUAMapi.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(
            UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario guardar(
            @RequestBody Usuario usuario) {

        return repository.save(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(
            @RequestBody LoginRequest request) {

        Usuario usuario =
                repository.findByCorreoUam(
                        request.getCorreoUam()
                );

        if (
                usuario != null &&
                        usuario.getPassword().equals(
                                request.getPassword()
                        )
        ) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }
}