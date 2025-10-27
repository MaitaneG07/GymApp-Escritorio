package modelo.gestores;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import modelo.entity.Cliente;
import modelo.entity.Ejercicio;
import modelo.entity.Serie;
import modelo.entity.Workout;
import modelo.exceptions.FireBaseException;
import utils.Constants;

/**
 * Gestor de operaciones con Firebase Firestore.
 * 
 * Esta clase implementa todas las operaciones CRUD necesarias para interactuar
 * con la base de datos Firebase Firestore. Gestiona la inicialización de Firebase,
 * autenticación, y operaciones sobre las colecciones de Clientes, Workouts,
 * Ejercicios y Series.
 * 
 * Estructura de la base de datos:
 * - GymElorrietaBD
 *   - gym_01
 *     - Clientes
 *     - Workouts
 *       - {idWorkout}
 *         - Ejercicios
 *           - {idEjercicio}
 *             - Series
 * 
 */
public class FirebaseGestor implements FirebaseInterface {

	private static final String CREDENTIALS = "/GymBBDD.json";
	private static final String COLLECTION_CLIENTE = "Clientes";
	private static final String COLLECTION_GYM = "GymElorrietaBD";
	private static final String DOCUMENTO_GYM = "gym_01";
	private static final String COLLECTION_WORKOUT = "Workouts";
	private static final String COLLECTION_EJERCICIO = "Ejercicios";
	private static final String COLLECTION_SERIE = "Series";

