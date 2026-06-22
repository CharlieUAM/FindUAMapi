package ni.edu.uam.FindUAMapi.repository;

import ni.edu.uam.FindUAMapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreoUam(String correoUam);
}