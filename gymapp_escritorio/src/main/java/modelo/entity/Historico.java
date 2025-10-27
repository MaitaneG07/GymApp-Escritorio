package modelo.entity;

import java.io.Serializable;
import java.util.Objects;

public class Historico extends Workout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String tiempoTotal;
	private String tiempoPrevisto;
	private String fechaInicial;
	private String nombre;
	private String nivel;
	private String porcentaje;
	private String video;
	
	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Historico() {
		super();
	}

	public Historico(String id, String tiempoTotal, String tiempoPrevisto) {
		super();
		this.id = id;
		this.tiempoTotal = tiempoTotal;
		this.tiempoPrevisto = tiempoPrevisto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(String tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public String getTiempoPrevisto() {
		return tiempoPrevisto;
	}

	public void setTiempoPrevisto(String tiempoPrevisto) {
		this.tiempoPrevisto = tiempoPrevisto;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(fechaInicial, id, nivel, nombre, porcentaje, tiempoPrevisto, tiempoTotal, video);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historico other = (Historico) obj;
		return Objects.equals(fechaInicial, other.fechaInicial) && Objects.equals(id, other.id)
				&& Objects.equals(nivel, other.nivel) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(porcentaje, other.porcentaje) && Objects.equals(tiempoPrevisto, other.tiempoPrevisto)
				&& Objects.equals(tiempoTotal, other.tiempoTotal) && Objects.equals(video, other.video);
	}

	@Override
	public String toString() {
		return id + ", " + tiempoTotal + ", " + tiempoPrevisto
				+ ", " + fechaInicial + ", " + nombre + ", " + nivel + ", "
				+ porcentaje + ", " + video;
	}
	
	
	
}
