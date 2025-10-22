package modelo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Ejercicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private String descripcion;
	private boolean completado;
	private List<Serie> series;
	private Workout workout;
	
	public Ejercicio() {
		super();
	}

	public Ejercicio(String id, String nombre, String descripcion, boolean completado, List<Serie> series,
			Workout workout) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.completado = completado;
		this.series = series;
		this.workout = workout;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	@Override
	public int hashCode() {
		return Objects.hash(completado, descripcion, id, nombre, workout);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio other = (Ejercicio) obj;
		return completado == other.completado && Objects.equals(descripcion, other.descripcion) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(workout, other.workout);
	}

	@Override
	public String toString() {
		return id + ", " + nombre + ", " + descripcion + ", "
				+ completado + ", " + workout;
	}
	
	

}