	/**
	 * Constructor del gestor de Firebase.
	 * 
	 * Inicializa la conexión con Firebase Firestore usando el archivo de
	 * credenciales. Si Firebase ya está inicializado, reutiliza la conexión
	 * existente. Valida que el archivo de credenciales exista antes de proceder.
	 * 
	 * @throws FireBaseException si no se encuentra el archivo de credenciales
	 *         o si hay error al inicializar Firebase
	 */
	public FirebaseGestor() throws FireBaseException {
		try {
			if (FirebaseApp.getApps().isEmpty()) {
				InputStream serviceAccount = FirebaseGestor.class.getResourceAsStream(CREDENTIALS);

				if (serviceAccount == null) {
					throw new FireBaseException("❌ No se encontró el archivo de credenciales: " + CREDENTIALS);
				}

				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
				FirebaseApp.initializeApp(options);
				System.out.println("✓ Firebase inicializado correctamente");
			} else {
				System.out.println("✓ Firebase ya estaba inicializado");
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
	}

	/**
	 * Obtiene todos los clientes registrados en Firebase.
	 * 
	 * Consulta la colección de clientes y devuelve una lista con todos
	 * los datos completos de cada cliente incluyendo: id, nombre, apellidos,
	 * fecha de nacimiento, email, contraseña y nivel.
	 * 
	 * @return Lista de clientes o null si no hay clientes registrados
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	@Override
	public List<Cliente> getClientes() throws FireBaseException {
		List<Cliente> ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();

			// Query...
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_CLIENTE).get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();

			for (QueryDocumentSnapshot cliente : clientes) {
				ret = null == ret ? new ArrayList<Cliente>() : ret;
				ret.add(new Cliente(cliente.getId(), cliente.getString(Constants.NOMBRE),
						cliente.getString(Constants.APELLIDO1), cliente.getString(Constants.APELLIDO2),
						cliente.getString(Constants.FECHA_NACIMIENTO), cliente.getString(Constants.EMAIL),
						cliente.getString(Constants.PASSWORD), cliente.getString(Constants.NIVEL)));
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

	/**
	 * Busca y obtiene un cliente por su nombre.
	 * 
	 * Realiza una búsqueda por el campo "nombre" y devuelve el primer
	 * cliente que coincida con el nombre especificado.
	 * 
	 * @param nombre Nombre del cliente a buscar
	 * @return Cliente encontrado con todos sus datos, o null si no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	@Override
	public Cliente getCliente(String nombre) throws FireBaseException {
		Cliente ret = null;
		try {

			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_CLIENTE).whereEqualTo(Constants.NOMBRE, nombre).get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot cliente : clientes) {
				ret = new Cliente(cliente.getId(), cliente.getString(Constants.NOMBRE),
						cliente.getString(Constants.APELLIDO1), cliente.getString(Constants.APELLIDO2),
						cliente.getString(Constants.FECHA_NACIMIENTO), cliente.getString(Constants.EMAIL),
						cliente.getString(Constants.PASSWORD), cliente.getString(Constants.NIVEL));
				break;
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

	/**
	 * Autentica a un cliente mediante email y contraseña.
	 * 
	 * Busca el cliente por email en Firebase y verifica que la contraseña
	 * coincida. Si la autenticación es exitosa, devuelve el objeto Cliente
	 * pero sin incluir la contraseña (se pasa null por seguridad).
	 * 
	 * @param email Email del cliente a autenticar
	 * @param password Contraseña del cliente
	 * @return Cliente autenticado sin contraseña, o null si las credenciales
	 *         son incorrectas o el usuario no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	@Override
	public Cliente login(String email, String password) throws FireBaseException {
		Cliente ret = null;
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_CLIENTE).whereEqualTo(Constants.EMAIL, email).get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();

			if (clientes.isEmpty()) {
				System.out.println("Email no encontrado");
				return null;
			}

			QueryDocumentSnapshot clienteDoc = clientes.get(0);

			String passwordStored = clienteDoc.getString(Constants.PASSWORD);

			if (passwordStored == null) {
				System.out.println("El usuario no tiene contraseña configurada");
				return null;
			}

			if (password.equals(passwordStored)) {
				ret = new Cliente(clienteDoc.getId(), clienteDoc.getString(Constants.NOMBRE),
						clienteDoc.getString(Constants.APELLIDO1), clienteDoc.getString(Constants.APELLIDO2),
						clienteDoc.getString(Constants.FECHA_NACIMIENTO), clienteDoc.getString(Constants.EMAIL), null,
						clienteDoc.getString(Constants.NIVEL));
				System.out.println("Login exitoso: " + ret.getNombre());
			} else {
				System.out.println("Contraseña incorrecta");
				return null;
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

	/**
	 * Obtiene todos los workouts con su información completa.
	 * 
	 * Consulta todos los workouts y carga recursivamente todos sus ejercicios
	 * y series asociadas. Construye una estructura completa de objetos anidados:
	 * Workout -> Lista de Ejercicios -> Lista de Series.
	 * 
	 * @return Lista de workouts con ejercicios y series, o null si no hay workouts
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	@Override
	public List<Workout> getWorkouts() throws FireBaseException {
		List<Workout> ret = null;

		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_WORKOUT).get();

			QuerySnapshot querySnapshot = query.get();

			if (querySnapshot.isEmpty()) {
				return null;
			}

			List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

			for (QueryDocumentSnapshot workoutDoc : workouts) {
				ret = ret == null ? new ArrayList<Workout>() : ret;

				ApiFuture<QuerySnapshot> ejerciciosQuery = workoutDoc.getReference().collection(COLLECTION_EJERCICIO)
						.get();

				QuerySnapshot ejerciciosSnapshot = ejerciciosQuery.get();
				List<Ejercicio> ejercicios = new ArrayList<>();

				for (QueryDocumentSnapshot ejercicioDoc : ejerciciosSnapshot) {

					ApiFuture<QuerySnapshot> seriesQuery = ejercicioDoc.getReference().collection(COLLECTION_SERIE)
							.get();

					QuerySnapshot seriesSnapshot = seriesQuery.get();
					List<Serie> series = new ArrayList<>();

					for (QueryDocumentSnapshot serieDoc : seriesSnapshot) {
						Serie serie = new Serie(serieDoc.getId(), serieDoc.getString(Constants.NOMBRE),
								serieDoc.getString(Constants.TIEMPO_ASIGNADO),
								serieDoc.getString(Constants.TIEMPO_DESCANSO),
								serieDoc.getBoolean(Constants.COMPLETADO));
						series.add(serie);
					}

					Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
							ejercicioDoc.getString(Constants.DESCRIPCION),
							ejercicioDoc.getBoolean(Constants.COMPLETADO), series);

					ejercicios.add(ejercicio);
				}

				Workout workout = new Workout(workoutDoc.getId(), workoutDoc.getString(Constants.NOMBRE),
						workoutDoc.getString(Constants.NIVEL), workoutDoc.getString(Constants.VIDEO),
						workoutDoc.getBoolean(Constants.COMPLETADO), ejercicios);

				ret.add(workout);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	/**
	 * Obtiene los ejercicios de un workout específico con sus series.
	 * 
	 * Consulta la subcolección de ejercicios de un workout y carga todas
	 * las series asociadas a cada ejercicio.
	 * 
	 * @param idWorkout ID del workout del cual obtener los ejercicios
	 * @return Lista de ejercicios con sus series, o null si no hay ejercicios
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	@Override
	public List<Ejercicio> obtenerEjerciciosPorWorkout(String idWorkout) throws FireBaseException {
		List<Ejercicio> listaEjercicios = new ArrayList<>();

		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_WORKOUT).document(idWorkout).collection(COLLECTION_EJERCICIO).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> ejercicios = querySnapshot.getDocuments();

			if (ejercicios.isEmpty()) {
				System.out.println("Ejercicios no encontrados");
				return null;
			}

			for (QueryDocumentSnapshot ejercicioDoc : ejercicios) {
				ApiFuture<QuerySnapshot> seriesQuery = ejercicioDoc.getReference().collection(COLLECTION_SERIE).get();

				QuerySnapshot seriesSnapshot = seriesQuery.get();
				List<Serie> series = new ArrayList<>();

				for (QueryDocumentSnapshot serieDoc : seriesSnapshot) {
					Serie serie = new Serie(serieDoc.getId(), serieDoc.getString(Constants.NOMBRE),
							serieDoc.getString(Constants.TIEMPO_ASIGNADO),
							serieDoc.getString(Constants.TIEMPO_DESCANSO), serieDoc.getBoolean(Constants.COMPLETADO));
					series.add(serie);
				}

				Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
						ejercicioDoc.getString(Constants.DESCRIPCION), ejercicioDoc.getBoolean(Constants.COMPLETADO),
						series);

				listaEjercicios.add(ejercicio);
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return listaEjercicios;
	}

	/**
	 * Guarda o actualiza un usuario en Firebase con validación de ID.
	 * 
	 * Si el cliente no tiene ID o está vacío, genera uno automáticamente
	 * usando UUID. Crea un Map con los campos específicos del cliente y
	 * los guarda en Firebase. Este método NO guarda el campo 'nivel'.
	 * 
	 * @param cliente Cliente con los datos a guardar
	 * @return true si se guardó correctamente
	 * @throws FireBaseException si hay error al guardar
	 */
	@Override
	public boolean guardarUsuario(Cliente cliente) throws FireBaseException {
		try {

			Firestore dataBase = FirestoreClient.getFirestore();

			if (cliente.getId() == null || cliente.getId().isEmpty()) {
				cliente.setId(UUID.randomUUID().toString());
			}

			Map<String, Object> usuarioData = new HashMap<>();
			usuarioData.put(Constants.NOMBRE, cliente.getNombre());
			usuarioData.put(Constants.APELLIDO1, cliente.getApellido1());
			usuarioData.put(Constants.APELLIDO2, cliente.getApellido2());
			usuarioData.put(Constants.FECHA_NACIMIENTO, cliente.getFechaNacimiento());
			usuarioData.put(Constants.EMAIL, cliente.getEmail());
			usuarioData.put(Constants.PASSWORD, cliente.getPassword());

			dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM).collection(COLLECTION_CLIENTE)
					.document(cliente.getId()).set(usuarioData).get();

			return true;
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());

		}

	}

