package ni.edu.uam.FindUAMapi.Config;

import java.time.LocalDateTime;
import ni.edu.uam.FindUAMapi.Models.Objeto;
import ni.edu.uam.FindUAMapi.Models.Publicacion;
import ni.edu.uam.FindUAMapi.Models.Usuario;
import ni.edu.uam.FindUAMapi.Repository.UsuarioRepository;
import ni.edu.uam.FindUAMapi.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
		if (usuarioRepository.existsByCorreo(DEMO_EMAIL)) {
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setNombre("Usuario");
		usuario.setApellido("Demo");
		usuario.setTelefono("8888-8888");
		usuario.setCorreo(DEMO_EMAIL);
		usuario.setContrasena("demo1234");

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
