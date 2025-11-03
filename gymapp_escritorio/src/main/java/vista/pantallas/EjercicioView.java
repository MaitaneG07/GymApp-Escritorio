package vista.pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.Constants;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controlador.CronometroLogica;
import controlador.TemporizadorLogica;
import modelo.entity.Ejercicio;
import modelo.entity.Serie;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;
import javax.swing.JTextArea;

public class EjercicioView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCronometro;
	private JLabel lblCronometroWorkout;
	private JLabel lblNombreEjercicio;
	private JLabel lblCronometroTotalEjercicio;
	private JLabel lblTiempoTotalEjercicio;
	private JLabel lblCuentaSerie;
	private JLabel lblcantidadDescanso;
	private JLabel lblCuentaDescanso;
	private DefaultTableModel tablaDetallesSeries;
	private JTable tableSeries;
	private JScrollPane scrollPane;
	private JLabel lblFotoEjercicio;
	private JButton btnPerfil;
	private String idCliente;
	private String nivel;
	private String idWorkoutSeleccionado;
	private CronometroLogica cronometro;
	private FirebaseGestor firebaseGestor;
	private String tiempoTotalWorkout;
	private String tiempoTotalEjercicio;
	private String tiempoActualEjercicio;

	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("🛠️Seteando ID Cliente en PanelViajesEventos: " + idCliente);
		System.out.println("🛠️Seteando Nivel Cliente en PanelViajesEventos: " + nivel);
	}

	/**
	 * Create the frame.
	 * 
	 * @throws FireBaseException
	 */
	public EjercicioView(String idCliente, String nivel, String idWorkoutSeleccionado) throws FireBaseException {

		this.idCliente = idCliente;
		this.nivel = nivel;
		this.idWorkoutSeleccionado = idWorkoutSeleccionado;
		firebaseGestor = new FirebaseGestor();
		List<Ejercicio> ejerciciosWorkoutSeleccionado = firebaseGestor
				.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPerfil = new JButton();

		// LOGO MAITANE
		ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA_AKIRA_PC));
