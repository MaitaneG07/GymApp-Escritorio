package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.Constants;

public class PerfilView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel tituloPerfil;
	private JButton btnModificar;
	private JButton btnVolver;
	private JLabel lblNombre;
	private JLabel lblApellidoUno;
	private JLabel lblApellidoDos;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JLabel lblFecNac;
	private JTextField textFieldEmail;
	private JTextField textFieldApellidoDos;
	private JPasswordField passwordField;
	private JTextField textFieldApellidoUno;
	private JTextField textFieldNombre;
	private JTextField textFieldFecNac;
	private JLabel lblFondoPerfil;
	@SuppressWarnings("unused")
	private String idCliente;
	@SuppressWarnings("unused")
	private String nivel;
	
	public void setIdCliente(String idCliente, String nivel) {
		this.idCliente = idCliente;
		this.nivel = nivel;
		System.out.println("🛠️Seteando ID Cliente en PanelViajesEventos: " + idCliente);
		System.out.println("🛠️Seteando Nivel Cliente en PanelViajesEventos: " + nivel);
	}

	/**
	 * Create the frame.
	 */
	public PerfilView(String idCliente, String nivel) {
		
		this.idCliente = idCliente;
		this.nivel = nivel;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblFondoPerfil = new JLabel();
		
		//LOGO MAITANE
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_OSCURO_CASA);
		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		
		Image imagenOriginal = originalIcon.getImage();

		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		lblFondoPerfil.setIcon(iconoEscalado);
		lblFondoPerfil.setBounds(0, 0, 873, 623);
		contentPane.add(lblFondoPerfil);
		
		tituloPerfil = new JLabel(Constants.PERFIL_LABEL);
		tituloPerfil.setOpaque(true);
		tituloPerfil.setForeground(Color.WHITE);
		tituloPerfil.setBackground(new Color(0, 0, 0, 0));
		tituloPerfil.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		tituloPerfil.setBounds(218, 23, 432, 79);
		lblFondoPerfil.add(tituloPerfil);
		
		btnModificar = new JButton(Constants.MODIFICAR_BOTON);
		btnModificar.setOpaque(true);
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnModificar.setFocusPainted(false);
		btnModificar.setContentAreaFilled(false);
		btnModificar.setBorderPainted(false);
		btnModificar.setBackground(new Color(255, 255, 255, 150));
		btnModificar.setBounds(237, 534, 159, 41);
		lblFondoPerfil.add(btnModificar);
		
		btnVolver = new JButton(Constants.VOLVER_BOTON);
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WorkoutView pantallaWorkout = new WorkoutView(idCliente, nivel);
				pantallaWorkout.setVisible(true);
				dispose();
			}
		});
		
		btnVolver.setOpaque(true);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setBackground(new Color(255, 255, 255, 150));
		btnVolver.setBounds(480, 534, 159, 41);
		lblFondoPerfil.add(btnVolver);
		
		textFieldNombre = new JTextField();
		lblFondoPerfil.add(textFieldNombre);
		textFieldNombre.setBounds(439, 136, 279, 30);
		textFieldNombre.setColumns(10);
		
		textFieldApellidoUno = new JTextField();
		lblFondoPerfil.add(textFieldApellidoUno);
		textFieldApellidoUno.setColumns(10);
		textFieldApellidoUno.setBounds(439, 191, 279, 30);
		
		passwordField = new JPasswordField();
		lblFondoPerfil.add(passwordField);
		passwordField.setBounds(441, 457, 279, 30);
		
		textFieldFecNac = new JTextField();
		lblFondoPerfil.add(textFieldFecNac);
		textFieldFecNac.setColumns(10);
		textFieldFecNac.setBounds(442, 368, 279, 30);
		
		lblNombre = new JLabel(Constants.NOMBRE_LABEL);
		lblFondoPerfil.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblNombre.setBounds(224, 136, 148, 29);
		lblNombre.setOpaque(true);
		lblNombre.setBackground(new Color(181, 179, 179, 150));
		
		lblApellidoUno = new JLabel(Constants.APELLIDO_LABEL);
		lblFondoPerfil.add(lblApellidoUno);
		lblApellidoUno.setOpaque(true);
		lblApellidoUno.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoUno.setForeground(Color.WHITE);
		lblApellidoUno.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoUno.setBackground(new Color(181, 179, 179, 150));
		lblApellidoUno.setBounds(224, 191, 148, 29);
		
		lblApellidoDos = new JLabel(Constants.APELLIDO_DOS_LABEL);
		lblFondoPerfil.add(lblApellidoDos);
		lblApellidoDos.setOpaque(true);
		lblApellidoDos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoDos.setForeground(Color.WHITE);
		lblApellidoDos.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoDos.setBackground(new Color(181, 179, 179, 150));
		lblApellidoDos.setBounds(224, 245, 148, 29);
		
		lblEmail = new JLabel(Constants.EMAIL_LABEL);
		lblFondoPerfil.add(lblEmail);
		lblEmail.setOpaque(true);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblEmail.setBackground(new Color(181, 179, 179, 150));
		lblEmail.setBounds(225, 298, 148, 29);
		
		lblFecNac = new JLabel(Constants.FECHA_NACIMIENTO_LABEL);
		lblFondoPerfil.add(lblFecNac);
		lblFecNac.setOpaque(true);
		lblFecNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecNac.setForeground(Color.WHITE);
		lblFecNac.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblFecNac.setBackground(new Color(181, 179, 179, 150));
		lblFecNac.setBounds(223, 368, 184, 29);
		
		textFieldEmail = new JTextField();
		lblFondoPerfil.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(442, 298, 276, 30);
		
		textFieldApellidoDos = new JTextField();
		lblFondoPerfil.add(textFieldApellidoDos);
		textFieldApellidoDos.setColumns(10);
		textFieldApellidoDos.setBounds(441, 243, 277, 30);
		
		lblPassword = new JLabel(Constants.PASSWORD_LABEL);
		lblFondoPerfil.add(lblPassword);
		lblPassword.setOpaque(true);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblPassword.setBackground(new Color(181, 179, 179, 150));
		lblPassword.setBounds(222, 457, 148, 29);
	}

}
