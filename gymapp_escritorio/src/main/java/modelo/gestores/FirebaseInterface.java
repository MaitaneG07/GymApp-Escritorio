package modelo.gestores;

import java.util.List;

import modelo.entity.Cliente;
import modelo.entity.Ejercicio;
import modelo.entity.Historico;
import modelo.entity.Workout;
import modelo.exceptions.FireBaseException;

/**
 * Interfaz que define las operaciones disponibles para interactuar con Firebase.
 * 
 * Esta interfaz establece el contrato para todas las operaciones CRUD y de
 * consulta relacionadas con clientes, workouts, ejercicios y series en la
 * base de datos Firebase Firestore.
 */
public interface FirebaseInterface {

	/**
	 * Obtiene todos los clientes registrados en Firebase.
	 * 
	 * @return Lista de todos los clientes con sus datos completos
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	public List<Cliente> getClientes() throws FireBaseException;
	
	
	/**
	 * Busca un cliente por su nombre.
	 * 
	 * @param nombre Nombre del cliente a buscar
	 * @return Cliente encontrado o null si no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	public Cliente getCliente(String nombre) throws FireBaseException;
	
	
    /**
     * Autentica a un cliente mediante email y contraseña.
     * 
     * @param email Email del cliente
     * @param password Contraseña del cliente
     * @return Cliente autenticado o null si las credenciales son incorrectas
     * @throws FireBaseException si hay error en la conexión con Firebase
     */
	public Cliente login (String email, String password) throws FireBaseException;

	
	/**
	 * Obtiene todos los workouts disponibles en Firebase.
	 * 
	 * @return Lista de workouts con sus ejercicios y series
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	List<Workout> getWorkouts() throws FireBaseException;

	
	/**
	 * Obtiene los ejercicios de un workout específico.
	 * 
	 * @param idWorkout ID del workout del cual obtener los ejercicios
	 * @return Lista de ejercicios con sus series, o null si no hay ejercicios
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	List<Ejercicio> obtenerEjerciciosPorWorkout(String idWorkout) throws FireBaseException;

	
	/**
	 * Obtiene un workout específico por su ID.
	 * 
	 * @param idWorkout ID del workout a obtener
	 * @return Workout con todos sus ejercicios y series, o null si no existe
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	Workout obtenerWorkoutPorId(String idWorkout) throws FireBaseException;

	
	/**
	 * Guarda o actualiza un cliente en Firebase.
	 * 
	 * @param cliente Cliente a guardar
	 * @throws FireBaseException si hay error al guardar los datos
	 */
	void guardarCliente(Cliente cliente) throws FireBaseException;

	
	/**
	 * Obtiene el siguiente ID disponible para un nuevo cliente.
	 * 
	 * Genera un ID secuencial siguiendo el formato "C" + número.
	 * Por ejemplo: C1, C2, C3, etc.
	 * 
	 * @return Siguiente ID disponible en formato de String
	 * @throws FireBaseException si hay error al consultar los IDs existentes
	 */
	String obtenerSiguienteId() throws FireBaseException;

	
	/**
	 * Guarda un nuevo usuario en Firebase con validación de ID.
	 * 
	 * Si el cliente no tiene ID o está vacío, genera uno automáticamente
	 * usando UUID. Guarda solo los campos específicos mediante un Map.
	 * 
	 * @param cliente Cliente a guardar
	 * @return true si se guardó correctamente
	 * @throws FireBaseException si hay error al guardar
	 */
	boolean guardarUsuario(Cliente cliente) throws FireBaseException;

	
	/**
	 * Verifica si existe un cliente con el email especificado en Firebase.
	 * 
	 * @param email Email a verificar
	 * @return true si el email ya existe, false si no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	boolean existeEmailCliente(String email) throws FireBaseException;


	List<Historico> getHistoricos(String idCliente) throws FireBaseException;



}
