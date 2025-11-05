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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controlador.CronometroLogica;
import controlador.FirebaseController;
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
	@SuppressWarnings("unused")
	private String idCliente;
	@SuppressWarnings("unused")
	private String nivel;
	private String idWorkoutSeleccionado;
	@SuppressWarnings("unused")
	private String nombreWorkoutSeleccionado;
	private CronometroLogica cronometro;
	private FirebaseGestor firebaseGestor;
	@SuppressWarnings("unused")
	private String tiempoTotalWorkout = "00:00:00";
	@SuppressWarnings("unused")
	private String tiempoTotalEjercicio;
	@SuppressWarnings("unused")
	private String tiempoActualEjercicio;
	private List<Serie> seriesActuales;
	private JLabel lblDuracionEjercicio;
	private FirebaseController firebaseController;
	private int indiceEjercicioActual = 0;
	private List<Ejercicio> ejerciciosWorkoutSeleccionado;
	protected double porcentajeCompletado;

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
	@SuppressWarnings("serial")
	public EjercicioView(String idCliente, String nivel, String idWorkoutSeleccionado, String nombreWorkoutSeleccionado,
			List<Ejercicio> ejerciciosPrevios, int indiceEjercicioPrevio) throws FireBaseException {

		this.idCliente = idCliente;
		this.nivel = nivel;
		this.idWorkoutSeleccionado = idWorkoutSeleccionado;
		this.nombreWorkoutSeleccionado = nombreWorkoutSeleccionado;

		if (ejerciciosPrevios != null) {
			this.ejerciciosWorkoutSeleccionado = ejerciciosPrevios;
			this.indiceEjercicioActual = indiceEjercicioPrevio;
		} else {
			try {
				firebaseGestor = new FirebaseGestor();
			} catch (FireBaseException e) {
				e.printStackTrace();
			}
			firebaseController = new FirebaseController();
			this.ejerciciosWorkoutSeleccionado = firebaseGestor.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);
			this.indiceEjercicioActual = 0;
		}

		Ejercicio ejercicioSeleccionado = getSiguienteEjercicio();
		if (ejercicioSeleccionado != null) {
			cargarSeriesLocal(ejercicioSeleccionado);
		} else {
			System.out.println("No hay ejercicios pendientes en este workout.");

		}

		if (ejercicioSeleccionado != null) {
			cargarSeriesLocal(ejercicioSeleccionado);
		} else {
			System.out.println("No hay ejercicios pendientes en este workout.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPerfil = new JButton();

		// LOGO MAITANE
		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
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
		@SuppressWarnings("unused")
		String frase1 = Constants.INICIAR_BOTON;
		@SuppressWarnings("unused")
		String frase2 = Constants.PARAR_BOTON;

		@SuppressWarnings("unused")
		final boolean[] esFrase1 = { true };

		cronometro = new CronometroLogica(() -> lblCronometroWorkout.setText(cronometro.obtenerTiempoFormateado()));

		// CAMBIAR ESTAS DECLARACIONES:
		final CronometroLogica[] temporizadorEjercicio = new CronometroLogica[1]; // Era TemporizadorLogica
		final TemporizadorLogica[] temporizadorSerie = new TemporizadorLogica[1];
		final TemporizadorLogica[] descansoSerie = new TemporizadorLogica[1];

		btnCronometro.addActionListener(new ActionListener() {

			private boolean mostrarCuentaAtras = false;
			private int indiceSerieActual = 0;
			@SuppressWarnings("unused")
			private long tiempoInicioEjercicio = 0;

			public void actionPerformed(ActionEvent e) {
				try {
					if (!cronometro.estaCorriendo()) {

						if (!mostrarCuentaAtras) {
							mostrarCuentaAtras(obtenerDatoEjercicioSeleccionado("nombre"));
							mostrarCuentaAtras = true;
						}

						cronometro.iniciar();
						btnCronometro.setText(Constants.PAUSAR_BOTON);
						btnCronometro.setBackground(new Color(139, 0, 0));

						// Guardar tiempo de inicio
						tiempoInicioEjercicio = System.currentTimeMillis();

						temporizadorEjercicio[0] = new CronometroLogica(() -> lblCronometroTotalEjercicio
								.setText(temporizadorEjercicio[0].obtenerTiempoFormateado()));
						temporizadorEjercicio[0].iniciar();

						// Iniciar la primera serie
						iniciarSiguienteSerie();

					} else {
						cronometro.pausarReanudar();
						if (temporizadorEjercicio[0] != null)
							temporizadorEjercicio[0].pausarReanudar();
						if (temporizadorSerie[0] != null)
							temporizadorSerie[0].pausarReanudar();
						if (descansoSerie[0] != null)
							descansoSerie[0].pausarReanudar();

						btnCronometro.setText(Constants.INICIAR_BOTON);
						btnCronometro.setBackground(new Color(0, 128, 0));
					}
				} catch (FireBaseException ex) {
					ex.printStackTrace();
				}
			}

			/**
			 * Inicia la siguiente serie no completada
			 */
			private void iniciarSiguienteSerie() {
				try {
					Serie serieActual = null;
					for (int i = 0; i < seriesActuales.size(); i++) {
						if (!seriesActuales.get(i).isCompletado()) {
							serieActual = seriesActuales.get(i);
							indiceSerieActual = i;
							break;
						}
					}

					if (serieActual == null) {
						finalizarEjercicio();
						return;
					}

					int tiempoSerie = Integer.parseInt(serieActual.getTiempoDuracion());
					int tiempoDescanso = Integer.parseInt(serieActual.getTiempoDescanso());

					lblcantidadDescanso.setText(serieActual.getTiempoDescanso());
					lblDuracionEjercicio.setText(serieActual.getTiempoDuracion());

					lblCuentaSerie.setText(convertirTiempoAFromatoCronometro(serieActual.getTiempoDuracion()));
					lblCuentaDescanso.setText(convertirTiempoAFromatoCronometro(serieActual.getTiempoDescanso()));

					temporizadorSerie[0] = new TemporizadorLogica(
							() -> lblCuentaSerie.setText(temporizadorSerie[0].obtenerTiempoFormateado()), () -> {

								marcarSerieComoCompletada(indiceSerieActual);

								iniciarDescanso(tiempoDescanso);
							});

					temporizadorSerie[0].iniciar(tiempoSerie);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			/**
			 * Inicia el temporizador de descanso
			 */
			private void iniciarDescanso(int tiempoDescanso) {

				if (temporizadorEjercicio[0] != null) {
					temporizadorEjercicio[0].pausarReanudar();
				}

				descansoSerie[0] = new TemporizadorLogica(
						() -> lblCuentaDescanso.setText(descansoSerie[0].obtenerTiempoFormateado()), () -> {
							if (temporizadorEjercicio[0] != null) {
								temporizadorEjercicio[0].pausarReanudar();
							}
							iniciarSiguienteSerie();
						});
				descansoSerie[0].iniciar(tiempoDescanso);
			}

			/**
			 * Marca una serie como completada y actualiza la tabla
			 */
			private void marcarSerieComoCompletada(int indice) {
				if (indice >= 0 && indice < seriesActuales.size()) {
					seriesActuales.get(indice).setCompletado(true);

					// Forzar actualización visual de la tabla
					SwingUtilities.invokeLater(() -> {
						tableSeries.repaint();
						tableSeries.revalidate();
					});

					System.out.println("Serie completada: " + seriesActuales.get(indice).getNombre());
				}
			}

			/**
			 * Finaliza el ejercicio cuando todas las series están completadas
			 */
			// --- Método finalizarEjercicio actualizado ---
			private void finalizarEjercicio() {
				try {
					cronometro.pausarReanudar();
					if (temporizadorEjercicio[0] != null)
						temporizadorEjercicio[0].pausarReanudar();

					btnCronometro.setText("FINALIZADO");
					btnCronometro.setEnabled(false);
					btnCronometro.setBackground(Color.GRAY);

					porcentajeCompletado = calcularPorcentajeEjerciciosCompletados();

					System.out.println("Ejercicio completado!");
					System.out.println(
							"Porcentaje del workout completado: " + String.format("%.2f", porcentajeCompletado) + "%");

					// Guardar histórico en Firebase
					firebaseController.guardarWorkoutHistorico(idCliente, nombreWorkoutSeleccionado, nivel,
							lblCronometroWorkout.getText(), lblCronometroTotalEjercicio.getText(),
							java.time.LocalDateTime.now().toString(), String.format("%.2f", porcentajeCompletado));

					// Marcar ejercicio completado en memoria
					ejerciciosWorkoutSeleccionado.get(indiceEjercicioActual).setCompletado(true);
					indiceEjercicioActual++;

					// Mostrar pantalla de resumen
					ResumenView pantallaResumen = new ResumenView(idCliente, nivel,
							ejerciciosWorkoutSeleccionado.get(indiceEjercicioActual - 1).getNombre(),
							lblCronometroTotalEjercicio.getText(), String.format("%.2f", porcentajeCompletado),
							idWorkoutSeleccionado, nombreWorkoutSeleccionado);

					// Pasar la lista y el índice al volver
					pantallaResumen.setVisible(true);
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// --- Al volver desde ResumenView ---
				// Cuando crees un nuevo EjercicioView después de ResumenView:
				EjercicioView siguienteEjercicioView = null;
				try {
					siguienteEjercicioView = new EjercicioView(idCliente, nivel, idWorkoutSeleccionado,
							nombreWorkoutSeleccionado, ejerciciosWorkoutSeleccionado, indiceEjercicioActual);
				} catch (FireBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				siguienteEjercicioView.setVisible(true);
			}

			/**
			 * Calcula el porcentaje de ejercicios completados en el workout
			 * 
			 * @return Porcentaje de 0 a 100
			 */
			private double calcularPorcentajeEjerciciosCompletados() {
				try {
					List<Ejercicio> todosLosEjercicios = firebaseGestor
							.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

					if (todosLosEjercicios == null || todosLosEjercicios.isEmpty()) {
						return 0.0;
					}

					int totalEjercicios = todosLosEjercicios.size();
					int ejerciciosCompletados = 0;

					for (Ejercicio ejercicio : todosLosEjercicios) {
						if (ejercicio.isCompletado()) {
							ejerciciosCompletados++;
						}
					}

					ejerciciosCompletados++;

					return ((double) ejerciciosCompletados / totalEjercicios) * 100.0;

				} catch (FireBaseException ex) {
					ex.printStackTrace();
					return 0.0;
				}
			}
		});
		btnCronometro.setBounds(360, 520, 133, 58);
		contentPane.add(btnCronometro);

		try {
			lblNombreEjercicio = new JLabel(obtenerDatoEjercicioSeleccionado("nombre"), SwingConstants.CENTER);
		} catch (FireBaseException e1) {
			e1.printStackTrace();
		}
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

		// Para cambiar el color de la fila segun este completado o no
		tableSeries.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				java.awt.Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				try {
					String nombreSerie = (String) table.getValueAt(row, 0);

					// Buscar en seriesActuales (la lista local que se actualiza)
					boolean completado = false;
					if (seriesActuales != null) {
						for (Serie serie : seriesActuales) {
							if (serie.getNombre().equals(nombreSerie)) {
								completado = serie.isCompletado();
								break;
							}
						}
					}

					// Si no está en seriesActuales, buscar en todos los ejercicios
					if (!completado && seriesActuales != null) {
						for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
							for (Serie serie : ejercicio.getSeries()) {
								if (serie.getNombre().equals(nombreSerie)) {
									completado = serie.isCompletado();
									break;
								}
							}
							if (completado)
								break;
						}
					}

					if (completado) {
						cell.setBackground(new java.awt.Color(144, 238, 144));
						cell.setForeground(Color.BLACK);
					} else {
						cell.setBackground(new java.awt.Color(255, 160, 160));
						cell.setForeground(Color.BLACK);
					}

					if (isSelected) {
						cell.setBackground(cell.getBackground().darker());
					}

				} catch (Exception e) {
					cell.setBackground(Color.WHITE);
					cell.setForeground(Color.BLACK);
				}

				return cell;
			}
		});

		JLabel lblEjercicio = new JLabel("Duración serie:");
		lblEjercicio.setBounds(44, 271, 107, 14);
		contentPane.add(lblEjercicio);

		JLabel lblDescanso = new JLabel("Descanso:");
		lblDescanso.setBounds(44, 388, 60, 14);
		contentPane.add(lblDescanso);

		lblcantidadDescanso = new JLabel(obtenerDatoPrimeraSerieNoCompletada("tiempo descanso"), SwingConstants.CENTER);
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
		try {
			textADescripcion.setText(obtenerDatoEjercicioSeleccionado("Descripcion"));
		} catch (FireBaseException e1) {
			e1.printStackTrace();
		}
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

		lblDuracionEjercicio = new JLabel(obtenerDatoPrimeraSerieNoCompletada("tiempo asignado"),
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

	// --- Método para obtener el siguiente ejercicio ---
	private Ejercicio getSiguienteEjercicio() {
		while (indiceEjercicioActual < ejerciciosWorkoutSeleccionado.size()) {
			Ejercicio e = ejerciciosWorkoutSeleccionado.get(indiceEjercicioActual);
			if (!e.isCompletado())
				return e;
			indiceEjercicioActual++;
		}
		return null; // todos completados
	}

	/**
	 * Metodo que devuelve el dato que elijas del primer ejercicio que tecla
	 * completado = false
	 * 
	 * @param dato (nombre del dato que se quiere obtener)
	 * @return El valor del dato seleccionado, si estan todos completado = true
	 *         devuelve null
	 * @throws FireBaseException
	 */
	private String obtenerDatoEjercicioSeleccionado(String dato) throws FireBaseException {
		List<Ejercicio> ejerciciosWorkoutSeleccionado = firebaseGestor
				.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

		for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
			if (!ejercicio.isCompletado()) {
				if (dato.equalsIgnoreCase("id")) {
					return ejercicio.getId();
				} else if (dato.equalsIgnoreCase("nombre")) {
					return ejercicio.getNombre();
				} else if (dato.equalsIgnoreCase("descripcion")) {
					return ejercicio.getDescripcion();
				} else if (dato.equalsIgnoreCase("numero de series")) {
					return String.valueOf((ejercicio.getSeries() != null) ? ejercicio.getSeries().size() : 0);
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
	 * @return El valor del dato seleccionado, si estan todos completado = true
	 *         devuelve null
	 * @throws FireBaseException
	 */
	private String obtenerDatoPrimeraSerieNoCompletada(String dato) {

		if (seriesActuales == null || seriesActuales.isEmpty()) {
			return null;
		}

		for (Serie serie : seriesActuales) {
			if (!serie.isCompletado()) {
				switch (dato.toLowerCase()) {
				case "id":
				case "id serie":
					return serie.getId();
				case "nombre":
				case "nombre serie":
					return serie.getNombre();
				case "tiempo asignado":
					return serie.getTiempoDuracion();
				case "tiempo descanso":
					return serie.getTiempoDescanso();
				case "completado":
				case "completado serie":
					return String.valueOf(serie.isCompletado());
				default:
					return null;
				}
			}
		}

		return null;
	}

	// coge los datos recibidos del ejercicio
	private void cargarSeriesLocal(Ejercicio ejercicio) {

		seriesActuales = Collections.synchronizedList(new ArrayList<>());
		for (Serie serie : ejercicio.getSeries()) {

			Serie copiaSerie = new Serie();
			copiaSerie.setId(serie.getId());
			copiaSerie.setNombre(serie.getNombre());
			copiaSerie.setTiempoDuracion(serie.getTiempoDuracion());
			copiaSerie.setTiempoDescanso(serie.getTiempoDescanso());
			copiaSerie.setCompletado(serie.isCompletado());
			seriesActuales.add(copiaSerie);
		}

		// print para probar que carga en la lista local las series
		System.out.println("Series cargadas en private List<Serie> seriesActuales: " + ejercicio.getNombre());
		for (int i = 0; i < seriesActuales.size(); i++) {
			Serie s = seriesActuales.get(i);
			System.out.println(
					String.format("   [%d] ID: %s | Nombre: %s | Duración: %s | Descanso: %s | Completado: %s", i + 1,
							s.getId(), s.getNombre(), s.getTiempoDuracion(), s.getTiempoDescanso(), s.isCompletado()));
		}
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
