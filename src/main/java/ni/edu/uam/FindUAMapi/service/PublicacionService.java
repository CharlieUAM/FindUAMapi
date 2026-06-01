package ni.edu.uam.FindUAMapi.service;

import ni.edu.uam.FindUAMapi.Models.Publicacion;
import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.Repository.PublicacionRepository;
import ni.edu.uam.FindUAMapi.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionService {

	private final PublicacionRepository repo;
	private final UsuarioRepository usuarioRepository;

	public PublicacionService(PublicacionRepository repo, UsuarioRepository usuarioRepository) {
		this.repo = repo;
		this.usuarioRepository = usuarioRepository;
	}

	public List<Publicacion> findAll() {
		List<Publicacion> publicaciones = repo.findAll();
		publicaciones.forEach(this::completarIdUsuario);
		return publicaciones;
	}

	public Publicacion findById(Long id) {
		Publicacion publicacion = repo.findById(id).orElse(null);
		return completarIdUsuario(publicacion);
	}

	public Publicacion save(Publicacion publicacion) {
		if (publicacion.getFechaHora() == null) {
			publicacion.setFechaHora(LocalDateTime.now());
		}

		if (publicacion.getIdUsuario() != null) {
			Usuario usuario = usuarioRepository.findById(publicacion.getIdUsuario()).orElse(null);
			publicacion.setUsuario(usuario);
		} else if (publicacion.getId() != null) {
			Publicacion existente = repo.findById(publicacion.getId()).orElse(null);
			if (existente != null && publicacion.getUsuario() == null) {
				publicacion.setUsuario(existente.getUsuario());
			}
			if (existente != null && publicacion.getFechaHora() == null) {
				publicacion.setFechaHora(existente.getFechaHora());
			}
		}

		if (publicacion.getUsuario() != null) {
			publicacion.setIdUsuario(publicacion.getUsuario().getId());
		}

		return completarIdUsuario(repo.save(publicacion));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

	private Publicacion completarIdUsuario(Publicacion publicacion) {
		if (publicacion != null && publicacion.getUsuario() != null) {
			publicacion.setIdUsuario(publicacion.getUsuario().getId());
		}
		return publicacion;
	}
}
