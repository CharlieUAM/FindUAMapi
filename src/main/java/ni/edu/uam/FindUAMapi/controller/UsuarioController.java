package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private final UsuarioService service;

	public record LoginRequest(String correo, String contrasena) {}

	public record LoginResponse(Long id, String nombre, String apellido, String correo, String telefono) {}

	public UsuarioController(UsuarioService service) {
		this.service = service;
	}

	@GetMapping
	public List<Usuario> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Usuario findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	public Usuario save(@RequestBody Usuario usuario) {
		return service.save(usuario);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		Usuario usuario = service.login(request.correo(), request.contrasena());
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Correo o contraseña incorrectos."));
		}

		return ResponseEntity.ok(new LoginResponse(
				usuario.getId(),
				usuario.getNombre(),
				usuario.getApellido(),
				usuario.getCorreo(),
				usuario.getTelefono()
		));
	}

	@PutMapping("/{id}")
	public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
		usuario.setId(id);
		return service.save(usuario);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
