package ni.edu.uam.FindUAMapi.Repository;

import ni.edu.uam.FindUAMapi.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	boolean existsByCorreo(String correo);
}
