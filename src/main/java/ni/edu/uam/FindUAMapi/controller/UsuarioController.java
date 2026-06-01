package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private final UsuarioService service;

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
