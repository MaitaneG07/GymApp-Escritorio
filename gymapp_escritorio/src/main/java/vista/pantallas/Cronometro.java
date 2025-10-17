package vista.pantallas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cronometro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFondoDescripcion;
	private JButton btnCronometro;
	private JLabel lblCronometro;
	private JLabel lblNombreEjercicio;
	private JLabel lblFondoCronometro;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cronometro frame = new Cronometro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cronometro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\in2dm3-v\\Documents\\Reto 1\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\\\logoLight.jpg");

		Image imagenOriginal = originalIcon.getImage();
		Image imagenEscalada = imagenOriginal.getScaledInstance(869, 608, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		lblFondoCronometro.setIcon(iconoEscalado);
		lblFondoCronometro = new JLabel("");
		lblFondoCronometro.setBounds(0, 11, 869, 608);
		lblFondoCronometro.setLayout(null);
		contentPane.add(lblFondoCronometro);
		
		
		lblCronometro = new JLabel("Aqui va el cronometro");
		lblCronometro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCronometro.setBounds(320, 418, 156, 42);
		contentPane.add(lblCronometro);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Workout panelWorkout = new Workout();
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(664, 555, 121, 23);
		contentPane.add(btnSalir);
		
		lblNombreEjercicio = new JLabel("Nombre Ejercicio");
		lblNombreEjercicio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombreEjercicio.setBounds(320, 47, 177, 52);
		contentPane.add(lblNombreEjercicio);
		
		btnCronometro = new JButton("Iniciar");
		 String frase1 = "Iniciar";
	        String frase2 = "Parar";
	        
	        final boolean[] esFrase1 = {true};
		
		btnCronometro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        
		        if (esFrase1[0]) {
		        	btnCronometro.setText(frase2);
                } else {
                	btnCronometro.setText(frase1);
                }
                esFrase1[0] = !esFrase1[0]; // Cambia el estado para la próxima vez
            }
		});
		btnCronometro.setBounds(362, 503, 89, 23);
		contentPane.add(btnCronometro);
		
		lblFondoDescripcion = new JLabel("Descripcion del ejercicio");
		lblFondoDescripcion.setBounds(247, 123, 354, 89);
		contentPane.add(lblFondoDescripcion);
		
	
		
		
		
		
		
		

		
	}
}
