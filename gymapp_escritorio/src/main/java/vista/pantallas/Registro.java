package vista.pantallas;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vista.Login;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFondoRegistro;
	private JLabel lblNombre;
	private JLabel lblApellidoUno;
	private JLabel lblApellidoDos;
	private JLabel lblEmail;
	private JLabel lblFecNac;
	private JLabel lblContraseña;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidoUno;
	private JTextField textFieldApellidoDos;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JTextField textFieldFecNac;
	private JLabel tituloRegistro;
	private JButton btnRegistro;
	private JButton btnVolver;


	/**
	 * Create the frame.
	 */
	public Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblFondoRegistro = new JLabel();
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\Usuario\\Desktop\\GymApp\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\fondo.jpg");
		
		Image imagenOriginal = originalIcon.getImage();

		// Escalar imagen al tamaño del label manteniendo calidad
		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		lblFondoRegistro.setIcon(iconoEscalado);
		lblFondoRegistro.setBounds(0, 0, 873, 623);
		contentPane.add(lblFondoRegistro);
		
		lblNombre = new JLabel("NOMBRE");
		lblFondoRegistro.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
		lblNombre.setBounds(44, 177, 148, 29);
		lblNombre.setOpaque(true);
		lblNombre.setBackground(new Color(255, 255, 255, 150));
		
		lblApellidoUno = new JLabel("APELLIDO");
		lblFondoRegistro.add(lblApellidoUno);
		lblApellidoUno.setOpaque(true);
		lblApellidoUno.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoUno.setForeground(new Color(0, 0, 0));
		lblApellidoUno.setFont(new Font("Arial", Font.BOLD, 14));
		lblApellidoUno.setBackground(new Color(255, 255, 255, 150));
		lblApellidoUno.setBounds(44, 232, 148, 29);
		
		lblApellidoDos = new JLabel("APELLIDO");
		lblFondoRegistro.add(lblApellidoDos);
		lblApellidoDos.setOpaque(true);
		lblApellidoDos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoDos.setForeground(Color.BLACK);
		lblApellidoDos.setFont(new Font("Arial", Font.BOLD, 14));
		lblApellidoDos.setBackground(new Color(255, 255, 255, 150));
		lblApellidoDos.setBounds(44, 286, 148, 29);
		
		lblEmail = new JLabel("EMAIL");
		lblFondoRegistro.add(lblEmail);
		lblEmail.setOpaque(true);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
		lblEmail.setBackground(new Color(255, 255, 255, 150));
		lblEmail.setBounds(45, 339, 148, 29);
		
		lblContraseña = new JLabel("CONTRASEÑA");
		lblFondoRegistro.add(lblContraseña);
		lblContraseña.setOpaque(true);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setForeground(Color.BLACK);
		lblContraseña.setFont(new Font("Arial", Font.BOLD, 14));
		lblContraseña.setBackground(new Color(255, 255, 255, 150));
		lblContraseña.setBounds(46, 392, 148, 29);
		
		lblFecNac = new JLabel("FECHA DE NACIMIENTO");
		lblFondoRegistro.add(lblFecNac);
		lblFecNac.setOpaque(true);
		lblFecNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecNac.setForeground(Color.BLACK);
		lblFecNac.setFont(new Font("Arial", Font.BOLD, 14));
		lblFecNac.setBackground(new Color(255, 255, 255, 150));
		lblFecNac.setBounds(47, 446, 184, 29);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(218, 177, 177, 30);
		lblFondoRegistro.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellidoUno = new JTextField();
		textFieldApellidoUno.setColumns(10);
		textFieldApellidoUno.setBounds(218, 232, 177, 30);
		lblFondoRegistro.add(textFieldApellidoUno);
		
		textFieldApellidoDos = new JTextField();
		textFieldApellidoDos.setColumns(10);
		textFieldApellidoDos.setBounds(207, 285, 177, 30);
		lblFondoRegistro.add(textFieldApellidoDos);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(218, 339, 276, 30);
		lblFondoRegistro.add(textFieldEmail);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(218, 393, 177, 30);
		lblFondoRegistro.add(passwordField);
		
		textFieldFecNac = new JTextField();
		textFieldFecNac.setColumns(10);
		textFieldFecNac.setBounds(256, 446, 177, 30);
		lblFondoRegistro.add(textFieldFecNac);
		
		tituloRegistro = new JLabel("REGISTRO");
		tituloRegistro.setOpaque(true);
		tituloRegistro.setBackground(new Color(255, 255, 255, 150));
		tituloRegistro.setFont(new Font("Arial", Font.BOLD, 39));
		tituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		tituloRegistro.setBounds(218, 23, 432, 79);
		lblFondoRegistro.add(tituloRegistro);
		
		btnRegistro = new JButton("REGISTRARME");
		btnRegistro.setOpaque(true);
		btnRegistro.setForeground(new Color(255, 255, 255));
		btnRegistro.setFont(new Font("Arial", Font.BOLD, 13));
		btnRegistro.setFocusPainted(false);
		btnRegistro.setContentAreaFilled(false);
		btnRegistro.setBorderPainted(false);
		btnRegistro.setBackground(new Color(255, 255, 255, 150));
		btnRegistro.setBounds(237, 534, 159, 41);
		lblFondoRegistro.add(btnRegistro);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login pantallaLogin = new Login();
				pantallaLogin.setVisible(true);
				dispose();
			}
		});
		btnVolver.setOpaque(true);
		btnVolver.setForeground(new Color(255, 255, 255));
		btnVolver.setFont(new Font("Arial", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setBackground(new Color(255, 255, 255, 150));
		btnVolver.setBounds(480, 534, 159, 41);
		lblFondoRegistro.add(btnVolver);

	}
}
