package vista.pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Constants;

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
	private JButton btnSalir;
	private JButton btnPrueba;

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
		

		lblFondoCronometro = new JLabel();
		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CASA);
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CLASE);

		Image imagenOriginal = originalIcon.getImage();
		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		lblFondoCronometro.setIcon(iconoEscalado);
		lblFondoCronometro.setBounds(0, 0, 873, 623);
		lblFondoCronometro.setLayout(null);
		contentPane.add(lblFondoCronometro);
		
		
		lblCronometro = new JLabel("Aqui va el cronometro");
		lblCronometro.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 15));
		lblCronometro.setBounds(320, 418, 156, 42);
		contentPane.add(lblCronometro);
		
		btnSalir = new JButton(Constants.SALIR_BOTON);
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Workout panelWorkout = new Workout();
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(664, 555, 121, 23);
		lblFondoCronometro.add(btnSalir);
		
		lblNombreEjercicio = new JLabel("Aquí va el Nombre Ejercicio");
		lblNombreEjercicio.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 20));
		lblNombreEjercicio.setBounds(320, 47, 177, 52);
		lblFondoCronometro.add(lblNombreEjercicio);
		
		btnCronometro = new JButton(Constants.INICIAR_BOTON);
		 String frase1 = Constants.INICIAR_BOTON;
	        String frase2 = Constants.PARAR_BOTON;
	        
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
		lblFondoCronometro.add(btnCronometro);
		
		lblFondoDescripcion = new JLabel("Aquí va Descripcion del ejercicio");
		lblFondoDescripcion.setBounds(247, 123, 354, 89);
		lblFondoCronometro.add(lblFondoDescripcion);
		
		btnPrueba = new JButton(Constants.PRUEBA_BOTON);
		lblFondoCronometro.add(btnPrueba);
		btnPrueba.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Resumen panelResumen = new Resumen();
				panelResumen.setVisible(true);
				dispose();
			}
		});
		btnPrueba.setBounds(48, 557, 222, 23);

	}
}
