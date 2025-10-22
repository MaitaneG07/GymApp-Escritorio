package modelo.entity;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {

	
	private static final long serialVersionUID = -296374817012181865L;
	
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String fecha_nacimiento;
	private String email;
	
	
	public Cliente() {
		super();
	}


	public Cliente(String nombre, String apellido1, String apellido2, String fecha_nacimiento, String email) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fecha_nacimiento = fecha_nacimiento;
		this.email = email;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}


	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(apellido1, apellido2, email, fecha_nacimiento, nombre);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(apellido1, other.apellido1) && Objects.equals(apellido2, other.apellido2)
				&& Objects.equals(email, other.email) && Objects.equals(fecha_nacimiento, other.fecha_nacimiento)
				&& Objects.equals(nombre, other.nombre);
	}


	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", fecha_nacimiento=" + fecha_nacimiento + ", email=" + email + "]";
	}

}
