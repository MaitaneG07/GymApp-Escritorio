package controlador;

import modelo.entity.Cliente;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;

public class FirebaseController {
	
    private FirebaseGestor firebaseGestor;
    
    public FirebaseController() {
        try {
            this.firebaseGestor = new FirebaseGestor();
        } catch (FireBaseException e) {
            System.out.println("Error al inicializar Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

	public Cliente login(String email, String password) throws FireBaseException {
		
		if (email == null || email.isEmpty()) {
			return null;
		}
		
		if(password == null || password.isEmpty()) {
			return null;
		}
		
		try {
			return firebaseGestor.login(email, password);
		} catch (FireBaseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void guardarCliente(Cliente cliente) throws FireBaseException {
		firebaseGestor.guardarCliente(cliente);
		
	}
	
	
}
