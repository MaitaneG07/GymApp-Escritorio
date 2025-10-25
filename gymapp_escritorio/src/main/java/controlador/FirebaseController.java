package controlador;

import java.util.List;

import modelo.entity.Cliente;
import modelo.entity.Workout;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;

/**
 * Controlador de operaciones con Firebase.
 * 
 * Esta clase actúa como intermediaria entre las vistas y el gestor de Firebase,
 * proporcionando métodos simplificados para operaciones comunes como login,
 * obtención de workouts y gestión de clientes.
 * 
 */
public class FirebaseController {
	
    private FirebaseGestor firebaseGestor;
    
    /**
     * Constructor del controlador de Firebase.
     * 
     * Inicializa el gestor de Firebase para realizar operaciones
     * con la base de datos.
     */
    public FirebaseController() {
        try {
            this.firebaseGestor = new FirebaseGestor();
        } catch (FireBaseException e) {
            System.out.println("Error al inicializar Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Autentica a un cliente mediante email y contraseña.
     * 
     * @param email Email del cliente
     * @param password Contraseña del cliente
     * @return Cliente autenticado o null si las credenciales son incorrectas
     * @throws FireBaseException si hay error en la conexión con Firebase
     */
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

	/**
	 * Obtiene todos los workouts disponibles en Firebase.
	 * 
	 * @return Lista de workouts con sus ejercicios y series
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	public List<Workout> workout() throws FireBaseException {
		return firebaseGestor.getWorkouts();
	}

	/**
	 * Obtiene un workout específico por su ID.
	 * 
	 * @param idWorkout ID del workout a obtener
	 * @return Workout con todos sus ejercicios y series, o null si no existe
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	public Workout obtenerWorkoutPorId(String idWorkout) throws FireBaseException {
		
	    return firebaseGestor.obtenerWorkoutPorId(idWorkout);
	}

	/**
	 * Guarda o actualiza un cliente en Firebase.
	 * 
	 * @param cliente Cliente a guardar
	 * @throws FireBaseException si hay error al guardar los datos
	 */
	public void guardarCliente(Cliente cliente) throws FireBaseException {
		firebaseGestor.guardarCliente(cliente);
		
	}
	
	/**
	 * Verifica si un email ya está registrado en la base de datos.
	 * 
	 * @param email Email a verificar
	 * @return true si el email existe, false si no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	public boolean existeEmail(String email) throws FireBaseException {
	    return firebaseGestor.existeEmailCliente(email);
	}

	/**
	 * Obtiene el siguiente ID disponible para un nuevo cliente.
	 * 
	 * Genera un ID secuencial siguiendo el formato "C" + número.
	 * Por ejemplo: C1, C2, C3, etc.
	 * 
	 * @return Siguiente ID disponible en formato de String
	 * @throws FireBaseException si hay error al consultar los IDs existentes
	 */
	public String obtenerSiguienteId() throws FireBaseException {
		return firebaseGestor.obtenerSiguienteId();
	}
	
}
