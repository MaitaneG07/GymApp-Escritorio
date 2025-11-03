package modelo.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import modelo.entity.Cliente;
import modelo.entity.Workout;
import modelo.exceptions.FileException;

public class Backup {

	private static final String BACKUP_FILE = "backup.dat";

	public static void writeBinaryFile(List<Cliente> clientes, List<Workout> workouts) throws FileException {
		File file = null;
		FileOutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {
			file = new File(BACKUP_FILE);

			outputStream = new FileOutputStream(file);

			objectOutputStream = new ObjectOutputStream(outputStream);

			for (Cliente cliente : clientes) {
				objectOutputStream.writeObject(cliente);
			}

			for (Workout workout : workouts) {
				objectOutputStream.writeObject(workout);
			}

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
	public static void readBinaryFile(List<Cliente> clientes, List<Workout> workouts) throws FileException {
		File file = new File(BACKUP_FILE);
		FileInputStream inputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			file = new File(BACKUP_FILE);

			if (!file.exists()) {
				throw new FileException("El archivo de backup no existe");
			}

			inputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(inputStream);

			clientes.clear();
			workouts.clear();

			try {
				while (true) {
					Object obj = objectInputStream.readObject();

					if (obj instanceof Cliente) {
						clientes.add((Cliente) obj);
					} else if (obj instanceof Workout) {
						workouts.add((Workout) obj);
					}
				}
			} catch (EOFException e) {
			}

			System.out.println("Backup cargado desde " + BACKUP_FILE);
			System.out.println("Clientes cargados: " + clientes.size());
			System.out.println("Workouts cargados: " + workouts.size());

		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			throw new FileException("Error al leer las clases - " + e.getLocalizedMessage());
		} catch (Exception e) {
			throw new FileException("Error - " + e.getLocalizedMessage());
		} finally {
			try {
				if (objectInputStream != null)
					objectInputStream.close();
			} catch (IOException e) {
			}
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}
	}
}
