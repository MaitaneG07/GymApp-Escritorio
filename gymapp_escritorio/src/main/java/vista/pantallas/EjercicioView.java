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
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controlador.ControladorCronometros;
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
	private CronometroLogica cronometroWorkout;
	private CronometroLogica cronometroEjercicio;
	private FirebaseGestor firebaseGestor;
	private JLabel lblDuracionEjercicio;
	private boolean enDescanso = false;

	private final ControladorCronometros controladorMaestro = new ControladorCronometros();
	private TemporizadorLogica temporizadorSerie;
	private TemporizadorLogica descansoSerie;

	private List<Ejercicio> ejerciciosWorkoutSeleccionado;
	private Ejercicio ejercicioSeleccionado;
	private List<Serie> seriesActuales;
	private List<Ejercicio> listaEjercicios;

	private boolean cuentaAtrasMostrada = false;
	
	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("🛠️Seteando ID Cliente en PanelViajesEventos: " + idCliente);
		System.out.println("🛠️Seteando Nivel Cliente en PanelViajesEventos: " + nivel);
	}

	public EjercicioView(String idCliente, String nivel, String idWorkoutSeleccionado, List<Ejercicio> ejercicios) throws FireBaseException {

		this.idCliente = idCliente;
		this.nivel = nivel;
		this.idWorkoutSeleccionado = idWorkoutSeleccionado;
		this.listaEjercicios = ejercicios;
		firebaseGestor = new FirebaseGestor();

		ejerciciosWorkoutSeleccionado = firebaseGestor.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);

		for (Ejercicio e : ejerciciosWorkoutSeleccionado) {
			if (!e.isCompletado()) {
				ejercicioSeleccionado = e;
				break;
			}
		}

		if (ejercicioSeleccionado != null) {
			cargarSeriesLocal(ejercicioSeleccionado);
		} else {
			seriesActuales = new ArrayList<>();
			ejercicioSeleccionado = new Ejercicio();
			ejercicioSeleccionado.setNombre("Fin de Workout");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPerfil = new JButton();
		ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA_AKIRA_PC));
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
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WorkoutView panelWorkout = new WorkoutView(idCliente, nivel);
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBackground(new Color(255, 255, 255));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pausarTodosLosTemporizadores();
				controladorMaestro.reanudarTodos();
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

		// Cronómetro total del workout (siempre activo)
		cronometroWorkout = new CronometroLogica(() -> {
		    String tiempo = cronometroWorkout.obtenerTiempoFormateado();
		    lblCronometroWorkout.setText(tiempo);
		}, controladorMaestro);

		// Cronómetro de ejercicio (solo actualiza si no está en descanso)
		cronometroEjercicio = new CronometroLogica(() -> {
		    if (!enDescanso) {
		        String tiempo = cronometroEjercicio.obtenerTiempoFormateado();
		        lblCronometroTotalEjercicio.setText(tiempo);
		    }
		}, controladorMaestro);
		
		lblCronometroTotalEjercicio = new JLabel(cronometroEjercicio.obtenerTiempoFormateado());
		lblCronometroTotalEjercicio.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCronometroTotalEjercicio.setBounds(44, 201, 180, 36);
		contentPane.add(lblCronometroTotalEjercicio);

		btnCronometro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejarFlujoCronometro();
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

		actualizarTablaSeries();

		tableSeries.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

			private static final long serialVersionUID = 6822466644230671528L;

			@Override
			public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				java.awt.Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				try {
					boolean completado = false;
					if (seriesActuales != null && row < seriesActuales.size()) {
						completado = seriesActuales.get(row).isCompletado();
					}

					if (completado) {
						cell.setBackground(new java.awt.Color(144, 238, 144));
						cell.setForeground(Color.BLACK);
					} else {
						if (row == obtenerIndicePrimeraSerieNoCompletada()) {
							cell.setBackground(new java.awt.Color(255, 255, 153));
						} else {
							cell.setBackground(new java.awt.Color(255, 160, 160));
						}
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

		String duracionSerieStr = obtenerDatoPrimeraSerieNoCompletada("tiempo asignado");
		String descansoSerieStr = obtenerDatoPrimeraSerieNoCompletada("tiempo descanso");

		lblcantidadDescanso = new JLabel(descansoSerieStr, SwingConstants.CENTER);
		lblcantidadDescanso.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblcantidadDescanso.setBounds(158, 380, 46, 26);
		contentPane.add(lblcantidadDescanso);

		lblCuentaSerie = new JLabel(convertirTiempoAFromatoCronometro(duracionSerieStr));
		lblCuentaSerie.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCuentaSerie.setBounds(44, 296, 210, 65);
		contentPane.add(lblCuentaSerie);

		lblCuentaDescanso = new JLabel(convertirTiempoAFromatoCronometro(descansoSerieStr));
		lblCuentaDescanso.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCuentaDescanso.setBounds(48, 413, 206, 60);
		contentPane.add(lblCuentaDescanso);
		lblCuentaDescanso.setVisible(false);

		JTextArea textADescripcion = new JTextArea();
		textADescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		textADescripcion.setText(obtenerDatoEjercicioSeleccionado("Descripcion"));
		textADescripcion.setBounds(0, 7, 347, 159);
		contentPane.add(textADescripcion);
		textADescripcion.setLineWrap(true);
		textADescripcion.setWrapStyleWord(true);

		JScrollPane scrollDescripcion = new JScrollPane(textADescripcion);
		scrollDescripcion.setBounds(332, 110, 347, 159);
		contentPane.add(scrollDescripcion);

		lblDuracionEjercicio = new JLabel(duracionSerieStr, SwingConstants.CENTER);
		lblDuracionEjercicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDuracionEjercicio.setBounds(158, 263, 46, 26);
		contentPane.add(lblDuracionEjercicio);

		JLabel lblTiempoTotalEjercicioTitulo = new JLabel("Tiempo total ejercicio:");
		lblTiempoTotalEjercicioTitulo.setBounds(44, 168, 143, 14);
		contentPane.add(lblTiempoTotalEjercicioTitulo);
	}

	private void manejarFlujoCronometro() {
		if (ejercicioSeleccionado == null || obtenerPrimeraSerieNoCompletada() == null) {
			finalizarEjercicio();
			return;
		}

		boolean estabaPausadoGlobalmente = controladorMaestro.isPausado();
		boolean primeraEjecucion = !cronometroWorkout.estaCorriendo() && !estabaPausadoGlobalmente;

		if (cronometroWorkout.estaCorriendo() && !estabaPausadoGlobalmente) {
			controladorMaestro.pausarTodos();
			btnCronometro.setText(Constants.INICIAR_BOTON);
			btnCronometro.setBackground(new Color(0, 128, 0));

		} else {
			if (primeraEjecucion) {
				mostrarCuentaAtras(() -> {
					cronometroWorkout.iniciar();
					cronometroEjercicio.iniciar();
					iniciarSerieActual();
					btnCronometro.setText(Constants.PAUSAR_BOTON);
					btnCronometro.setBackground(new Color(139, 0, 0));
				});

				cuentaAtrasMostrada = true;

			} else if (estabaPausadoGlobalmente) {
				controladorMaestro.reanudarTodos();
				btnCronometro.setText(Constants.PAUSAR_BOTON);
				btnCronometro.setBackground(new Color(139, 0, 0));
			}
		}
	}

	private void iniciarSerieActual() {
		try {
			String tiempoSerieStr = obtenerDatoPrimeraSerieNoCompletada("tiempo asignado");
			int tiempoSerie = Integer.parseInt(tiempoSerieStr);

			if (tiempoSerie <= 0) {
				manejarFinalizacionDescanso();
				return;
			}

			Runnable alFinalizarSerie = () -> SwingUtilities.invokeLater(() -> {
				iniciarDescansoActual();
			});

			temporizadorSerie = new TemporizadorLogica(
					() -> SwingUtilities
							.invokeLater(() -> lblCuentaSerie.setText(temporizadorSerie.obtenerTiempoFormateado())),
					alFinalizarSerie, controladorMaestro);

			temporizadorSerie.iniciar(tiempoSerie);

			lblCuentaSerie.setVisible(true);
			lblCuentaDescanso.setVisible(false);

		} catch (NumberFormatException e) {
			System.err.println("Error: El tiempo de la serie no es un número válido. " + e.getMessage());
		}
	}

	private void iniciarDescansoActual() {
		try {
			enDescanso = true;
			lblCronometroTotalEjercicio.setForeground(Color.GRAY);
			
			String tiempoDescansoStr = obtenerDatoPrimeraSerieNoCompletada("tiempo descanso");
			int tiempoDescanso = Integer.parseInt(tiempoDescansoStr);

			if (tiempoDescanso <= 0) {
				manejarFinalizacionDescanso();
				return;
			}

			Runnable alFinalizarDescanso = () -> SwingUtilities.invokeLater(() -> {
				manejarFinalizacionDescanso();
			});

			descansoSerie = new TemporizadorLogica(
					() -> SwingUtilities
							.invokeLater(() -> lblCuentaDescanso.setText(descansoSerie.obtenerTiempoFormateado())),
					alFinalizarDescanso, controladorMaestro);

			descansoSerie.iniciar(tiempoDescanso);

			lblCuentaSerie.setVisible(false);
			lblCuentaDescanso.setVisible(true);

		} catch (NumberFormatException e) {
			System.err.println("Error: El tiempo de descanso no es un número válido. " + e.getMessage());
		}
	}

	private void manejarFinalizacionDescanso() {
		enDescanso = false;
		lblCronometroTotalEjercicio.setForeground(Color.BLACK);
		
		Serie serieActual = obtenerPrimeraSerieNoCompletada();
		if (serieActual != null) {
			serieActual.setCompletado(true);

			if (descansoSerie != null) {
				descansoSerie.detener();
				descansoSerie = null;
			}
			if (temporizadorSerie != null) {
				temporizadorSerie.detener();
				temporizadorSerie = null;
			}

			actualizarTablaSeries();
		}

		Serie siguienteSerie = obtenerPrimeraSerieNoCompletada();

		if (siguienteSerie != null) {
			actualizarUIConSiguienteSerie(siguienteSerie);
			iniciarSerieActual();
		} else {
			finalizarEjercicio();
		}
	}

	private void pausarTodosLosTemporizadores() {
		if (cronometroWorkout != null) {
	        cronometroWorkout.detener();
	    }
	    if (cronometroEjercicio != null) {
	        cronometroEjercicio.detener();
	    }
	    if (temporizadorSerie != null) {
	        temporizadorSerie.detener();
	    }
	    if (descansoSerie != null) {
	        descansoSerie.detener();
	    }
	}

	private void finalizarEjercicio() {
		pausarTodosLosTemporizadores();
		controladorMaestro.reanudarTodos();

		if (ejercicioSeleccionado != null) {
			ejercicioSeleccionado.setCompletado(true);
		}
		if (cronometroWorkout != null) cronometroWorkout.detener();
		if (cronometroEjercicio != null) cronometroEjercicio.detener();

		btnCronometro.setText("¡COMPLETADO!");
		btnCronometro.setBackground(new Color(34, 139, 34));
		lblCuentaSerie.setText("00:00:00");
		lblCuentaDescanso.setText("00:00:00");
	}

	private void actualizarUIConSiguienteSerie(Serie siguienteSerie) {
		String duracionSerieStr = siguienteSerie.getTiempoDuracion();
		String descansoSerieStr = siguienteSerie.getTiempoDescanso();

		lblDuracionEjercicio.setText(duracionSerieStr);
		lblcantidadDescanso.setText(descansoSerieStr);

		lblCuentaSerie.setText(convertirTiempoAFromatoCronometro(duracionSerieStr));
		lblCuentaDescanso.setText(convertirTiempoAFromatoCronometro(descansoSerieStr));
	}

	private void actualizarTablaSeries() {
		tablaDetallesSeries.setRowCount(0);

		if (ejercicioSeleccionado != null) {
			for (Serie serie : seriesActuales) {
				String[] fila = { serie.getNombre(), serie.getTiempoDuracion(), serie.getTiempoDescanso() };
				tablaDetallesSeries.addRow(fila);
			}
		}
		tableSeries.repaint();
	}

	private void mostrarCuentaAtras(Runnable onFinish) {
		JDialog dialog = new JDialog(this, "Preparado...", true);
		JLabel label = new JLabel(ejercicioSeleccionado.getNombre() + " empieza en 5", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 25));
		dialog.getContentPane().add(label);
		dialog.setSize(300, 200);
		dialog.setLocationRelativeTo(this);

		btnCronometro.setEnabled(false);

		new Thread(() -> {
			try {
				for (int i = 5; i >= 1; i--) {
					final int segundos = i;
					SwingUtilities
							.invokeLater(() -> label.setText(ejercicioSeleccionado.getNombre() + " en: " + segundos));
					Thread.sleep(1000);
				}
				SwingUtilities.invokeLater(onFinish);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				SwingUtilities.invokeLater(() -> {
					dialog.dispose();
					btnCronometro.setEnabled(true);
				});
			}
		}).start();

		dialog.setVisible(true);
	}

	private Serie obtenerPrimeraSerieNoCompletada() {
		if (seriesActuales == null || seriesActuales.isEmpty()) {
			return null;
		}

		for (Serie serie : seriesActuales) {
			if (!serie.isCompletado()) {
				return serie;
			}
		}
		return null;
	}

	private int obtenerIndicePrimeraSerieNoCompletada() {
		if (seriesActuales == null || seriesActuales.isEmpty()) {
			return -1;
		}

		for (int i = 0; i < seriesActuales.size(); i++) {
			if (!seriesActuales.get(i).isCompletado()) {
				return i;
			}
		}
		return -1;
	}

	private String obtenerDatoPrimeraSerieNoCompletada(String dato) {
		Serie serie = obtenerPrimeraSerieNoCompletada();
		if (serie == null) {
			return "0";
		}

		switch (dato.toLowerCase()) {
		case "id":
			return serie.getId();
		case "nombre":
			return serie.getNombre();
		case "tiempo asignado":
			return serie.getTiempoDuracion();
		case "tiempo descanso":
			return serie.getTiempoDescanso();
		case "completado":
			return String.valueOf(serie.isCompletado());
		default:
			return null;
		}
	}

	private String obtenerDatoEjercicioSeleccionado(String dato) throws FireBaseException {
		if (ejercicioSeleccionado == null)
			return null;

		if (dato.equalsIgnoreCase("id")) {
			return ejercicioSeleccionado.getId();
		} else if (dato.equalsIgnoreCase("nombre")) {
			return ejercicioSeleccionado.getNombre();
		} else if (dato.equalsIgnoreCase("descripcion")) {
			return ejercicioSeleccionado.getDescripcion();
		} else if (dato.equalsIgnoreCase("numero de series")) {
			return String.valueOf(
					(ejercicioSeleccionado.getSeries() != null) ? ejercicioSeleccionado.getSeries().size() : 0);
		} else if (dato.equalsIgnoreCase("completado")) {
			return String.valueOf(ejercicioSeleccionado.isCompletado());
		} else {
			return null;
		}
	}

	private void cargarSeriesLocal(Ejercicio ejercicio) {

		seriesActuales = new ArrayList<>();
		if (ejercicio.getSeries() != null) {
			for (Serie serie : ejercicio.getSeries()) {

				Serie copiaSerie = new Serie();
				copiaSerie.setId(serie.getId());
				copiaSerie.setNombre(serie.getNombre());
				copiaSerie.setTiempoDuracion(serie.getTiempoDuracion());
				copiaSerie.setTiempoDescanso(serie.getTiempoDescanso());
				copiaSerie.setCompletado(serie.isCompletado());
				seriesActuales.add(copiaSerie);
			}
		}
	}

	private String convertirTiempoAFromatoCronometro(String tiempo) {
		try {
			int segundosTotales = Integer.parseInt(tiempo);

			int minutos = segundosTotales / 60;
			int segundos = segundosTotales % 60;
			int centesimas = 0;

			return String.format("%02d:%02d:%02d", minutos, segundos, centesimas);
		} catch (NumberFormatException e) {
			return "00:00:00";
		}
	}
}
