package modelo.ficheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelo.entity.Cliente;
import modelo.exceptions.FileException;

public class Backup {

	private static final String BACKUP_FILE = "backup.dat";
	
	public static void writeBinaryFile(Cliente cliente) throws FileException {
		File file = null;
		FileOutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		try {
			file = new File(BACKUP_FILE);
			
			outputStream = new FileOutputStream(file);
			
			objectOutputStream = new ObjectOutputStream(outputStream);
			
			objectOutputStream.writeObject(cliente);
			
			System.out.println("Backup guardado en " + BACKUP_FILE);
			
		} catch (Exception e) {
			throw new FileException("Error - " + e.getLocalizedMessage());
		} finally {
			try {
				if (objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
				
			}
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
     * Lee el archivo binario y devuelve el Cliente guardado.
     */
    public static Cliente readBinaryFile() throws FileException {
        File file = new File(BACKUP_FILE);
        if (!file.exists()) {
            System.out.println("No se encontró el backup local (" + BACKUP_FILE + ")");
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Cliente cliente = (Cliente) ois.readObject();
            System.out.println("Backup cargado desde " + BACKUP_FILE);
            return cliente;

        } catch (Exception e) {
            throw new FileException("Error al leer el backup: " + e.getMessage());
        }
    }
}
