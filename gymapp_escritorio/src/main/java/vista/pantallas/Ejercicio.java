package vista.pantallas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Ejercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDescripcion;
	private JButton btnCronometro;
	private JLabel lblCronometro;
	private JTable tableSeries;
	private DefaultTableModel tablaDetallesSeries;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio frame = new Ejercicio();
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
	public Ejercicio() {
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
		
		
		
		lblCronometro = new JLabel("Aqui va el cronometro");
		lblCronometro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCronometro.setBounds(332, 435, 177, 74);
		contentPane.add(lblCronometro);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(255, 255, 255));
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
		btnCronometro.setBounds(352, 520, 133, 58);
		contentPane.add(btnCronometro);
		
		lblDescripcion = new JLabel("Descripcion del ejercicio");
		lblDescripcion.setBounds(98, 97, 411, 137);
		contentPane.add(lblDescripcion);
		
		JLabel lblNombreEjercicio = new JLabel("Nombre del ejercicio");
		lblNombreEjercicio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombreEjercicio.setBounds(304, 38, 244, 28);
		contentPane.add(lblNombreEjercicio);
		
		JLabel lblFotoEjercicio = new JLabel("Foto");
		lblFotoEjercicio.setBounds(565, 97, 169, 137);
		contentPane.add(lblFotoEjercicio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 325, 434, 99);
		contentPane.add(scrollPane);
		
		 tablaDetallesSeries = new DefaultTableModel();
		 tablaDetallesSeries.addColumn("SERIE");
		 tablaDetallesSeries.addColumn("TIEMPO");
		 tablaDetallesSeries.addColumn("DESCANSO");
		
		tableSeries = new JTable(tablaDetallesSeries);
		scrollPane.setViewportView(tableSeries);
		
		
	
		
		
		
		
		
		

		
	}
}
