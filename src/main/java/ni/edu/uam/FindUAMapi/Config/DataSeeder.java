package ni.edu.uam.FindUAMapi.Config;

import java.time.LocalDateTime;
import java.util.Optional;
import ni.edu.uam.FindUAMapi.Models.Objeto;
import ni.edu.uam.FindUAMapi.Models.Publicacion;
import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.Repository.UsuarioRepository;
import ni.edu.uam.FindUAMapi.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {

	private static final String DEMO_EMAIL = "demo@finduam.com";

	private final UsuarioRepository usuarioRepository;
	private final UsuarioService usuarioService;

	public DataSeeder(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	public void run(String... args) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(DEMO_EMAIL);
		if (usuarioExistente.isPresent() && usuarioExistente.get().getPublicaciones() != null
				&& !usuarioExistente.get().getPublicaciones().isEmpty()) {
			return;
		}

		Usuario usuario = usuarioExistente.orElseGet(() -> {
			Usuario nuevo = new Usuario();
			nuevo.setNombre("Usuario");
			nuevo.setApellido("Demo");
			nuevo.setTelefono("8888-8888");
			nuevo.setCorreo(DEMO_EMAIL);
			nuevo.setContrasena("demo1234");
			return nuevo;
		});

		Publicacion publicacion = new Publicacion();
		publicacion.setUbicacion("Campus UAM");
		publicacion.setFechaHora(LocalDateTime.now());

		Objeto objeto = new Objeto();
		objeto.setNombre("Cartera negra");
		objeto.setDescripcion("Cartera de ejemplo para probar la API");
		objeto.setCategoria("Documento");
		objeto.setFoto(null);

		publicacion.setObjeto(objeto);
		usuario.agregarPublicacion(publicacion);

		usuarioService.save(usuario);
	}
}
