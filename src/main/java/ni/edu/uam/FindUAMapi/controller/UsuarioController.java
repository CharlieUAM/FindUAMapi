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

        System.out.println("LOGIN RECIBIDO");
        System.out.println(request.getCorreoUam());
        System.out.println(request.getPassword());

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
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario datosActualizados
    ) {

        return repository.findById(id)
                .map(usuario -> {

                    usuario.setNombre(
                            datosActualizados.getNombre()
                    );

                    usuario.setApellido(
                            datosActualizados.getApellido()
                    );

                    usuario.setTelefono(
                            datosActualizados.getTelefono()
                    );

                    return ResponseEntity.ok(
                            repository.save(usuario)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }
}