package modelo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Workout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String nombre;
	protected String nivel;
	protected String video;
	protected boolean completado;
	protected List<Ejercicio> ejercicios;
	
	public Workout() {
		super();
	}

	public Workout(String id, String nombre, String nivel, String video, boolean completado,
			List<Ejercicio> ejercicios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nivel = nivel;
		this.video = video;
		this.completado = completado;
		this.ejercicios = ejercicios;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public List<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(List<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(completado, ejercicios, nivel, id, nombre, video);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		return completado == other.completado && Objects.equals(ejercicios, other.ejercicios)
				&& id == other.id && Objects.equals(nombre, other.nombre) 
				&& Objects.equals(video, other.video) && Objects.equals(nivel, other.nivel);
	}

	@Override
	public String toString() {
		return id + ", " + nombre + ", " + video
				+ ", " + nivel + ", " + completado + ", " + ejercicios;
	}
	
	

}
