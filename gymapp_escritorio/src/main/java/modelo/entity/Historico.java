package modelo.entity;


import java.io.Serializable;
import java.util.Objects;

public class Historico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String nivel;
    private String tiempo_total;
    private String tiempo_previsto;
    private String fecha_inicio;
    private String porcentaje; 

    public Historico() {
        super();
    }

    public Historico(String id, String nombre, String nivel, String tiempoTotal, 
                     String tiempoPrevisto, String fecha, String porcentaje) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.tiempo_total = tiempoTotal;
        this.tiempo_previsto = tiempoPrevisto;
        this.fecha_inicio = fecha;
        this.porcentaje = porcentaje;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getTiempoTotal() {
		return tiempo_total;
	}

	public void setTiempoTotal(String tiempoTotal) {
		this.tiempo_total = tiempoTotal;
	}

	public String getTiempoPrevisto() {
		return tiempo_previsto;
	}

	public void setTiempoPrevisto(String tiempoPrevisto) {
		this.tiempo_previsto = tiempoPrevisto;
	}

	public String getFecha() {
		return fecha_inicio;
	}

	public void setFecha(String fecha) {
		this.fecha_inicio = fecha;
	}

	public String getEjerciciosCompletados() {
		return porcentaje;
	}

	public void setEjerciciosCompletados(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public int hashCode() {
		return Objects.hash(porcentaje, fecha_inicio, id, nivel, nombre, tiempo_previsto, tiempo_total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historico other = (Historico) obj;
		return Objects.equals(porcentaje, other.porcentaje) && Objects.equals(fecha_inicio, other.fecha_inicio)
				&& Objects.equals(id, other.id) && Objects.equals(nivel, other.nivel)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(tiempo_previsto, other.tiempo_previsto)
				&& Objects.equals(tiempo_total, other.tiempo_total);
	}

	@Override
	public String toString() {
		return id + ", " + nombre + ", " + nivel + ", " + tiempo_total
				+ ", " + tiempo_previsto + ", " + fecha_inicio + ", "
				+ porcentaje;
	}

}