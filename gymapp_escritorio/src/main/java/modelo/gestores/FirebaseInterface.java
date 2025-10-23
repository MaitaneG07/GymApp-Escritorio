package modelo.gestores;

import java.util.List;

import modelo.entity.Cliente;
import modelo.exceptions.FireBaseException;

public interface FirebaseInterface {

	public List<Cliente> getClientes() throws FireBaseException;
	
	public Cliente getCliente(String nombre) throws FireBaseException;
	
	public Cliente login (String email, String password) throws FireBaseException;

	void guardarCliente(Cliente cliente) throws FireBaseException;

	String obtenerSiguienteId() throws FireBaseException;

	boolean guardarUsuario(Cliente cliente) throws FireBaseException;
	
}
