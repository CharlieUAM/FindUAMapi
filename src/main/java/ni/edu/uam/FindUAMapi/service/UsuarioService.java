package ni.edu.uam.FindUAMapi.service;

import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.Repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

	private final UsuarioRepository repo;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Usuario> findAll() {
		return repo.findAll();
	}

	public Usuario findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Usuario save(Usuario usuario) {
		if (usuario.getId() != null) {
			Usuario existente = repo.findById(usuario.getId()).orElse(null);
			if (existente != null && (usuario.getContrasena() == null || usuario.getContrasena().isBlank())) {
				usuario.setContrasena(existente.getContrasena());
			}
		}

		if (usuario.getContrasena() != null && !usuario.getContrasena().startsWith("$2a$")) {
			usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
		}

		return repo.save(usuario);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
