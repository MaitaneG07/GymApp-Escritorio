package vista;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.FirebaseController;
import modelo.entity.Cliente;
import modelo.entity.Serie;
import modelo.entity.Workout;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPasswordField;

/**
 * Vista de inicio de sesión de la aplicación.
 * 
 * Esta clase proporciona la interfaz de login permitiendo a los usuarios
 * autenticarse mediante email y contraseña. Soporta modo online (Firebase) y
 * modo offline (backup local). Si el login es exitoso, guarda un backup local
 * del cliente para permitir acceso sin conexión.
 */
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
	@SuppressWarnings("unused")
	private Backup backup;

	/**
	 * Punto de entrada de la aplicación.
	 * 
	 * @param args Argumentos de línea de comandos (no utilizados)
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
	 * Constructor de la vista de login.
	 * 
	 * Inicializa todos los componentes visuales, configura los listeners y
	 * establece las conexiones con Firebase y el sistema de backup.
	 */
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

			/**
			 * Realiza el proceso de autenticación del usuario.
			 * 
			 * Intenta autenticar al usuario usando Firebase si hay conexión a internet. Si
			 * no hay conexión, intenta usar el backup local guardado previamente. Si el
			 * login es exitoso, guarda o actualiza el backup local y redirige a la vista de
			 * Workouts.
			 * 
			 * Valida que los campos no estén vacíos antes de proceder.
			 */
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
				Cliente clienteAutenticado = null;
				List<Cliente> clientes = null;
				List<Workout> workouts = null;
				@SuppressWarnings("unused")
				List<Serie> series = null;

				try {
					if (online) {

						clientes = firebaseController.getClientes();
						workouts = firebaseController.workout();
						
						if (clientes != null && workouts != null) {
							try {
								modelo.ficheros.Backup.writeBinaryFile(clientes, workouts);
							} catch (FileException e) {
								e.printStackTrace();
							}

							clienteAutenticado = buscarCliente(clientes, email, password);
						}

						if (clienteAutenticado == null) {
							JOptionPane.showMessageDialog(Login.this, Constants.USUARIO_CONTRASEÑA_ERROR,
									Constants.ERROR_LOGIN, JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						List<Cliente> clientesBackup = new ArrayList<>();
						List<Workout> workoutsBackup = new ArrayList<>();
						try {
							modelo.ficheros.Backup.readBinaryFile(clientesBackup, workoutsBackup);
							clienteAutenticado = buscarCliente(clientesBackup, email, password);
							if (clienteAutenticado == null) {
								JOptionPane.showMessageDialog(Login.this,
										"No se pudo iniciar sesión sin conexión (usuario/contraseña no coinciden).",
										"Error offline", JOptionPane.WARNING_MESSAGE);
								return;
							} else {
								System.out.println("Login offline con backup local");
							}
						} catch (FileException e) {
							e.printStackTrace();
							return;
						}
					}

					if (clienteAutenticado != null) {
						System.out.println("Login exitoso: " + clienteAutenticado.getNombre());
						WorkoutView pantallaWorkout = new WorkoutView(clienteAutenticado.getId(),
								clienteAutenticado.getNivel());
						
						pantallaWorkout.setIdCliente(clienteAutenticado.getId(), clienteAutenticado.getNivel());
						pantallaWorkout.setVisible(true);
						dispose();
					}

				} catch (FireBaseException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(Login.this, "Error de conexión con Firebase.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private Cliente buscarCliente(List<Cliente> clientes, String email, String password) {
				for (Cliente cliente : clientes) {
					if (cliente.getEmail().equalsIgnoreCase(email) && cliente.getPassword().equals(password)) {
						return cliente;
					}
				}
				return null;
			}

			/**
			 * Limpia los campos de email y contraseña.
			 */
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
