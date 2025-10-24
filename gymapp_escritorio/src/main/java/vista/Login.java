package vista;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.FirebaseController;
import modelo.entity.Cliente;
import modelo.exceptions.FileException;
import modelo.exceptions.FireBaseException;
import modelo.ficheros.Backup;
import utils.Constants;
import vista.pantallas.RegistroView;
import vista.pantallas.WorkoutView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelFondo;
	private JLabel labelUsuario;
	private JLabel labelPassword;
	private JTextField textFieldUsuario;
	private JButton btnIniciarSesion;
	private JButton btnRegistro;
	private JLabel tituloLogin;
	private JPasswordField passwordField;
	private FirebaseController firebaseController;
	private Backup backup;

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

	public Login() {

		firebaseController = new FirebaseController();
		backup = new Backup();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelFondo = new JLabel();

		// LOGO MAITANE
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CASA);
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

		btnIniciarSesion = new JButton(Constants.INICAR_SESION_BOTON);
		btnIniciarSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				realizarLogin();
				limpiarCampos();
			}

			private void realizarLogin() {
				String email = textFieldUsuario.getText().trim();
				String password = new String(passwordField.getPassword());

				System.out.println(email + "\n" + password);

				if (email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(Login.this, Constants.SIN_USUARIO_CONTRASEÑA, Constants.CAMPOS_VACIOS,
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				boolean online = utils.Network.isInternetAvailable();
				Cliente cliente = null;

				try {
					if (online) {

						cliente = firebaseController.login(email, password);

						if (cliente != null) {
							try {
								modelo.ficheros.Backup.writeBinaryFile(cliente);
							} catch (FileException e) {
								e.printStackTrace();
							}
						}
					} else {
						Cliente backupCliente = null;
						try {
							backupCliente = modelo.ficheros.Backup.readBinaryFile();
							System.out.println("Nombre: " + backupCliente.getNombre());
							System.out.println("Email: " + backupCliente.getEmail());
							System.out.println("Nivel: " + backupCliente.getNivel());
						} catch (FileException e) {
							e.printStackTrace();
						}

						if (backupCliente != null && backupCliente.getEmail().equalsIgnoreCase(email)
								&& backupCliente.getPassword().equals(password)) {
							cliente = backupCliente;
							System.out.println("Login offline con backup local");
						} else {
							JOptionPane.showMessageDialog(Login.this,
									"No se pudo iniciar sesión sin conexión (usuario/contraseña no coinciden).",
									"Error offline", JOptionPane.WARNING_MESSAGE);
						}
					}

					System.out.println(cliente);

					if (cliente != null) {
						WorkoutView pantallaWorkout = new WorkoutView(password, password);
						pantallaWorkout.setIdCliente(cliente.getId(), cliente.getNivel());
						pantallaWorkout.setVisible(true);
						dispose();

					} else if (online) {
						JOptionPane.showMessageDialog(Login.this, Constants.USUARIO_CONTRASEÑA_ERROR,
								Constants.ERROR_LOGIN, JOptionPane.ERROR_MESSAGE);
					}

				} catch (FireBaseException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(Login.this, "Error de conexión con Firebase.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void limpiarCampos() {

				textFieldUsuario.setText("");
				passwordField.setText("");
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

		btnRegistro = new JButton(Constants.REGISTRARME_BOTON);
		btnRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegistroView panelRegistro = new RegistroView();
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

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setForeground(new Color(0, 0, 0));
		passwordField.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 11));
		passwordField.setColumns(10);
		passwordField.setBounds(321, 380, 225, 30);
		labelFondo.add(passwordField);

	}
}
