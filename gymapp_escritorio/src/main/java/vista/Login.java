package vista;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Constants;
import vista.pantallas.Registro;
import vista.pantallas.Workout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelFondo;
	private JLabel labelUsuario;
	private JLabel labelPassword;
	private JTextField textFieldUsuario;
	private JTextField textFieldPassword;
	private JButton btnIniciarSesion;
	private JButton btnRegistro;
	private JLabel tituloLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		labelFondo = new JLabel();
		//ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CASA);
		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CLASE);

		Image imagenOriginal = originalIcon.getImage();
		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		labelFondo.setIcon(iconoEscalado);
		labelFondo.setBounds(0, 0, 885, 658);
		labelFondo.setLayout(null);
		contentPane.add(labelFondo);
		
		labelUsuario = new JLabel(Constants.USUARIO_LABEL);
		labelFondo.add(labelUsuario);
		labelUsuario.setForeground(new Color(0, 0, 0));
		labelUsuario.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		labelUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		labelUsuario.setBounds(322, 198, 223, 49);
		labelUsuario.setOpaque(true);
		labelUsuario.setBackground(new Color(181, 179, 179, 150));
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUsuario.setForeground(new Color(0, 0, 0));
		textFieldUsuario.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 11));
		textFieldUsuario.setBounds(321, 261, 225, 30);
		labelFondo.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		btnIniciarSesion = new JButton("INICIAR SESIÓN");
		btnIniciarSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Workout panelWorkout = new Workout();
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnIniciarSesion.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnIniciarSesion.setForeground(new Color(0, 0, 0));
		btnIniciarSesion.setBounds(282, 516, 159, 41);
		btnIniciarSesion.setOpaque(true);
		btnIniciarSesion.setBackground(new Color(255, 255, 255, 150));
		btnIniciarSesion.setFocusPainted(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setOpaque(false);
		labelFondo.add(btnIniciarSesion);

		btnRegistro = new JButton("REGISTRARME");
		btnRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Registro panelRegistro = new Registro();
				panelRegistro.setVisible(true);
				dispose();
			}
		});
		btnRegistro.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnRegistro.setForeground(new Color(0, 0, 0));
		btnRegistro.setBounds(451, 516, 159, 41);
		btnRegistro.setOpaque(true);
		btnRegistro.setBackground(new Color(255, 255, 255, 150));
		btnRegistro.setFocusPainted(false);
		btnRegistro.setBorderPainted(false);
		btnRegistro.setContentAreaFilled(false);
		btnRegistro.setOpaque(false);
		labelFondo.add(btnRegistro);
		
		tituloLogin = new JLabel("LOGIN");
		tituloLogin.setOpaque(true);
		tituloLogin.setHorizontalAlignment(SwingConstants.CENTER);
		tituloLogin.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloLogin.setBackground(new Color(255, 255, 255, 150));
		tituloLogin.setBounds(224, 26, 432, 79);
		labelFondo.add(tituloLogin);
		
		labelPassword = new JLabel(Constants.PASSWORD_LABEL);
		labelFondo.add(labelPassword);
		labelPassword.setForeground(new Color(0, 0, 0));
		labelPassword.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		labelPassword.setHorizontalAlignment(SwingConstants.CENTER);
		labelPassword.setBounds(322, 308, 223, 49);
		labelPassword.setOpaque(true);
		labelPassword.setBackground(new Color(181, 179, 179, 150));
		
		textFieldPassword = new JTextField();
		labelFondo.add(textFieldPassword);
		textFieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPassword.setForeground(new Color(0, 0, 0));
		textFieldPassword.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 11));
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(321, 380, 225, 30);

	}
}
