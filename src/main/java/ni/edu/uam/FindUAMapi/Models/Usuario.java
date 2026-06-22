package ni.edu.uam.FindUAMapi.Models;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	@JsonAlias("idUsuario")
	private Long id;

	@Column(name = "nombre_usuario", nullable = false, length = 100)
	private String nombre;

	@Column(name = "apellido_usuario", nullable = false, length = 100)
	private String apellido;

	@Column(name = "telefono_usuario", nullable = false, length = 20)
	private String telefono;

	@Column(name = "correo_usuario", nullable = false, unique = true, length = 150)
	private String correo;

	@Column(name = "contrasena_usuario", nullable = false, length = 250)
	private String contrasena;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference("usuario-publicaciones")
	private List<Publicacion> publicaciones = new ArrayList<>();

	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", telefono='" + telefono + '\'' +
				", correo='" + correo + '\'' +
				", contrasena='" + contrasena + '\'' +
				'}';
	}

	public void agregarPublicacion(Publicacion publicacion) {
		publicaciones.add(publicacion);
		publicacion.setUsuario(this);
	}

	public void quitarPublicacion(Publicacion publicacion) {
		publicaciones.remove(publicacion);
		publicacion.setUsuario(null);
	}
}
