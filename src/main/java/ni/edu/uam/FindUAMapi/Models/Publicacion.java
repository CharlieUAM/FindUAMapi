package ni.edu.uam.FindUAMapi.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	@Column(name = "ubicacion_publicacion", nullable = false, length = 150)
	private String ubicacion;

	@Column(name = "fecha_hora_publicacion", nullable = false)
	private LocalDateTime fechaHora;

	@Transient
	private Long idUsuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonBackReference("usuario-publicaciones")
	private Usuario usuario;

	@OneToOne(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference("publicacion-objeto")
	private Objeto objeto;

	@Override
	public String toString() {
		return "Publicacion{" +
				"id=" + id +
				", ubicacion='" + ubicacion + '\'' +
				", fechaHora=" + fechaHora +
				", idUsuario=" + idUsuario +
				", objeto=" + objeto +
				'}';
	}
}
