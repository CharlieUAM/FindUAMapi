package ni.edu.uam.FindUAMapi.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
