package ni.edu.uam.FindUAMapi.Repository;

import ni.edu.uam.FindUAMapi.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	boolean existsByCorreo(String correo);

	Optional<Usuario> findByCorreo(String correo);

	Optional<Usuario> findByCorreoIgnoreCase(String correo);
}
