package modelo.gestores;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import modelo.entity.Cliente;
import modelo.entity.Workout;
import modelo.entity.Ejercicio;
import modelo.entity.Serie;
import modelo.exceptions.FireBaseException;

public class FirebaseGestor implements FirebaseInterface {

	private static final String CREDENTIALS = "/GymBBDD.json";
	private static final String COLLECTION_ROOT = "GymElorrietaBD";
	private static final String DOCUMENT_ID = "gym_01";
	private static final String COLLECTION_CLIENTE = "Clientes";
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
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_CLIENTE).get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();
			
			System.out.println(clientes);
			
			for (QueryDocumentSnapshot cliente : clientes) {
				ret = null == ret ? new ArrayList<Cliente>() : ret;
				ret.add(new Cliente(cliente.getId(), cliente.getString("nombre"), cliente.getString("apellido1"),
						cliente.getString("apellido2"), cliente.getString("fecha_nacimiento"),
						cliente.getString("email"), cliente.getString("password")));
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

            ApiFuture<QuerySnapshot> query = dataBase
                .collection(COLLECTION_ROOT)
                .document(DOCUMENT_ID)
                .collection(COLLECTION_CLIENTE)
                .whereEqualTo("nombre", nombre)
                .get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot cliente : clientes) {
				ret = new Cliente(cliente.getId(), cliente.getString("nombre"), cliente.getString("apellido1"),
						cliente.getString("apellido2"), cliente.getString("fecha_nacimiento"),
						cliente.getString("email"), cliente.getString("password"));
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
	        
            ApiFuture<QuerySnapshot> query = dataBase
                .collection(COLLECTION_ROOT)
                .document(DOCUMENT_ID)
                .collection(COLLECTION_CLIENTE)
                .whereEqualTo("email", email)
                .get();
	        
	        // Procesamos la query...
	        QuerySnapshot querySnapshot = query.get();
	        List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();

	        if (clientes.isEmpty()) {
	            System.out.println("Email no encontrado");
	            return null;
	        }

	        QueryDocumentSnapshot clienteDoc = clientes.get(0);

	        String passwordStored = clienteDoc.getString("password");

	        if (passwordStored == null) {
	            System.out.println("El usuario no tiene contraseña configurada");
	            return null;
	        }

	        if (password.equals(passwordStored)) {
	            ret = new Cliente(
	                clienteDoc.getId(),
	                clienteDoc.getString("nombre"),
	                clienteDoc.getString("apellido1"),
	                clienteDoc.getString("apellido2"),
	                clienteDoc.getString("fecha_nacimiento"),
	                clienteDoc.getString("email"),
	                null 
	            );
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

	            ApiFuture<QuerySnapshot> query = dataBase
	                .collection(COLLECTION_ROOT)
	                .document(DOCUMENT_ID)
	                .collection(COLLECTION_WORKOUT)
	                .get();

	            QuerySnapshot querySnapshot = query.get();

	            if (querySnapshot.isEmpty()) {
	                return null;
	            }

	            List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

	            for (QueryDocumentSnapshot workoutDoc : workouts) {
	                ret = ret == null ? new ArrayList<Workout>() : ret;
	                
	                ApiFuture<QuerySnapshot> ejerciciosQuery = 
	                    workoutDoc.getReference().collection(COLLECTION_EJERCICIO).get();

	                QuerySnapshot ejerciciosSnapshot = ejerciciosQuery.get();
	                List<Ejercicio> ejercicios = new ArrayList<>();
	                
	                for (QueryDocumentSnapshot ejercicioDoc : ejerciciosSnapshot) {

	                    ApiFuture<QuerySnapshot> seriesQuery = 
	                        ejercicioDoc.getReference().collection(COLLECTION_SERIE).get();

	                    QuerySnapshot seriesSnapshot = seriesQuery.get();
	                    List<Serie> series = new ArrayList<>();

	                    for (QueryDocumentSnapshot serieDoc : seriesSnapshot) {
	                        Serie serie = new Serie(
	                            serieDoc.getId(),
	                            serieDoc.getString("nombre"),
	                            serieDoc.getString("tiempo_asignado"),
	                            serieDoc.getString("tiempo_descanso"),
	                            serieDoc.getBoolean("completado")
	                        );
	                        series.add(serie);
	                    }

	                    Ejercicio ejercicio = new Ejercicio(
	                        ejercicioDoc.getId(),
	                        ejercicioDoc.getString("nombre"),
	                        ejercicioDoc.getString("descripcion"),
	                        ejercicioDoc.getBoolean("completado"),
	                        series
	                    );

	                    ejercicios.add(ejercicio);
	                }

	                Workout workout = new Workout(
	                    workoutDoc.getId(),
	                    workoutDoc.getString("nombre"),
	                    workoutDoc.getString("nivel"),
	                    workoutDoc.getString("video"),
	                    workoutDoc.getBoolean("completado"),
	                    ejercicios
	                );

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
		        
	            ApiFuture<QuerySnapshot> query = dataBase
	                .collection(COLLECTION_ROOT)
	                .document(DOCUMENT_ID)
	                .collection(COLLECTION_WORKOUT)
	                .document(idWorkout)
	                .collection(COLLECTION_EJERCICIO)
	                .get();
		        
		        // Procesamos la query...
		        QuerySnapshot querySnapshot = query.get();
		        List<QueryDocumentSnapshot> ejercicios = querySnapshot.getDocuments();

		        if (ejercicios.isEmpty()) {
		            System.out.println("Ejercicios no encontrados");
		            return null;
		        }

		        for (QueryDocumentSnapshot ejercicioDoc : ejercicios) {
		        	ApiFuture<QuerySnapshot> seriesQuery = 
	                        ejercicioDoc.getReference().collection(COLLECTION_SERIE).get();

	                    QuerySnapshot seriesSnapshot = seriesQuery.get();
	                    List<Serie> series = new ArrayList<>();

	                    for (QueryDocumentSnapshot serieDoc : seriesSnapshot) {
	                        Serie serie = new Serie(
	                            serieDoc.getId(),
	                            serieDoc.getString("nombre"),
	                            serieDoc.getString("tiempo_asignado"),
	                            serieDoc.getString("tiempo_descanso"),
	                            serieDoc.getBoolean("completado")
	                        );
	                        series.add(serie);
	                    }

	                    Ejercicio ejercicio = new Ejercicio(
	                        ejercicioDoc.getId(),
	                        ejercicioDoc.getString("nombre"),
	                        ejercicioDoc.getString("descripcion"),
	                        ejercicioDoc.getBoolean("completado"),
	                        series
	                    );
	                    
	                    listaEjercicios.add(ejercicio);
		        }

		    } catch (Exception e) {
		        throw new FireBaseException("Error - " + e.getLocalizedMessage());
		    }

		    return listaEjercicios;
		}

	 public Workout obtenerWorkoutPorId(String idWorkout) throws FireBaseException {
		    Workout workout = null;

		    try {
		        Firestore db = FirestoreClient.getFirestore();

		        DocumentReference workoutRef = db
		            .collection(COLLECTION_ROOT)      
		            .document(DOCUMENT_ID)           
		            .collection(COLLECTION_WORKOUT)   
		            .document(idWorkout);

		        ApiFuture<DocumentSnapshot> future = workoutRef.get();
		        DocumentSnapshot workoutSnap = future.get();

		        if (workoutSnap.exists()) {
		            workout = new Workout();
		            workout.setId(workoutSnap.getId());
		            workout.setNombre(workoutSnap.getString("nombre"));
		            workout.setNivel(workoutSnap.getString("nivel"));
		            workout.setVideo(workoutSnap.getString("video"));

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
		                    Serie serie = new Serie(
		                        serieDoc.getId(),
		                        serieDoc.getString("nombre"),
		                        serieDoc.getString("tiempo_asignado"),
		                        serieDoc.getString("tiempo_descanso"),
		                        serieDoc.getBoolean("completado")
		                    );
		                    series.add(serie);
		                }

		                Ejercicio ejercicio = new Ejercicio(
		                    ejercicioDoc.getId(),
		                    ejercicioDoc.getString("nombre"),
		                    ejercicioDoc.getString("descripcion"),
		                    ejercicioDoc.getBoolean("completado"),
		                    series
		                );

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
