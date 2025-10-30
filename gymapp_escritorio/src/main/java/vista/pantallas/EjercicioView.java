package vista.pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.Constants;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import controlador.CronometroLogica;
import modelo.entity.Ejercicio;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;
import javax.swing.JTextArea;

public class EjercicioView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCronometro;
	private JLabel lblCronometro;
	private JLabel lblNombreEjercicio;
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

	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("🛠️Seteando ID Cliente en PanelViajesEventos: " + idCliente);
		System.out.println("🛠️Seteando Nivel Cliente en PanelViajesEventos: " + nivel);
	}

	/**
	 * Create the frame.
	 * @throws FireBaseException 
	 */
	public EjercicioView(String idCliente, String nivel, String idWorkoutSeleccionado) throws FireBaseException {

		this.idCliente = idCliente;
		this.nivel = nivel;
		this.idWorkoutSeleccionado = idWorkoutSeleccionado;
		firebaseGestor = new FirebaseGestor();
		List<Ejercicio> ejerciciosWorkoutSeleccionado = firebaseGestor.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);
		//Probar los datos que llegan:
//		for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
//			System.out.println("---------------------------");
//			System.out.println("Datos del ejercicio obtenido:");
//		    System.out.println("ID: " + ejercicio.getId());
//		    System.out.println("Nombre: " + ejercicio.getNombre());
//		    System.out.println("Descripción: " + ejercicio.getDescripcion());
//		    System.out.println("Completado: " + ejercicio.isCompletado());
//		    System.out.println("Número de series: " + ejercicio.getSeries().size());
//		    System.out.println("---------------------------");
//		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPerfil = new JButton();

		// LOGO MAITANE
		ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CLASE_Ak));
//		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);

		lblCronometro = new JLabel("00:00:00", SwingConstants.CENTER);
		lblCronometro.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblCronometro.setBounds(20, 91, 265, 74);
		contentPane.add(lblCronometro);

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

		cronometro = new CronometroLogica(() -> lblCronometro.setText(cronometro.obtenerTiempoFormateado()));

		btnCronometro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!cronometro.estaCorriendo()) {
					cronometro.iniciar();
					btnCronometro.setText(Constants.PAUSAR_BOTON);
					btnCronometro.setBackground(new Color(139, 0, 0));
				} else {
					cronometro.pausarReanudar();
					btnCronometro.setText(Constants.INICIAR_BOTON);
					btnCronometro.setBackground(new Color(0, 128, 0));
				}
			}
		});
		btnCronometro.setBounds(360, 520, 133, 58);
		contentPane.add(btnCronometro);

		lblNombreEjercicio = new JLabel(obtenerDatoEjercicioSeleccionado("nombre"), SwingConstants.CENTER);
		lblNombreEjercicio.setFont(new Font("Arial", Font.BOLD, 30));
		lblNombreEjercicio.setBounds(243, 11, 368, 55);
		contentPane.add(lblNombreEjercicio);

		lblFotoEjercicio = new JLabel("");
		lblFotoEjercicio.setIcon(new ImageIcon(
				"C:\\Users\\in2dm3-v\\Documents\\Reto 1\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\remo.jpg"));
		lblFotoEjercicio.setBounds(695, 110, 150, 159);
		contentPane.add(lblFotoEjercicio);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(332, 299, 513, 183);
		contentPane.add(scrollPane);

		tablaDetallesSeries = new DefaultTableModel();
		tablaDetallesSeries.addColumn(Constants.COLUMNA_SERIES);
		tablaDetallesSeries.addColumn(Constants.COLUMNA_TIEMPO);
		tablaDetallesSeries.addColumn(Constants.COLUMNA_DESCANSO);

		tableSeries = new JTable(tablaDetallesSeries);
		scrollPane.setViewportView(tableSeries);

		JLabel lblEjercicio = new JLabel("Tiempo ejercicio:");
		lblEjercicio.setBounds(44, 206, 107, 14);
		contentPane.add(lblEjercicio);

		JLabel lblDescanso = new JLabel("Descanso ");
		lblDescanso.setBounds(44, 362, 60, 14);
		contentPane.add(lblDescanso);

		JLabel lblcantidadDescanso = new JLabel("New label");
		lblcantidadDescanso.setBounds(119, 362, 68, 14);
		contentPane.add(lblcantidadDescanso);

		JLabel lblCronometroEjercicio = new JLabel("New label");
		lblCronometroEjercicio.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCronometroEjercicio.setBounds(44, 243, 210, 65);
		contentPane.add(lblCronometroEjercicio);

		JLabel lblCuentaDescanso = new JLabel("New label");
		lblCuentaDescanso.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCuentaDescanso.setBounds(48, 413, 206, 60);
		contentPane.add(lblCuentaDescanso);
		
		JTextArea textADescripcion = new JTextArea();
		textADescripcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		textADescripcion.setText(obtenerDatoEjercicioSeleccionado("Descripcion"));
		textADescripcion.setBounds(332, 110, 347, 159);
		contentPane.add(textADescripcion);
		//estas dos siguientes lineas son para que no se corte el texto si la frase es larga
		textADescripcion.setLineWrap(true);
		textADescripcion.setWrapStyleWord(true);
		//un scroll
		JScrollPane scrollDescripcion = new JScrollPane(textADescripcion);
		scrollDescripcion.setBounds(332, 110, 347, 159);
		contentPane.add(scrollDescripcion);

	}
	
	private String obtenerDatoEjercicioSeleccionado (String dato) throws FireBaseException {
		String ret = null;
		List<Ejercicio> ejerciciosWorkoutSeleccionado = firebaseGestor.obtenerEjerciciosPorWorkout(idWorkoutSeleccionado);
		for (Ejercicio ejercicio : ejerciciosWorkoutSeleccionado) {
			
			if (dato.equalsIgnoreCase("id")) {
				ret = ejercicio.getId();
			} else if (dato.equalsIgnoreCase("nombre")) {
				ret = ejercicio.getNombre();
			} else if (dato.equalsIgnoreCase("descripcion")) {
				ret = ejercicio.getDescripcion();
			} else if (dato.equalsIgnoreCase("completado")) {
				
			} else if (dato.equalsIgnoreCase("numero de series")) {
	
			}
		}
		return ret;
	}
}
