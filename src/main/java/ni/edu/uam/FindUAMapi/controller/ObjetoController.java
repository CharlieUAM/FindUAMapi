package ni.edu.uam.FindUAMapi.controller;

import ni.edu.uam.FindUAMapi.entity.Objeto;
import ni.edu.uam.FindUAMapi.repository.ObjetoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objetos")
@CrossOrigin("*")
public class ObjetoController {

    private final ObjetoRepository repository;

    public ObjetoController(
            ObjetoRepository repository
    ) {
        this.repository = repository;
    }

    @GetMapping
    public List<Objeto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Objeto guardar(
            @RequestBody Objeto objeto
    ) {
        return repository.save(objeto);
    }
}
