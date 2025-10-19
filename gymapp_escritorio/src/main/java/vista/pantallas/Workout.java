package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

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

import utils.Constants;
import vista.Login;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Workout extends JFrame {

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

	/**
	 * Create the frame.
	 */
	public Workout() {
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
		 ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA));
//		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
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
			
			Perfil pantallaPerfil = new Perfil();
			pantallaPerfil.setVisible(true);
			dispose();
		});

		menuHistorico = new JMenuItem(Constants.CONSULTAR_HISTORICO_MENU);
		popupMenuPerfil.add(menuHistorico);
		menuHistorico.addActionListener(e -> {

			Historico pantallaHistorico = new Historico();
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

		scrollPaneWorkouts = new JScrollPane();
		scrollPaneWorkouts.setBounds(162, 131, 574, 149);
		contentPane.add(scrollPaneWorkouts);

		modeloWorkouts = new DefaultTableModel();
		modeloWorkouts.addColumn(Constants.COLUMNA_NOMBRE_WORKOUT);
		modeloWorkouts.addColumn(Constants.COLUMNA_EJERCICIOS);
		modeloWorkouts.addColumn(Constants.COLUMNA_NIVEL_WORKOUT);
		modeloWorkouts.addColumn(Constants.COLUMNA_VIDEO);

		tablaWorkouts = new JTable(modeloWorkouts);
		scrollPaneWorkouts.setViewportView(tablaWorkouts);

		scrollPaneDetallesWorkout = new JScrollPane();
		scrollPaneDetallesWorkout.setBounds(162, 351, 574, 149);
		contentPane.add(scrollPaneDetallesWorkout);

		modeloDetallesWorkouts = new DefaultTableModel();
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_NOMBRE_EJERCICIO);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_DESCRIPCION);
		modeloDetallesWorkouts.addColumn(Constants.COLUMNA_SERIES);

		tablaDetallesWorkout = new JTable(modeloDetallesWorkouts);
		scrollPaneDetallesWorkout.setViewportView(tablaDetallesWorkout);

		btnSeleccionar = new JButton(Constants.SELECCIONAR_BOTON);
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Ejercicio pantallaEjercicio = new Ejercicio();
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

		popupMenuNivel = new JPopupMenu();
		// Crear los MenuItem cargandolos de la bbdd
	}
}
