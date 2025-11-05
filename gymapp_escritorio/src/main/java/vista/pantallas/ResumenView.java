package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.Constants;

public class ResumenView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel tituloResumen;
	private JScrollPane scrollPaneResumen;
	private DefaultTableModel modeloResumen;
	private JTable tablaResumen;
	private JButton btnConfirmar;
	private JButton btnPerfil;
	private JLabel lblMensaje;	
	private String idCliente;
	private String nivel;
	
	private static final String[] MENSAJES = {
	        "¡Excelente trabajo!",
	        "¡Bien hecho!",
	        "¡Sigue así!",
	        "¡Gran esfuerzo!",
	        "¡Lo estás haciendo genial!",
	        "¡Muy bien!",
	        "¡Fantástico!",
	        "¡Continúa así!",
	        "¡Eres increíble!",
	        "¡No te rindas!"
	    };
	
	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("🛠️Seteando ID Cliente en PanelViajesEventos: " + idCliente);
		System.out.println("🛠️Seteando Nivel Cliente en PanelViajesEventos: " + nivel);
	}

	/**
	 * Create the frame.
	 */
	public ResumenView(String idCliente, String nivel) {
		
		this.idCliente = idCliente;
		this.nivel = nivel;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPerfil = new JButton();
		
		//LOGO MAITANE
//		 ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA));
		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);
		
		tituloResumen = new JLabel(Constants.RESUMEN_LABEL);
		tituloResumen.setOpaque(true);
		tituloResumen.setBackground(new Color(0, 0, 0, 0));
		tituloResumen.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloResumen.setHorizontalAlignment(SwingConstants.CENTER);
		tituloResumen.setBounds(218, 23, 432, 79);
		contentPane.add(tituloResumen);
		
		scrollPaneResumen = new JScrollPane();
		scrollPaneResumen.setBounds(162, 170, 574, 149);
		contentPane.add(scrollPaneResumen);

		modeloResumen = new DefaultTableModel();
		modeloResumen.addColumn(Constants.COLUMNA_NOMBRE_EJERCICIO);
		modeloResumen.addColumn(Constants.TIEMPO_TOTAL_WORKOUT);
		modeloResumen.addColumn(Constants.EJERCICIOS_COMPLETADOS);

		tablaResumen = new JTable(modeloResumen);
		scrollPaneResumen.setViewportView(tablaResumen);
		
		btnConfirmar = new JButton(Constants.CONFIRMAR_BOTON);
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WorkoutView pantallaWorkout = new WorkoutView(idCliente, nivel);
				pantallaWorkout.setVisible(true);
				dispose();
			}
		});
		btnConfirmar.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnConfirmar.setBounds(354, 554, 155, 40);
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.setContentAreaFilled(false);
		btnConfirmar.setBorderPainted(false);
		contentPane.add(btnConfirmar);
		
		Random random = new Random();
        String mensajeAleatorio = MENSAJES[random.nextInt(MENSAJES.length)];
		
		lblMensaje = new JLabel(mensajeAleatorio);
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		lblMensaje.setBounds(199, 368, 503, 107);
		contentPane.add(lblMensaje);
		
		
		
	}
	
}
