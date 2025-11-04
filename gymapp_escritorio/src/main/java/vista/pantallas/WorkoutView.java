package vista.pantallas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.FirebaseController;
import modelo.entity.Cliente;
import modelo.entity.Ejercicio;
import modelo.entity.Workout;
import modelo.exceptions.FileException;
import modelo.exceptions.FireBaseException;
import utils.Constants;
import vista.Login;

/**
 * Vista principal de gestión de Workouts.
 * 
 * Esta clase permite visualizar, filtrar y seleccionar workouts según el nivel
 * del cliente. Implementa un sistema de control de acceso basado en niveles
 * jerárquicos: - Principiante: Acceso solo a workouts de nivel Principiante -
 * Intermedio: Acceso a workouts de nivel Principiante e Intermedio - Avanzado:
 * Acceso a todos los workouts (Principiante, Intermedio y Avanzado)
 * 
 */
public class WorkoutView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPerfil;
	private JPopupMenu popupMenuPerfil;
	private JMenuItem menuGestionPerfil;
	private JMenuItem menuHistorico;
	private JMenuItem menuCerrarPerfil;
	private JTable tablaWorkouts;
	private JTable tablaDetallesWorkout;
	private JScrollPane scrollPaneWorkouts;
	private JScrollPane scrollPaneDetallesWorkout;
	private JButton btnSeleccionar;
	private JButton btnFiltrarPorNivel;
	private JPopupMenu popupMenuNivel;
	public static DefaultTableModel modeloWorkouts;
	private DefaultTableModel modeloDetallesWorkouts;
	private JLabel tituloWorkout;
	private FirebaseController firebaseController;
	private JMenuItem menuPrincipiante;
	private JMenuItem menuIntermedio;
	private JMenuItem menuAvanzado;
	private JButton btnNivel;
	@SuppressWarnings("unused")
	private String idCliente;
	private String nivel;
	private Component lblNivelCliente;
	private JMenuItem menuTodos;
	private String idWorkoutSeleccionado = null;
	private boolean online = utils.Network.isInternetAvailable();
	private List<Cliente> clientesBackup = new ArrayList<>();
	private List<Workout> workoutsBackup = new ArrayList<>();

	/**
	 * Establece el ID del cliente y su nivel.
	 * 
	 * @param idCliente ID único del cliente
	 * @param nivel     Nivel de experiencia del cliente
	 */
	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("Seteando ID Cliente: " + idCliente);
		System.out.println("Seteando Nivel Cliente: " + nivel);
	}

	/**
	 * Constructor de la vista de Workouts.
	 * 
	 * Inicializa todos los componentes visuales, configura los listeners y carga
	 * los workouts desde Firebase. Implementa el control de acceso basado en el
	 * nivel del cliente.
	 * 
	 * @param idCliente ID único del cliente autenticado
	 * @param nivel     Nivel de experiencia del cliente (Principiante, Intermedio o
	 *                  Avanzado)
	 */
	public WorkoutView(String idCliente, String nivel) {

		this.idCliente = idCliente;
		this.nivel = nivel;

		firebaseController = new FirebaseController();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tituloWorkout = new JLabel(Constants.WORKOUT_LABEL);
		tituloWorkout.setOpaque(true);
		tituloWorkout.setBackground(new Color(0, 0, 0, 0));
		tituloWorkout.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		tituloWorkout.setBounds(230, 23, 432, 79);
		contentPane.add(tituloWorkout);

		btnPerfil = new JButton();
		// LOGO MAITANE
		// ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA));
		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);

		popupMenuPerfil = new JPopupMenu();

		menuGestionPerfil = new JMenuItem(Constants.GESTIONAR_PERFIL_MENU);
		popupMenuPerfil.add(menuGestionPerfil);
		menuGestionPerfil.addActionListener(e -> {

			PerfilView pantallaPerfil = new PerfilView(idCliente, nivel);
			pantallaPerfil.setVisible(true);
			dispose();
		});

		menuHistorico = new JMenuItem(Constants.CONSULTAR_HISTORICO_MENU);
		popupMenuPerfil.add(menuHistorico);
		menuHistorico.addActionListener(e -> {

			HistoricoView pantallaHistorico = new HistoricoView(idCliente, nivel);
			pantallaHistorico.setVisible(true);
			dispose();
		});

		menuCerrarPerfil = new JMenuItem(Constants.CERRAR_SESION_MENU);
		popupMenuPerfil.add(menuCerrarPerfil);
		menuCerrarPerfil.addActionListener(e -> {

			Login pantallaLogin = new Login();
			pantallaLogin.setVisible(true);
			dispose();
		});

		btnPerfil.addActionListener(e -> {
			popupMenuPerfil.show(btnPerfil, 0, btnPerfil.getHeight());

		});

		btnNivel = new JButton();
		btnNivel.setBounds(761, 11, 102, 40);
		btnNivel.setFocusPainted(false);
		btnNivel.setContentAreaFilled(false);
		btnNivel.setBorderPainted(false);
		contentPane.add(btnNivel);

		popupMenuNivel = new JPopupMenu();

		menuPrincipiante = new JMenuItem(Constants.NIVEL_PRINCIPIANTE_MENU);
		popupMenuNivel.add(menuPrincipiante);
		menuPrincipiante.addActionListener(e -> {
			actualizarTablaWorkouts(modeloWorkouts, Constants.NIVEL_PRINCIPIANTE_MENU);
		});

		menuIntermedio = new JMenuItem(Constants.NIVEL_INTERMEDIO_MENU);
		popupMenuNivel.add(menuIntermedio);
		menuIntermedio.addActionListener(e -> {
			actualizarTablaWorkouts(modeloWorkouts, Constants.NIVEL_INTERMEDIO_MENU);
		});

		menuAvanzado = new JMenuItem(Constants.NIVEL_AVANZADO_MENU);
		popupMenuNivel.add(menuAvanzado);
		menuAvanzado.addActionListener(e -> {
			actualizarTablaWorkouts(modeloWorkouts, Constants.NIVEL_AVANZADO_MENU);
		});

		menuTodos = new JMenuItem(Constants.TODOS_NIVELES_MENU);
		popupMenuNivel.add(menuTodos);
		menuTodos.addActionListener(e -> {
			actualizarTablaWorkouts(modeloWorkouts, null);
		});

		btnNivel.addActionListener(e -> {
			popupMenuNivel.show(btnNivel, 0, btnNivel.getHeight());

		});

		scrollPaneWorkouts = new JScrollPane();
		scrollPaneWorkouts.setBounds(162, 131, 574, 149);
		contentPane.add(scrollPaneWorkouts);

		modeloWorkouts = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloWorkouts.addColumn(Constants.COLUMNA_ID_WORKOUT);
		modeloWorkouts.addColumn(Constants.COLUMNA_NOMBRE_WORKOUT);
		modeloWorkouts.addColumn(Constants.COLUMNA_NIVEL_WORKOUT);
		modeloWorkouts.addColumn(Constants.COLUMNA_VIDEO);
		modeloWorkouts.addColumn(Constants.COLUMNA_EJERCICIOS);

		tablaWorkouts = new JTable(modeloWorkouts);
		tablaWorkouts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tablaWorkouts.getColumnModel().getColumn(0).setMinWidth(0);
		tablaWorkouts.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaWorkouts.getColumnModel().getColumn(0).setWidth(0);

		// Agregar MouseListener para detectar el un clic y obtener el id
		tablaWorkouts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && tablaWorkouts.getSelectedRow() != -1) {
					int selectedRow = tablaWorkouts.getSelectedRow();
					String idWorkout = (String) tablaWorkouts.getValueAt(selectedRow, 0);
					String nivelWorkout = (String) tablaWorkouts.getValueAt(selectedRow, 2);

					System.out.println("ID Workout seleccionado: " + idWorkout);
					System.out.println("Nivel Workout seleccionado: " + nivelWorkout);
					System.out.println("Nivel Cliente: " + nivel);

					if (!puedeAccederAlWorkout(nivel, nivelWorkout)) {
						JOptionPane.showMessageDialog(WorkoutView.this, Constants.SIN_NIVEL, Constants.ACCESO_DENEGADO,
								JOptionPane.WARNING_MESSAGE);

						btnSeleccionar.setEnabled(false);
						idWorkoutSeleccionado = null;
					} else {
						actualizarTablaDetallesWorkout(modeloDetallesWorkouts, idWorkout);
						btnSeleccionar.setEnabled(true);
						idWorkoutSeleccionado = idWorkout;
					}
				}
			}
		});

		// Con doble click obtenemos el video y lo mostramos
		tablaWorkouts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && tablaWorkouts.getSelectedRow() != -1) {
					int selectedRow = tablaWorkouts.getSelectedRow();
					String video = (String) tablaWorkouts.getValueAt(selectedRow, 3);

					try {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().browse(new URI(video));
						} else {
							JOptionPane.showMessageDialog(null, "El sistema no soporta abrir enlaces automáticamente.");
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error al abrir el video: " + ex.getMessage());
					}
				}
			}
		});

		// Renderer personalizado para colorear filas según accesibilidad
		tablaWorkouts.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				String nivelWorkout = (String) table.getValueAt(row, 2);

				if (!puedeAccederAlWorkout(nivel, nivelWorkout)) {
					// Workout no accesible - gris claro
					c.setBackground(isSelected ? new Color(200, 200, 200) : new Color(240, 240, 240));
					c.setForeground(Color.GRAY);
				} else {
					// Workout accesible - colores normales
					c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
					c.setForeground(isSelected ? table.getSelectionForeground() : Color.BLACK);
				}

				return c;
			}
		});

		scrollPaneWorkouts.setViewportView(tablaWorkouts);

		scrollPaneDetallesWorkout = new JScrollPane();
		scrollPaneDetallesWorkout.setBounds(162, 351, 574, 149);
		contentPane.add(scrollPaneDetallesWorkout);

		modeloDetallesWorkouts = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_ID_EJERCICIO);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_NOMBRE_EJERCICIO);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_DESCRIPCION);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_SERIES);

		tablaDetallesWorkout = new JTable(modeloDetallesWorkouts);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setMinWidth(0);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setWidth(0);
		scrollPaneDetallesWorkout.setViewportView(tablaDetallesWorkout);

		actualizarTablaWorkouts(modeloWorkouts, null);

		btnSeleccionar = new JButton(Constants.SELECCIONAR_BOTON);
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (idWorkoutSeleccionado != null && btnSeleccionar.isEnabled()) {
					EjercicioView pantallaEjercicio = new EjercicioView(idCliente, nivel);
					pantallaEjercicio.setVisible(true);
					dispose();
				}
			}
		});
		btnSeleccionar.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnSeleccionar.setBounds(360, 554, 155, 40);
		btnSeleccionar.setFocusPainted(false);
		btnSeleccionar.setContentAreaFilled(false);
		btnSeleccionar.setBorderPainted(false);
		contentPane.add(btnSeleccionar);

		btnFiltrarPorNivel = new JButton();
		btnFiltrarPorNivel.setText(Constants.COLUMNA_NIVEL_WORKOUT);
		btnFiltrarPorNivel.setFocusPainted(false);
		btnFiltrarPorNivel.setContentAreaFilled(false);
		btnFiltrarPorNivel.setBorderPainted(false);
		btnFiltrarPorNivel.setBounds(761, 11, 102, 40);
		contentPane.add(btnFiltrarPorNivel);

		lblNivelCliente = new JLabel(Constants.NIVEL_USUARIO + this.nivel);
		lblNivelCliente.setBounds(10, 537, 231, 73);
		lblNivelCliente.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 14));
		contentPane.add(lblNivelCliente);

	}

	/**
	 * Compara si el cliente puede acceder a un workout según su nivel
	 * 
	 * @param nivelCliente El nivel del cliente
	 * @param nivelWorkout El nivel del workout
	 * @return true si puede acceder, false si no
	 */
	private boolean puedeAccederAlWorkout(String nivelCliente, String nivelWorkout) {

		int nivelClienteValor = obtenerValorNivel(nivelCliente);
		int nivelWorkoutValor = obtenerValorNivel(nivelWorkout);

		return nivelClienteValor >= nivelWorkoutValor;
	}

	/**
	 * Convierte el nombre del nivel a un valor numérico para comparar
	 * 
	 * @param nivel Nombre del nivel
	 * @return Valor numérico (1=Principiante, 2=Intermedio, 3=Avanzado)
	 */
	private int obtenerValorNivel(String nivel) {
		if (nivel == null)
			return 0;

		switch (nivel.toLowerCase()) {
		case "principiante":
			return 1;
		case "intermedio":
			return 2;
		case "avanzado":
			return 3;
		default:
			return 0;
		}
	}

	/**
	 * Actualiza la tabla de workouts aplicando un filtro por nivel (opcional).
	 * 
	 * Este método obtiene todos los workouts desde Firebase y los filtra según el
	 * nivel especificado. Si el filtro es null o vacío, muestra todos los workouts.
	 * 
	 * @param modeloWorkouts Modelo de la tabla donde se mostrarán los workouts
	 * @param nivelFiltro    Nivel por el que filtrar (Principiante, Intermedio,
	 *                       Avanzado) o null para mostrar todos
	 */
	public void actualizarTablaWorkouts(DefaultTableModel modeloWorkouts, String nivelFiltro) {
		modeloWorkouts.setRowCount(0);
		List<Workout> listaWorkouts = null;

		try {
			if (online) {
				listaWorkouts = firebaseController.workout();

				if (listaWorkouts != null) {
					try {
						for (Workout workout : listaWorkouts) {
							if (nivelFiltro == null || nivelFiltro.isEmpty()
									|| workout.getNivel().equalsIgnoreCase(nivelFiltro)) {
								modeloWorkouts.addRow(new Object[] { workout.getId(), workout.getNombre(),
										workout.getNivel(), workout.getVideo(), workout.getEjercicios().size() });
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else {
				try {
					modelo.ficheros.Backup.readBinaryFile(clientesBackup, workoutsBackup);
					for (Workout workout : workoutsBackup) {
						if (nivelFiltro == null || nivelFiltro.isEmpty()
								|| workout.getNivel().equalsIgnoreCase(nivelFiltro)) {
							modeloWorkouts.addRow(new Object[] { workout.getId(), workout.getNombre(),
									workout.getNivel(), workout.getVideo(), workout.getEjercicios().size() });
						}
					}

				} catch (FileException e) {
					e.printStackTrace();
					return;
				}
			}

		} catch (

		FireBaseException e) {
			e.printStackTrace();
		}

		tablaWorkouts.revalidate();
		tablaWorkouts.repaint();
	}

	/**
	 * Actualiza la tabla de detalles mostrando los ejercicios de un workout
	 * específico.
	 * 
	 * Obtiene el workout completo desde Firebase utilizando su ID y muestra todos
	 * sus ejercicios asociados en la tabla de detalles.
	 * 
	 * @param modeloDetallesWorkouts Modelo de la tabla donde se mostrarán los
	 *                               ejercicios
	 * @param idWorkout              ID del workout del cual se mostrarán los
	 *                               ejercicios
	 */
	public void actualizarTablaDetallesWorkout(DefaultTableModel modeloDetallesWorkouts, String idWorkout) {
		modeloDetallesWorkouts.setRowCount(0);
		List<Ejercicio> listaEjercicios = new ArrayList<>();
		Workout workoutSeleccionado = null;

		try {
			if (online) {
				workoutSeleccionado = firebaseController.obtenerWorkoutPorId(idWorkout);

				if (workoutSeleccionado != null) {
					listaEjercicios = workoutSeleccionado.getEjercicios();
				}

				for (Ejercicio ejercicio : listaEjercicios) {
					modeloDetallesWorkouts.addRow(new Object[] { ejercicio.getId(), ejercicio.getNombre(),
							ejercicio.getDescripcion(), ejercicio.getSeries().size() });
				}
			} else {
				try {
					modelo.ficheros.Backup.readBinaryFile(clientesBackup, workoutsBackup);
					for (Workout workout : workoutsBackup) {
						if (workout.getId().equals(idWorkout)) {
							listaEjercicios = workout.getEjercicios();

						}
					}

					for (Ejercicio ejercicio : listaEjercicios) {
						modeloDetallesWorkouts.addRow(new Object[] { ejercicio.getId(), ejercicio.getNombre(),
								ejercicio.getDescripcion(), ejercicio.getSeries().size() });
					}
				} catch (FileException e) {
					e.printStackTrace();
					return;
				}
			}
		} catch (FireBaseException e) {
			e.printStackTrace();

		}

		tablaDetallesWorkout.revalidate();
		tablaDetallesWorkout.repaint();
	}
}
