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

public class FirebaseGestor implements FirebaseInterface {

	private static final String CREDENTIALS = "/GymBBDD.json";
	private static final String COLLECTION_CLIENTE = "Clientes";
	private static final String COLLECTION_GYM = "GymElorrietaBD";
	private static final String DOCUMENTO_GYM = "gym_01";
	private static final String COLLECTION_WORKOUT = "Workouts";
	private static final String COLLECTION_EJERCICIO = "Ejercicios";
	private static final String COLLECTION_SERIE = "Series";

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
				ret.add(new Cliente(cliente.getId(), cliente.getString(Constants.NOMBRE), cliente.getString(Constants.APELLIDO1),
						cliente.getString(Constants.APELLIDO2), cliente.getString(Constants.FECHA_NACIMIENTO),
						cliente.getString(Constants.EMAIL), cliente.getString(Constants.PASSWORD), cliente.getString(Constants.NIVEL)));
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

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
				ret = new Cliente(cliente.getId(), cliente.getString(Constants.NOMBRE), cliente.getString(Constants.APELLIDO1),
						cliente.getString(Constants.APELLIDO2), cliente.getString(Constants.FECHA_NACIMIENTO),
						cliente.getString(Constants.EMAIL), cliente.getString(Constants.PASSWORD), cliente.getString(Constants.NIVEL));
				break;
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

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
				ret = new Cliente(clienteDoc.getId(), clienteDoc.getString(Constants.NOMBRE), clienteDoc.getString(Constants.APELLIDO1),
						clienteDoc.getString(Constants.APELLIDO2), clienteDoc.getString(Constants.FECHA_NACIMIENTO),
						clienteDoc.getString(Constants.EMAIL), clienteDoc.getString(Constants.NIVEL), null);
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
								serieDoc.getString(Constants.TIEMPO_ASIGNADO), serieDoc.getString(Constants.TIEMPO_DESCANSO),
								serieDoc.getBoolean(Constants.COMPLETADO));
						series.add(serie);
					}

					Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
							ejercicioDoc.getString(Constants.DESCRIPCION), ejercicioDoc.getBoolean(Constants.COMPLETADO), series);

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
							serieDoc.getString(Constants.TIEMPO_ASIGNADO), serieDoc.getString(Constants.TIEMPO_DESCANSO),
							serieDoc.getBoolean(Constants.COMPLETADO));
					series.add(serie);
				}

				Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
						ejercicioDoc.getString(Constants.DESCRIPCION), ejercicioDoc.getBoolean(Constants.COMPLETADO), series);

				listaEjercicios.add(ejercicio);
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return listaEjercicios;
	}

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
								serieDoc.getString(Constants.TIEMPO_ASIGNADO), serieDoc.getString(Constants.TIEMPO_DESCANSO),
								serieDoc.getBoolean(Constants.COMPLETADO));
						series.add(serie);
					}

					Ejercicio ejercicio = new Ejercicio(ejercicioDoc.getId(), ejercicioDoc.getString(Constants.NOMBRE),
							ejercicioDoc.getString(Constants.DESCRIPCION), ejercicioDoc.getBoolean(Constants.COMPLETADO), series);

					listaEjercicios.add(ejercicio);
				}

				workout.setEjercicios(listaEjercicios);
			}

		} catch (Exception e) {
			throw new FireBaseException("Error al obtener workout: " + e.getLocalizedMessage());
		}

		return workout;
	}

}
