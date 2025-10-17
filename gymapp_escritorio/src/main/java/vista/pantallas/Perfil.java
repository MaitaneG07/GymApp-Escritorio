package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.Constants;

public class Perfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel tituloRegistro;
	private JButton btnRegistro;
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

	/**
	 * Create the frame.
	 */
	public Perfil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tituloRegistro = new JLabel("PERFIL");
		tituloRegistro.setOpaque(true);
		tituloRegistro.setBackground(new Color(255, 255, 255, 150));
		tituloRegistro.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		tituloRegistro.setBounds(218, 23, 432, 79);
		contentPane.add(tituloRegistro);
		
		btnRegistro = new JButton("MODIFICAR");
		btnRegistro.setOpaque(true);
		btnRegistro.setForeground(new Color(0, 0, 0));
		btnRegistro.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnRegistro.setFocusPainted(false);
		btnRegistro.setContentAreaFilled(false);
		btnRegistro.setBorderPainted(false);
		btnRegistro.setBackground(new Color(255, 255, 255, 150));
		btnRegistro.setBounds(237, 534, 159, 41);
		contentPane.add(btnRegistro);
		
		btnVolver = new JButton(Constants.VOLVER_BOTON);
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Workout pantallaWorkout = new Workout();
				pantallaWorkout.setVisible(true);
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
		contentPane.add(btnVolver);
		
		lblNombre = new JLabel(Constants.NOMBRE_LABEL);
		contentPane.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblNombre.setBounds(247, 173, 148, 29);
		lblNombre.setOpaque(true);
		lblNombre.setBackground(new Color(181, 179, 179, 150));
		
		lblApellidoUno = new JLabel(Constants.APELLIDO_LABEL);
		contentPane.add(lblApellidoUno);
		lblApellidoUno.setOpaque(true);
		lblApellidoUno.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoUno.setForeground(new Color(0, 0, 0));
		lblApellidoUno.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoUno.setBackground(new Color(181, 179, 179, 150));
		lblApellidoUno.setBounds(247, 228, 148, 29);
		
		lblApellidoDos = new JLabel(Constants.APELLIDO_DOS_LABEL);
		contentPane.add(lblApellidoDos);
		lblApellidoDos.setOpaque(true);
		lblApellidoDos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoDos.setForeground(Color.BLACK);
		lblApellidoDos.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblApellidoDos.setBackground(new Color(181, 179, 179, 150));
		lblApellidoDos.setBounds(247, 282, 148, 29);
		
		lblEmail = new JLabel(Constants.EMAIL_LABEL);
		contentPane.add(lblEmail);
		lblEmail.setOpaque(true);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblEmail.setBackground(new Color(181, 179, 179, 150));
		lblEmail.setBounds(248, 335, 148, 29);
		
		lblPassword = new JLabel(Constants.PASSWORD_LABEL);
		contentPane.add(lblPassword);
		lblPassword.setOpaque(true);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblPassword.setBackground(new Color(181, 179, 179, 150));
		lblPassword.setBounds(249, 388, 148, 29);
		
		lblFecNac = new JLabel(Constants.FECHA_NACIMIENTO_LABEL);
		contentPane.add(lblFecNac);
		lblFecNac.setOpaque(true);
		lblFecNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecNac.setForeground(Color.BLACK);
		lblFecNac.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 14));
		lblFecNac.setBackground(new Color(181, 179, 179, 150));
		lblFecNac.setBounds(250, 442, 184, 29);
		
		textFieldEmail = new JTextField();
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(462, 335, 276, 30);
		
		textFieldApellidoDos = new JTextField();
		contentPane.add(textFieldApellidoDos);
		textFieldApellidoDos.setColumns(10);
		textFieldApellidoDos.setBounds(461, 280, 277, 30);
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField);
		passwordField.setBounds(563, 387, 177, 30);
		
		textFieldApellidoUno = new JTextField();
		contentPane.add(textFieldApellidoUno);
		textFieldApellidoUno.setColumns(10);
		textFieldApellidoUno.setBounds(563, 228, 177, 30);
		
		textFieldNombre = new JTextField();
		contentPane.add(textFieldNombre);
		textFieldNombre.setBounds(563, 172, 177, 30);
		textFieldNombre.setColumns(10);
		
		textFieldFecNac = new JTextField();
		contentPane.add(textFieldFecNac);
		textFieldFecNac.setColumns(10);
		textFieldFecNac.setBounds(563, 440, 177, 30);
	}

}
