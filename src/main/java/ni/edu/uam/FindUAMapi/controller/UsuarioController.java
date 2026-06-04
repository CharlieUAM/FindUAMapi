package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.entity.Usuario;
import ni.edu.uam.FindUAMapi.repository.UsuarioRepository;
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
}