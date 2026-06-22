package ni.edu.uam.FindUAMapi.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "objetos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Objeto {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JsonBackReference("publicacion-objeto")
	@JoinColumn(name = "id_objeto")
	private Publicacion publicacion;

	@Column(name = "nombre_objeto", nullable = false, length = 100)
	private String nombre;

	@Column(name = "descripcion_objeto", nullable = false, length = 250)
	private String descripcion;

	@Column(name = "categoria_objeto", nullable = false, length = 100)
	private String categoria;

	@Column(name = "foto_objeto")
	@Lob
	private byte[] foto;

	@Override
	public String toString() {
		return "Objeto{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", categoria='" + categoria + '\'' +
				'}';
	}
}
