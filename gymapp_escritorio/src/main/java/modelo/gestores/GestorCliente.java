package modelo.gestores;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import modelo.entity.Cliente;
import modelo.exceptions.FireBaseException;


public class GestorCliente {
	
	Cliente cliente = new Cliente();
	
	
	//manejar excepciones
	public GestorCliente() throws Exception {
	    try {
	        if (FirebaseApp.getApps().isEmpty()) {
	            InputStream serviceAccount = GestorCliente.class.getResourceAsStream("/FirebaseDB.json");
	            FirebaseOptions options = FirebaseOptions.builder()
	                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                    .build();
	            FirebaseApp.initializeApp(options);
	            System.out.println("Firebase ok");
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Imprime el error real
	    }
	}

	



	public Cliente getCliente(String name) throws FireBaseException {
	    try {
	    	  Firestore db = FirestoreClient.getFirestore();

	          // Consulta en todas las subcolecciones llamadas "clientes" por nombre
	          ApiFuture<QuerySnapshot> query = db.collection("Clientes")
	                                             .whereEqualTo("nombre", name)
	                                             .get();

	          List<QueryDocumentSnapshot> documentos = query.get().getDocuments();

	          if (!documentos.isEmpty()) {
	              QueryDocumentSnapshot doc = documentos.get(0);
	              return new Cliente(
	                  doc.getString("nombre"),
	                  doc.getString("fecha_nacimiento"),
	                  doc.getString("apellido1"),
	                  doc.getString("apellido2"),
	                  doc.getString("email")
	              );
	          } else {
	              return null; // O lanzar una excepción si quieres
	          }

	    } catch (Exception e) {
	        throw new FireBaseException("Error - " + e.getLocalizedMessage());
	    }
	}

}