//		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);

		lblCronometroWorkout = new JLabel("00:00:00", SwingConstants.CENTER);
		lblCronometroWorkout.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblCronometroWorkout.setBounds(580, 11, 265, 74);
		contentPane.add(lblCronometroWorkout);

		JButton btnSalir = new JButton(Constants.SALIR_BOTON);
		btnSalir.setBackground(new Color(255, 255, 255));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WorkoutView panelWorkout = new WorkoutView(idCliente, nivel);
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(664, 555, 121, 23);
		contentPane.add(btnSalir);

		btnCronometro = new JButton(Constants.INICIAR_BOTON);
		btnCronometro.setForeground(new Color(255, 255, 255));
		btnCronometro.setOpaque(true);
		btnCronometro.setContentAreaFilled(true);
		btnCronometro.setBorderPainted(false);
		btnCronometro.setFocusPainted(false);
		btnCronometro.setBackground(new Color(0, 128, 0));
		String frase1 = Constants.INICIAR_BOTON;
		String frase2 = Constants.PARAR_BOTON;

		final boolean[] esFrase1 = { true };

		cronometro = new CronometroLogica(() -> lblCronometroWorkout.setText(cronometro.obtenerTiempoFormateado()));
		
		final TemporizadorLogica[] temporizadorEjercicio = new TemporizadorLogica[1];
		final TemporizadorLogica[] temporizadorSerie = new TemporizadorLogica[1];
		final TemporizadorLogica[] descansoSerie = new TemporizadorLogica[1];

		btnCronometro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
		            String tiempoSerieStr = obtenerDatoPrimeraSerieNoCompletada("tiempo asignado");
		            int tiempoSerie = Integer.parseInt(tiempoSerieStr);

		            String tiempoDescansoStr = obtenerDatoPrimeraSerieNoCompletada("tiempo descanso");
		            int tiempoDescanso = Integer.parseInt(tiempoDescansoStr);

		            if (!cronometro.estaCorriendo()) {
		                // Cronómetro del workout
		            	mostrarCuentaAtras(obtenerDatoEjercicioSeleccionado("nombre"));
		                cronometro.iniciar();
		                btnCronometro.setText(Constants.PAUSAR_BOTON);
		                btnCronometro.setBackground(new Color(139, 0, 0));

		                // Cronómetro total del ejercicio
		                temporizadorEjercicio[0] = new TemporizadorLogica(
		                        () -> lblCronometroTotalEjercicio.setText(
		                                temporizadorEjercicio[0].obtenerTiempoFormateado()),
		                        null
		                );
		                temporizadorEjercicio[0].iniciar(tiempoSerie);

		                // Contador de la serie actual
		                temporizadorSerie[0] = new TemporizadorLogica(
		                        () -> lblCuentaSerie.setText(
		                                temporizadorSerie[0].obtenerTiempoFormateado()),
		                        () -> {
		                            // Al finalizar la serie, iniciar descanso
		                            descansoSerie[0] = new TemporizadorLogica(
		                                    () -> lblCuentaDescanso.setText(
		                                            descansoSerie[0].obtenerTiempoFormateado()),
		                                    null
		                            );
		                            descansoSerie[0].iniciar(tiempoDescanso);
		                        }
		                );
		                temporizadorSerie[0].iniciar(tiempoSerie);

		            } else {
		                // Pausar o reanudar todos
		                cronometro.pausarReanudar();
		                if (temporizadorEjercicio[0] != null) temporizadorEjercicio[0].pausarReanudar();
		                if (temporizadorSerie[0] != null) temporizadorSerie[0].pausarReanudar();
		                if (descansoSerie[0] != null) descansoSerie[0].pausarReanudar();

		                btnCronometro.setText(Constants.INICIAR_BOTON);
		                btnCronometro.setBackground(new Color(0, 128, 0));
		            }
		        } catch (FireBaseException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		btnCronometro.setBounds(360, 520, 133, 58);
		contentPane.add(btnCronometro);

		lblNombreEjercicio = new JLabel(obtenerDatoEjercicioSeleccionado("nombre"), SwingConstants.CENTER);
		lblNombreEjercicio.setFont(new Font("Arial", Font.BOLD, 30));
		lblNombreEjercicio.setBounds(125, 11, 368, 55);
		contentPane.add(lblNombreEjercicio);

		lblFotoEjercicio = new JLabel("");
		lblFotoEjercicio.setIcon(new ImageIcon(
				"C:\\Users\\in2dm3-v\\Documents\\Reto 1\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\remo.jpg"));
		lblFotoEjercicio.setBounds(695, 110, 150, 159);
		contentPane.add(lblFotoEjercicio);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(332, 323, 513, 159);
		contentPane.add(scrollPane);

		tablaDetallesSeries = new DefaultTableModel();
		tablaDetallesSeries.addColumn(Constants.COLUMNA_SERIES);
		tablaDetallesSeries.addColumn(Constants.COLUMNA_TIEMPO);
		tablaDetallesSeries.addColumn(Constants.COLUMNA_DESCANSO);

		tableSeries = new JTable(tablaDetallesSeries);
		scrollPane.setViewportView(tableSeries);

		tablaDetallesSeries.setRowCount(0);

		for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
			for (Serie serie : ejercicio.getSeries()) {

				String[] fila = { serie.getNombre(), serie.getTiempoDuracion(), serie.getTiempoDescanso() };
				tablaDetallesSeries.addRow(fila);

			}
		}

		JLabel lblEjercicio = new JLabel("Duración serie:");
		lblEjercicio.setBounds(44, 271, 107, 14);
		contentPane.add(lblEjercicio);

		JLabel lblDescanso = new JLabel("Descanso:");
		lblDescanso.setBounds(44, 388, 60, 14);
		contentPane.add(lblDescanso);

		lblcantidadDescanso = new JLabel(obtenerDatoPrimeraSerieNoCompletada("tiempo descanso"),
				SwingConstants.CENTER);
		lblcantidadDescanso.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblcantidadDescanso.setBounds(158, 380, 46, 26);
		contentPane.add(lblcantidadDescanso);

		lblCuentaSerie = new JLabel(
				convertirTiempoAFromatoCronometro((obtenerDatoPrimeraSerieNoCompletada("tiempo asignado"))));
		lblCuentaSerie.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCuentaSerie.setBounds(44, 296, 210, 65);
		contentPane.add(lblCuentaSerie);

		lblCuentaDescanso = new JLabel(
				convertirTiempoAFromatoCronometro((obtenerDatoPrimeraSerieNoCompletada("tiempo descanso"))));
		lblCuentaDescanso.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCuentaDescanso.setBounds(48, 413, 206, 60);
		contentPane.add(lblCuentaDescanso);

		JTextArea textADescripcion = new JTextArea();
		textADescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		textADescripcion.setText(obtenerDatoEjercicioSeleccionado("Descripcion"));
		textADescripcion.setBounds(0, 7, 347, 159);
		contentPane.add(textADescripcion);
		// estas dos siguientes lineas son para que no se corte el texto si la frase es
		// larga
		textADescripcion.setLineWrap(true);
		textADescripcion.setWrapStyleWord(true);
		// un scroll
		JScrollPane scrollDescripcion = new JScrollPane(textADescripcion);
		scrollDescripcion.setBounds(332, 110, 347, 159);
		contentPane.add(scrollDescripcion);

		JLabel lblDuracionEjercicio = new JLabel(obtenerDatoPrimeraSerieNoCompletada("tiempo asignado"),
				SwingConstants.CENTER);
		lblDuracionEjercicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDuracionEjercicio.setBounds(158, 263, 46, 26);
		contentPane.add(lblDuracionEjercicio);

		lblTiempoTotalEjercicio = new JLabel("Tiempo total ejercicio:");
		lblTiempoTotalEjercicio.setBounds(44, 168, 143, 14);
		contentPane.add(lblTiempoTotalEjercicio);

		lblCronometroTotalEjercicio = new JLabel(cronometro.obtenerTiempoFormateado());
		lblCronometroTotalEjercicio.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCronometroTotalEjercicio.setBounds(44, 201, 180, 36);
		contentPane.add(lblCronometroTotalEjercicio);

	}

	/**
	 * Metodo que devuelve el dato que elijas del primer ejercicio que tecla completado = false
	 * 
	 * @param dato (nombre del dato que se quiere obtener)
	 * @return El valor del dato seleccionado, si estan todos completado = true devuelve null
	 * @throws FireBaseException
	 */
	private String obtenerDatoEjercicioSeleccionado(String dato) throws FireBaseException {
		List<Ejercicio> ejerciciosWorkoutSeleccionado =
	            firebaseGestor.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

	    for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
	        if (!ejercicio.isCompletado()) {
	            if (dato.equalsIgnoreCase("id")) {
	                return ejercicio.getId();
	            } else if (dato.equalsIgnoreCase("nombre")) {
	                return ejercicio.getNombre();
	            } else if (dato.equalsIgnoreCase("descripcion")) {
	                return ejercicio.getDescripcion();
	            } else if (dato.equalsIgnoreCase("numero de series")) {
	                return String.valueOf(
	                        (ejercicio.getSeries() != null) ? ejercicio.getSeries().size() : 0);
	            } else if (dato.equalsIgnoreCase("completado")) {
	                return String.valueOf(ejercicio.isCompletado());
	            } else {
	                return null;
	            }
	        }
	    }
	    return null;
	}

	/**
	 * Metodo que devuelve el dato que elijas de la primera seriq eu tenga
	 * completado en false
	 * 
	 * @param dato (nombre del dato que se quiere obtener)
	 * @return El valor del dato seleccionado, si estan todos completado = true devuelve null
	 * @throws FireBaseException
	 */
	private String obtenerDatoPrimeraSerieNoCompletada(String dato) throws FireBaseException {

		List<Ejercicio> ejerciciosWorkoutSeleccionado = firebaseGestor
				.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

		for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
			List<Serie> series = ejercicio.getSeries();
			if (series != null && !series.isEmpty()) {
				for (Serie serie : series) {
					if (!serie.isCompletado()) {
						if (dato.equals("id") || dato.equals("id serie")) {
							return serie.getId();
						} else if (dato.equals("nombre") || dato.equals("nombre serie")) {
							return serie.getNombre();
						} else if (dato.equals("tiempo asignado")) {
							return serie.getTiempoDuracion();
						} else if (dato.equals("tiempo descanso")) {
							return serie.getTiempoDescanso();
						} else if (dato.equals("completado") || dato.equals("completado serie")) {
							return String.valueOf(serie.isCompletado());
						} else {
							return null;
						}
					}
				}
			}
		}

		return null;
	}

	private String convertirTiempoAFromatoCronometro(String tiempo) {
		int segundosTotales = Integer.parseInt(tiempo);

		int minutos = segundosTotales / 60;
		int segundos = segundosTotales % 60;
		int centesimas = 0;

		return String.format("%02d:%02d:%02d", minutos, segundos, centesimas);
	}
	
	private void mostrarCuentaAtras(String nombreEjercicio) {
	    JDialog dialog = new JDialog(this, "Preparado...", true);
	    JLabel label = new JLabel(nombreEjercicio + " empieza en 5", SwingConstants.CENTER);
	    label.setFont(new Font("Arial", Font.BOLD, 15));
	    dialog.add(label);
	    dialog.setSize(250, 150);
	    dialog.setLocationRelativeTo(this);

	    new Thread(() -> {
	        try {
	            for (int i = 5; i >= 1; i--) {
	                final int segundos = i;
	                SwingUtilities.invokeLater(() -> label.setText(nombreEjercicio + " empieza en " + segundos));
	                Thread.sleep(1000);
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        dialog.dispose();
	    }).start();

	    dialog.setVisible(true);
	}
}
