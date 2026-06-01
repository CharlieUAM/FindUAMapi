package ni.edu.uam.FindUAMapi.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_publicacion", nullable = false)
	private Long id;

	@Column(name = "nombre_publicacion", nullable = false, length = 100)
	private String nombre;

	@Column(name = "descripcion_publicacion", nullable = false, length = 250)
	private String descripcion;

	@Column(name = "ubicacion_publicacion", nullable = false, length = 150)
	private String ubicacion;

	@Column(name = "categoria_publicacion", nullable = false, length = 100)
	private String categoria;

	@Column(name = "fecha_hora_publicacion", nullable = false)
	private LocalDateTime fechaHora;

	@Column(name = "foto_publicacion")
	@Lob
	private byte[] foto;

	@Transient
	private Long idUsuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonBackReference
	private Usuario usuario;

	@Override
	public String toString() {
		return "Publicacion{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", ubicacion='" + ubicacion + '\'' +
				", categoria='" + categoria + '\'' +
				", fechaHora=" + fechaHora +
				", idUsuario=" + idUsuario +
				'}';
	}
}
