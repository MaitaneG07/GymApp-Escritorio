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
		result = prime * result + Objects.hash(id, tiempoPrevisto, tiempoTotal);
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
		return Objects.equals(id, other.id) && Objects.equals(tiempoPrevisto, other.tiempoPrevisto)
				&& Objects.equals(tiempoTotal, other.tiempoTotal);
	}

	@Override
	public String toString() {
		return super.toString() + id + ", " + tiempoTotal + ", " + tiempoPrevisto;
	}
	
	
	
}
