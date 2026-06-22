package ni.edu.uam.FindUAMapi.service;

import ni.edu.uam.FindUAMapi.Models.Publicacion;
import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.Models.Objeto;
import ni.edu.uam.FindUAMapi.Repository.PublicacionRepository;
import ni.edu.uam.FindUAMapi.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Base64;

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
		Publicacion existente = null;
		if (publicacion.getId() != null) {
			existente = repo.findById(publicacion.getId()).orElse(null);
		}

		if (publicacion.getFechaHora() == null) {
			if (existente != null && existente.getFechaHora() != null) {
				publicacion.setFechaHora(existente.getFechaHora());
			} else {
				publicacion.setFechaHora(LocalDateTime.now());
			}
		}

		Usuario usuario = null;
		if (publicacion.getIdUsuario() != null) {
			usuario = usuarioRepository.findById(publicacion.getIdUsuario()).orElse(null);
		} else if (publicacion.getUsuario() != null && publicacion.getUsuario().getId() != null) {
			usuario = usuarioRepository.findById(publicacion.getUsuario().getId()).orElse(null);
		} else if (existente != null) {
			usuario = existente.getUsuario();
		}

		if (usuario == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario invalido o no enviado");
		}

		publicacion.setUsuario(usuario);

		Objeto objeto = publicacion.getObjeto();
		if (objeto == null) {
			if (tieneDatosPlenosDelObjeto(publicacion)) {
				objeto = existente != null && existente.getObjeto() != null ? existente.getObjeto() : new Objeto();
				aplicarDatosPlenosDelObjeto(publicacion, objeto);
			} else if (existente != null) {
				objeto = existente.getObjeto();
			}
		}

		if (objeto != null) {
			objeto.setPublicacion(publicacion);
			objeto.setId(publicacion.getId());
			publicacion.setObjeto(objeto);
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

	private boolean tieneDatosPlenosDelObjeto(Publicacion publicacion) {
		return (publicacion.getNombre() != null && !publicacion.getNombre().isBlank())
				|| (publicacion.getDescripcion() != null && !publicacion.getDescripcion().isBlank())
				|| (publicacion.getCategoria() != null && !publicacion.getCategoria().isBlank())
				|| publicacion.getIdCategoria() != null
				|| (publicacion.getFotoObjeto() != null && !publicacion.getFotoObjeto().isBlank());
	}

	private void aplicarDatosPlenosDelObjeto(Publicacion publicacion, Objeto objeto) {
		if (publicacion.getNombre() != null && !publicacion.getNombre().isBlank()) {
			objeto.setNombre(publicacion.getNombre().trim());
		}

		if (publicacion.getDescripcion() != null && !publicacion.getDescripcion().isBlank()) {
			objeto.setDescripcion(publicacion.getDescripcion().trim());
		} else if (objeto.getDescripcion() == null || objeto.getDescripcion().isBlank()) {
			objeto.setDescripcion("Sin descripcion");
		}

		String categoria = null;
		if (publicacion.getIdCategoria() != null) {
			categoria = mapCategoria(publicacion.getIdCategoria());
		} else if (publicacion.getCategoria() != null && !publicacion.getCategoria().isBlank()) {
			categoria = publicacion.getCategoria().trim();
		}
		if (categoria != null && !categoria.isBlank()) {
			objeto.setCategoria(categoria);
		}

		if (publicacion.getFotoObjeto() != null && !publicacion.getFotoObjeto().isBlank()) {
			try {
				objeto.setFoto(Base64.getDecoder().decode(publicacion.getFotoObjeto()));
			} catch (IllegalArgumentException ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La foto del objeto no es valida");
			}
		}

		if (objeto.getNombre() == null || objeto.getNombre().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del objeto es obligatorio");
		}

		if (objeto.getCategoria() == null || objeto.getCategoria().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria del objeto es obligatoria");
		}
	}

	private String mapCategoria(Integer idCategoria) {
		return switch (idCategoria) {
			case 1 -> "Llaves";
			case 2 -> "Electrónica";
			case 3 -> "Bolsas y Mochilas";
			case 4 -> "Documentos";
			default -> "Accesorios";
		};
	}
}
