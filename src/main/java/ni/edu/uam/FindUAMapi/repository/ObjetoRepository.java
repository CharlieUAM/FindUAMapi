package ni.edu.uam.FindUAMapi.repository;

import ni.edu.uam.FindUAMapi.entity.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjetoRepository
        extends JpaRepository<Objeto, Integer> {
}
