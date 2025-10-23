package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.FirebaseController;
import modelo.entity.Ejercicio;
import modelo.entity.Workout;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;
import utils.Constants;
import vista.Login;


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
	private Scanner scanner;
	private FirebaseGestor firebaseGestor;
	private JMenuItem menuNivel;
	private JMenuItem menuPrincipiante;
	private JMenuItem menuIntermedio;
	private JMenuItem menuAvanzado;
	private JButton btnNivel;
	
	
	public WorkoutView() {
		
		scanner = new Scanner(System.in);
		firebaseController = new FirebaseController();
		try {
			firebaseGestor = new FirebaseGestor();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		
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
		//LOGO MAITANE
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
			
			PerfilView pantallaPerfil = new PerfilView();
			pantallaPerfil.setVisible(true);
			dispose();
		});

		menuHistorico = new JMenuItem(Constants.CONSULTAR_HISTORICO_MENU);
		popupMenuPerfil.add(menuHistorico);
		menuHistorico.addActionListener(e -> {

			HistoricoView pantallaHistorico = new HistoricoView();
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

		menuIntermedio = new JMenuItem(Constants.NIVEL_INTERMEDIO_MENU);
		popupMenuNivel.add(menuIntermedio);

		menuAvanzado = new JMenuItem(Constants.NIVEL_AVANZADO_MENU);
		popupMenuNivel.add(menuAvanzado);
		
		btnNivel.addActionListener(e -> {
			popupMenuNivel.show(btnNivel, 0, btnNivel.getHeight());

		});

		scrollPaneWorkouts = new JScrollPane();
		scrollPaneWorkouts.setBounds(162, 131, 574, 149);
		contentPane.add(scrollPaneWorkouts);

		modeloWorkouts = new DefaultTableModel(){
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
		

		// Agregar MouseListener para detectar el doble clic y obtener el id_viaje
		tablaWorkouts.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2 && tablaWorkouts.getSelectedRow() != -1) {
		            int selectedRow = tablaWorkouts.getSelectedRow();
		            String idWorkout = (String) tablaWorkouts.getValueAt(selectedRow, 0);

		            System.out.println("ID Workout seleccionado: " + idWorkout);
		            actualizarTablaDetallesWorkout(modeloDetallesWorkouts, idWorkout);
		        }
		    }
		});
		scrollPaneWorkouts.setViewportView(tablaWorkouts);

		scrollPaneDetallesWorkout = new JScrollPane();
		scrollPaneDetallesWorkout.setBounds(162, 351, 574, 149);
		contentPane.add(scrollPaneDetallesWorkout);

		modeloDetallesWorkouts = new DefaultTableModel();
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_ID_EJERCICIO);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_NOMBRE_EJERCICIO);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_DESCRIPCION);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_SERIES);

		tablaDetallesWorkout = new JTable(modeloDetallesWorkouts);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setMinWidth(0);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaDetallesWorkout.getColumnModel().getColumn(0).setWidth(0);
		scrollPaneDetallesWorkout.setViewportView(tablaDetallesWorkout);
		
		
		actualizarTablaWorkouts(modeloWorkouts);

		
		btnSeleccionar = new JButton(Constants.SELECCIONAR_BOTON);
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EjercicioView pantallaEjercicio = new EjercicioView();
				pantallaEjercicio.setVisible(true);
				dispose();
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
		
	}
	
	public void actualizarTablaWorkouts(DefaultTableModel modeloWorkouts) {
		modeloWorkouts.setRowCount(0);
		List<Workout> listaWorkouts = null;
		try {
			listaWorkouts = firebaseController.workout();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		
		if (listaWorkouts == null || listaWorkouts.isEmpty()) {
			listaWorkouts = new ArrayList<>();
		}
		
		for (Workout workout : listaWorkouts) {
			modeloWorkouts.addRow(new Object[] {
					workout.getId(), workout.getNombre(), workout.getNivel(), 
					workout.getVideo(), workout.getEjercicios().size()
			});
		}
		
		tablaWorkouts.revalidate();
		tablaWorkouts.repaint();
	}
	
	public void actualizarTablaDetallesWorkout(DefaultTableModel modeloDetallesWorkouts, String idWorkout) {
	    modeloDetallesWorkouts.setRowCount(0); 

	    List<Ejercicio> listaEjercicios = new ArrayList<>();

	    Workout workoutSeleccionado = null;
		try {
			workoutSeleccionado = firebaseController.obtenerWorkoutPorId(idWorkout);
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		if (workoutSeleccionado != null) {
		    listaEjercicios = workoutSeleccionado.getEjercicios();
		}

	    for (Ejercicio ejercicio : listaEjercicios) {
	        modeloDetallesWorkouts.addRow(new Object[] {
	            ejercicio.getId(),
	            ejercicio.getNombre(),
	            ejercicio.getDescripcion(),
	            ejercicio.getSeries().size()
	        });
	    }
	}

}
