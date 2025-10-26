package vista.pantallas;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.FirebaseController;
import modelo.entity.Cliente;
import modelo.exceptions.FireBaseException;
import modelo.gestores.FirebaseGestor;
import utils.Constants;
import vista.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Vista de registro de nuevos clientes.
 * 
 * Esta clase proporciona una interfaz gráfica para que nuevos usuarios puedan
 * registrarse en el sistema. Incluye validación de campos obligatorios y
 * verificación de emails duplicados. Los nuevos usuarios se crean automáticamente
 * con nivel "Principiante".
 */
public class RegistroView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFondoRegistro;
	private JLabel lblNombre;
	private JLabel lblApellidoUno;
	private JLabel lblApellidoDos;
	private JLabel lblEmail;
	private JLabel lblFecNac;
	private JLabel lblPassword;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidoUno;
	private JTextField textFieldApellidoDos;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JTextField textFieldFecNac;
	private JLabel tituloRegistro;
	private JButton btnRegistro;
	private JButton btnVolver;
	private FirebaseGestor firebaseGestor;
	private FirebaseController firebaseController;

	/**
	 * Constructor de la vista de registro.
	 * 
	 * Inicializa todos los componentes visuales, configura los listeners
	 * y establece las conexiones con Firebase para el registro de usuarios.
	 */
	public RegistroView() {
		try {
			firebaseGestor = new FirebaseGestor();
			firebaseController = new FirebaseController();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblFondoRegistro = new JLabel();

		// LOGO MAITANE
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CASA);
		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CLASE);

		Image imagenOriginal = originalIcon.getImage();

		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		lblFondoRegistro.setIcon(iconoEscalado);
		lblFondoRegistro.setBounds(0, 0, 873, 623);
		contentPane.add(lblFondoRegistro);

		tituloRegistro = new JLabel(Constants.REGISTRO_LABEL);
		tituloRegistro.setOpaque(true);
		tituloRegistro.setBackground(new Color(255, 255, 255, 150));
		tituloRegistro.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		tituloRegistro.setBounds(218, 23, 432, 79);
		lblFondoRegistro.add(tituloRegistro);

		btnRegistro = new JButton(Constants.REGISTRARME_LABEL);
		btnRegistro.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				realizarRegistro();
				limpiarCampos();

			}

			/**
			 * Realiza el proceso de registro de un nuevo cliente.
			 * 
			 * Valida que todos los campos obligatorios estén completos, verifica
			 * que el email no esté duplicado en la base de datos, y si todo es
			 * correcto, crea el nuevo cliente con nivel "Principiante" por defecto.
			 * 
			 * Muestra mensajes de error o éxito según corresponda.
			 */
			private void realizarRegistro() {

				String nombre = textFieldNombre.getText().trim();
				String apellido1 = textFieldApellidoUno.getText().trim();
				String apellido2 = textFieldApellidoDos.getText().trim();
				String fechaNacimiento = textFieldFecNac.getText().trim();
				String email = textFieldEmail.getText().trim();
				String password = new String(passwordField.getPassword()).trim();

				if (nombre.isEmpty() || apellido1.isEmpty() || fechaNacimiento.isEmpty() || email.isEmpty()
						|| password.isEmpty()) {

					JOptionPane.showMessageDialog(RegistroView.this, Constants.COMPLETAR_CAMPOS,
							Constants.CAMPOS_VACIOS, JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					
					if (firebaseController.existeEmail(email)) {
			            JOptionPane.showMessageDialog(RegistroView.this, 
			                Constants.EMAIL_EXISTENTE,
			                Constants.EMAIL_DUPLICADO, 
			                JOptionPane.WARNING_MESSAGE);
			            return; 
			        }
					
					String siguienteId = firebaseController.obtenerSiguienteId();

					Cliente cliente = new Cliente();
					cliente.setId(String.valueOf(siguienteId));
					cliente.setNombre(nombre);
					cliente.setApellido1(apellido1);
					cliente.setApellido2(apellido2);
					cliente.setFechaNacimiento(fechaNacimiento);
					cliente.setEmail(email);
					cliente.setPassword(password);
					cliente.setNivel(Constants.NIVEL_PRINCIPIANTE_MENU);

					firebaseController.guardarCliente(cliente);

					JOptionPane.showMessageDialog(RegistroView.this, Constants.LOGIN_EXITO + cliente.getId(),
							Constants.EXITO, JOptionPane.INFORMATION_MESSAGE);

				} catch (FireBaseException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(RegistroView.this, Constants.ERROR_REGISTRO + e.getMessage(),
							Constants.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		btnRegistro.setOpaque(true);
		btnRegistro.setForeground(new Color(0, 0, 0));
		btnRegistro.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnRegistro.setFocusPainted(false);
		btnRegistro.setContentAreaFilled(false);
		btnRegistro.setBorderPainted(false);
		btnRegistro.setBackground(new Color(255, 255, 255, 150));
		btnRegistro.setBounds(237, 534, 159, 41);
		lblFondoRegistro.add(btnRegistro);

		btnVolver = new JButton(Constants.VOLVER_BOTON);
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login pantallaLogin = new Login();
				pantallaLogin.setVisible(true);
				dispose();
			}
		});

		btnVolver.setOpaque(true);
		btnVolver.setForeground(new Color(0, 0, 0));
		btnVolver.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setBackground(new Color(255, 255, 255, 150));
		btnVolver.setBounds(480, 534, 159, 41);
		lblFondoRegistro.add(btnVolver);

		lblFecNac = new JLabel(Constants.FECHA_NACIMIENTO_LABEL);
		lblFondoRegistro.add(lblFecNac);
		lblFecNac.setOpaque(true);
		lblFecNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecNac.setForeground(Color.BLACK);
		lblFecNac.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblFecNac.setBackground(new Color(181, 179, 179, 150));
		lblFecNac.setBounds(250, 442, 184, 29);

		textFieldFecNac = new JTextField();
		lblFondoRegistro.add(textFieldFecNac);
		textFieldFecNac.setColumns(10);
		textFieldFecNac.setBounds(563, 440, 177, 30);

		lblNombre = new JLabel(Constants.NOMBRE_LABEL);
		lblFondoRegistro.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblNombre.setBounds(246, 155, 148, 29);
		lblNombre.setOpaque(true);
		lblNombre.setBackground(new Color(181, 179, 179, 150));

		lblApellidoUno = new JLabel(Constants.APELLIDO_LABEL);
		lblFondoRegistro.add(lblApellidoUno);
		lblApellidoUno.setOpaque(true);
		lblApellidoUno.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoUno.setForeground(new Color(0, 0, 0));
		lblApellidoUno.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoUno.setBackground(new Color(181, 179, 179, 150));
		lblApellidoUno.setBounds(246, 210, 148, 29);

		lblApellidoDos = new JLabel(Constants.APELLIDO_DOS_LABEL);
		lblFondoRegistro.add(lblApellidoDos);
		lblApellidoDos.setOpaque(true);
		lblApellidoDos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoDos.setForeground(Color.BLACK);
		lblApellidoDos.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoDos.setBackground(new Color(181, 179, 179, 150));
		lblApellidoDos.setBounds(246, 264, 148, 29);

		lblEmail = new JLabel(Constants.EMAIL_LABEL);
		lblFondoRegistro.add(lblEmail);
		lblEmail.setOpaque(true);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblEmail.setBackground(new Color(181, 179, 179, 150));
		lblEmail.setBounds(247, 317, 148, 29);

		lblPassword = new JLabel(Constants.PASSWORD_LABEL);
		lblFondoRegistro.add(lblPassword);
		lblPassword.setOpaque(true);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblPassword.setBackground(new Color(181, 179, 179, 150));
		lblPassword.setBounds(248, 370, 148, 29);

		textFieldNombre = new JTextField();
		lblFondoRegistro.add(textFieldNombre);
		textFieldNombre.setBounds(562, 154, 177, 30);
		textFieldNombre.setColumns(10);

		textFieldApellidoUno = new JTextField();
		lblFondoRegistro.add(textFieldApellidoUno);
		textFieldApellidoUno.setColumns(10);
		textFieldApellidoUno.setBounds(562, 210, 177, 30);

		textFieldApellidoDos = new JTextField();
		lblFondoRegistro.add(textFieldApellidoDos);
		textFieldApellidoDos.setColumns(10);
		textFieldApellidoDos.setBounds(460, 262, 277, 30);

		textFieldEmail = new JTextField();
		lblFondoRegistro.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(461, 317, 276, 30);

		passwordField = new JPasswordField();
		lblFondoRegistro.add(passwordField);
		passwordField.setBounds(562, 369, 177, 30);
	}

	/**
	 * Limpia todos los campos del formulario de registro.
	 * 
	 * Este método restablece todos los campos de texto y contraseña
	 * a su estado inicial vacío.
	 */
	private void limpiarCampos() {
		textFieldNombre.setText("");
		textFieldApellidoUno.setText("");
		textFieldApellidoDos.setText("");
		textFieldFecNac.setText("");
		textFieldEmail.setText("");
		passwordField.setText("");
	}

}
