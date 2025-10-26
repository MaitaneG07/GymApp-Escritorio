package modelo.entity;

import java.io.Serializable;
import java.util.Objects;

public class Serie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private String tiempoDuracion;
	private String tiempoDescanso;
	private boolean completado;
	
	public Serie() {
		super();
	}

	public Serie(String id, String nombre, String tiempoDuracion, String tiempoDescanso, boolean completado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tiempoDuracion = tiempoDuracion;
		this.tiempoDescanso = tiempoDescanso;
		this.completado = completado;
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

	public String getTiempoDuracion() {
		return tiempoDuracion;
	}

	public void setTiempoDuracion(String tiempoDuracion) {
		this.tiempoDuracion = tiempoDuracion;
	}

	public String getTiempoDescanso() {
		return tiempoDescanso;
	}

	public void setTiempoDescanso(String tiempoDescanso) {
		this.tiempoDescanso = tiempoDescanso;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(completado, id, nombre, tiempoDuracion, tiempoDescanso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		return completado == other.completado && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(tiempoDuracion, other.tiempoDuracion)
				&& Objects.equals(tiempoDescanso, other.tiempoDescanso);
	}

	@Override
	public String toString() {
		return id + ", " + nombre + ", " + tiempoDuracion + ", " + tiempoDescanso
				+ ", " + completado;
	}
	
	
}
