package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.Models.Publicacion;
import ni.edu.uam.FindUAMapi.service.PublicacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

	private final PublicacionService service;

	public PublicacionController(PublicacionService service) {
		this.service = service;
	}

	@GetMapping
	public List<Publicacion> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Publicacion findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	public Publicacion save(@RequestBody Publicacion publicacion) {
		return service.save(publicacion);
	}

	@PutMapping("/{id}")
	public Publicacion update(@PathVariable Long id, @RequestBody Publicacion publicacion) {
		publicacion.setId(id);
		return service.save(publicacion);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
