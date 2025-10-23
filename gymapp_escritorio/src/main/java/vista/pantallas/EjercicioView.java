package vista.pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EjercicioView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDescripcion;
	private JButton btnCronometro;
	private JLabel lblCronometro;
	private JLabel lblNombreEjercicio;
	private DefaultTableModel tablaDetallesSeries;
	private JTable tableSeries;
	private JScrollPane scrollPane;
	private JLabel lblFotoEjercicio;
	private JButton btnPerfil;

	/**
	 * Create the frame.
	 */
	public EjercicioView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPerfil = new JButton();
		
		//LOGO MAITANE
		 ImageIcon iconoOriginal = new ImageIcon((Constants.LOGO_OSCURO_CASA));
//		ImageIcon iconoOriginal = new ImageIcon(Constants.LOGO_OSCURO_CLASE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);
		
		
		lblCronometro = new JLabel("Aqui va el cronometro");
		lblCronometro.setFont(new Font(Constants.FONT_FAMILY, Font.PLAIN, 15));
		lblCronometro.setBounds(331, 426, 177, 74);
		contentPane.add(lblCronometro);
		
		JButton btnSalir = new JButton(Constants.SALIR_BOTON);
		btnSalir.setBackground(new Color(255, 255, 255));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WorkoutView panelWorkout = new WorkoutView();
				panelWorkout.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(664, 555, 121, 23);
		contentPane.add(btnSalir);
		
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
		btnCronometro.setBounds(341, 520, 133, 58);
		contentPane.add(btnCronometro);
		
		lblDescripcion = new JLabel("Descripcion del ejercicio");
		lblDescripcion.setBounds(98, 97, 411, 163);
		contentPane.add(lblDescripcion);
		
		lblNombreEjercicio = new JLabel("Nombre del ejercicio");
		lblNombreEjercicio.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 20));
		lblNombreEjercicio.setBounds(304, 38, 244, 28);
		contentPane.add(lblNombreEjercicio);
		
		lblFotoEjercicio = new JLabel("");
		lblFotoEjercicio.setIcon(new ImageIcon("C:\\Users\\in2dm3-v\\Documents\\Reto 1\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\remo.jpg"));
		lblFotoEjercicio.setBounds(565, 97, 201, 163);
		contentPane.add(lblFotoEjercicio);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 292, 434, 99);
		contentPane.add(scrollPane);
		
		 tablaDetallesSeries = new DefaultTableModel();
		 tablaDetallesSeries.addColumn(Constants.COLUMNA_SERIES);
		 tablaDetallesSeries.addColumn(Constants.COLUMNA_TIEMPO);
		 tablaDetallesSeries.addColumn(Constants.COLUMNA_DESCANSO);
		
		tableSeries = new JTable(tablaDetallesSeries);
		scrollPane.setViewportView(tableSeries);

	}
}