	/**
	 * Obtiene el siguiente ID secuencial disponible para un nuevo cliente.
	 * 
	 * Consulta el último cliente registrado ordenando por ID en orden
	 * descendente, extrae el número del ID y lo incrementa en 1.
	 * Formato del ID: "C" + número (por ejemplo: C1, C2, C3, etc.)
	 * Si no hay clientes registrados, devuelve "C1".
	 * 
	 * @return Siguiente ID disponible en formato String (ej: "C5")
	 * @throws FireBaseException si hay error al consultar los IDs
	 */
	@Override
	public String obtenerSiguienteId() throws FireBaseException {
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> future = dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_CLIENTE).orderBy(FieldPath.documentId(), Query.Direction.DESCENDING).limit(1)
					.get();

			List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

			if (documentos.isEmpty()) {
				return "C1";
			} else {
				String ultimoId = documentos.get(0).getId();
				String soloLetras = ultimoId.replaceAll("\\d+", "");
				String soloNumeros = ultimoId.replaceAll("\\D+", "");

				int incrementarNumero = Integer.parseInt(soloNumeros) + 1;

				return soloLetras + incrementarNumero;
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());

		}
	}

	/**
	 * Guarda o actualiza un cliente completo en Firebase.
	 * 
	 * Utiliza el método set() de Firestore para guardar el objeto
	 * completo Cliente. Si el documento ya existe, lo sobrescribe.
	 * A diferencia de guardarUsuario(), este método guarda todos
	 * los campos del objeto Cliente incluyendo el nivel.
	 * 
	 * @param cliente Cliente completo a guardar
	 * @throws FireBaseException si hay error al guardar
	 */
	@Override
	public void guardarCliente(Cliente cliente) throws FireBaseException {
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			dataBase.collection(COLLECTION_GYM).document(DOCUMENTO_GYM).collection(COLLECTION_CLIENTE)
					.document(cliente.getId()).set(cliente).get();
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
	}

	/**
	 * Obtiene un workout específico por su ID con toda su información.
	 * 
	 * Busca el workout en Firebase y carga recursivamente todos sus
	 * ejercicios y las series de cada ejercicio. Construye un objeto
	 * Workout completo con toda su estructura anidada.
	 * 
	 * @param idWorkout ID del workout a obtener
	 * @return Workout completo con ejercicios y series, o null si no existe
	 * @throws FireBaseException si hay error al obtener los datos
	 */
	@Override
	public Workout obtenerWorkoutPorId(String idWorkout) throws FireBaseException {
		Workout workout = null;

		try {
			Firestore db = FirestoreClient.getFirestore();

			DocumentReference workoutRef = db.collection(COLLECTION_GYM).document(DOCUMENTO_GYM)
					.collection(COLLECTION_WORKOUT).document(idWorkout);

			ApiFuture<DocumentSnapshot> future = workoutRef.get();
			DocumentSnapshot workoutSnap = future.get();

			if (workoutSnap.exists()) {
				workout = new Workout();
				workout.setId(workoutSnap.getId());
				workout.setNombre(workoutSnap.getString(Constants.NOMBRE));
				workout.setNivel(workoutSnap.getString(Constants.NIVEL));
				workout.setVideo(workoutSnap.getString(Constants.VIDEO));

				CollectionReference ejerciciosRef = workoutRef.collection(COLLECTION_EJERCICIO);
				ApiFuture<QuerySnapshot> queryEjercicios = ejerciciosRef.get();
				QuerySnapshot ejerciciosSnapshot = queryEjercicios.get();

				List<Ejercicio> listaEjercicios = new ArrayList<>();

				for (QueryDocumentSnapshot ejercicioDoc : ejerciciosSnapshot.getDocuments()) {

					CollectionReference seriesRef = ejercicioDoc.getReference().collection(COLLECTION_SERIE);
					ApiFuture<QuerySnapshot> querySeries = seriesRef.get();
					QuerySnapshot seriesSnapshot = querySeries.get();

					List<Serie> series = new ArrayList<>();
					for (QueryDocumentSnapshot serieDoc : seriesSnapshot.getDocuments()) {
						Serie serie = new Serie(serieDoc.getId(), serieDoc.getString(Constants.NOMBRE),
								serieDoc.getString(Constants.TIEMPO_ASIGNADO),
								serieDoc.getString(Constants.TIEMPO_DESCANSO),
								serieDoc.getBoolean(Constants.COMPLETADO));
						series.add(serie);
					}

					Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
							ejercicioDoc.getString(Constants.DESCRIPCION),
							ejercicioDoc.getBoolean(Constants.COMPLETADO), series);

					listaEjercicios.add(ejercicio);
				}

				workout.setEjercicios(listaEjercicios);
			}

		} catch (Exception e) {
			throw new FireBaseException("Error al obtener workout: " + e.getLocalizedMessage());
		}

		return workout;
	}
	
	/**
	 * Verifica si existe un cliente con el email especificado en Firebase.
	 * 
	 * @param email Email a verificar
	 * @return true si el email ya existe, false si no existe
	 * @throws FireBaseException si hay error en la consulta
	 */
	@Override
	public boolean existeEmailCliente(String email) throws FireBaseException {
	    try {
	        Firestore dataBase = FirestoreClient.getFirestore();

	        ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_GYM)
	                .document(DOCUMENTO_GYM)
	                .collection(COLLECTION_CLIENTE)
	                .whereEqualTo(Constants.EMAIL, email)
	                .get();

	        QuerySnapshot querySnapshot = query.get();
	        
	        return !querySnapshot.isEmpty();

	    } catch (Exception e) {
	        throw new FireBaseException("Error al verificar email: " + e.getLocalizedMessage());
	    }
	}
}
