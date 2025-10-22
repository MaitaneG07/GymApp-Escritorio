package modelo.gestores;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import modelo.entity.Cliente;
import modelo.exceptions.FireBaseException;

public class FirebaseGestor implements FirebaseInterface {
	
	private static final String CREDENTIALS = "/GymBBDD.json";
	private static final String COLLECTION_CLIENTE = "Clientes";
	
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

			// Query...
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_CLIENTE).whereEqualTo("nombre", nombre).get();

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

			// Query...
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_CLIENTE).whereEqualTo("email", email).get();

			// Procesamos la query...
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> clientes = querySnapshot.getDocuments();
			
			if(clientes.isEmpty()) {
				System.out.println("Email no encontrado");
	            return null;
			}
			
			QueryDocumentSnapshot clienteDoc = clientes.get(0);
			
			String passwordHash = clienteDoc.getString("password");
			
			if (passwordHash == null) {
	            System.out.println("El usuario no tiene contraseña configurada");
	            return null;
	        }
			
			if (BCrypt.checkpw(password, passwordHash)) {
	            ret = new Cliente(
	                clienteDoc.getId(),
	                clienteDoc.getString("nombre"),
	                clienteDoc.getString("apellido1"),
	                clienteDoc.getString("apellido2"),
	                clienteDoc.getString("fecha_nacimiento"),
	                clienteDoc.getString("email"),
	                clienteDoc.getString("password")
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
}
