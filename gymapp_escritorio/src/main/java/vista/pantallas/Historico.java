package vista.pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.Constants;
import vista.Login;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Historico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFondoHistorico;
	private JLabel tituloHistorico;
	private JTable tableHistoricos;
	private JButton btnAtras;
	private DefaultTableModel modeloTabla;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public Historico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblFondoHistorico = new JLabel();
		
		//Logo AKIRA
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CLASE_Ak);
		
		//LOGO MAITANE
		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CLASE);
//		ImageIcon originalIcon = new ImageIcon(Constants.LOGO_CLARO_CASA);

		Image imagenOriginal = originalIcon.getImage();
		
		Image imagenEscalada = imagenOriginal.getScaledInstance(885, 658, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		lblFondoHistorico.setIcon(iconoEscalado);
		lblFondoHistorico.setBounds(0, 0, 885, 658);
		contentPane.add(lblFondoHistorico);
		
		btnAtras = new JButton(Constants.VOLVER_BOTON);
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login pantallaLogin = new Login();
				pantallaLogin.setVisible(true);
				dispose();
			}
		});
		btnAtras.setOpaque(true);
		btnAtras.setForeground(new Color(0, 0, 0));
		btnAtras.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 13));
		btnAtras.setFocusPainted(false);
		btnAtras.setContentAreaFilled(false);
		btnAtras.setBorderPainted(false);
		btnAtras.setBackground(new Color(255, 255, 255, 150));
		btnAtras.setBounds(48, 156, 89, 23);
		lblFondoHistorico.add(btnAtras);
		
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn(Constants.COLUMNA_NOMBRE_WORKOUT);
		modeloTabla.addColumn(Constants.COLUMNA_NIVEL_WORKOUT);
		modeloTabla.addColumn(Constants.TIEMPO_TOTAL_WORKOUT);
		modeloTabla.addColumn(Constants.TIEMPO_PREVISTO_WORKOUT);
		modeloTabla.addColumn(Constants.FECHA_WORKOUT);
		modeloTabla.addColumn(Constants.EJERCICIOS_COMPLETADOS);
		
		tableHistoricos = new JTable(modeloTabla);
		tableHistoricos.getTableHeader().setFont(new Font(Constants.FONT_FAMILY, 1, 13));
		tableHistoricos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //poder seleccionar solo una tabla
		tableHistoricos.setBounds(93, 237, 660, 319);
		scrollPane = new JScrollPane(tableHistoricos);
		scrollPane.setBounds(48, 237, 764, 319);
		
		// Hacer la tabla transparente
		tableHistoricos.setOpaque(false);
		tableHistoricos.setBackground(new Color(0, 0, 0, 0));
		((DefaultTableCellRenderer)tableHistoricos.getDefaultRenderer(Object.class)).setOpaque(false);

		// Hacer el scroll transparente
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// Cabecera semitransparente
		tableHistoricos.getTableHeader().setOpaque(false);
		tableHistoricos.getTableHeader().setBackground(new Color(255, 255, 255, 120));

		
		lblFondoHistorico.add(scrollPane);
		
		tituloHistorico = new JLabel(Constants.HISTORIAL_WORKOUTS_LABEL);
		lblFondoHistorico.add(tituloHistorico);
		tituloHistorico.setOpaque(true);
		tituloHistorico.setBackground(new Color(255, 255, 255, 150));
		tituloHistorico.setFont(new Font(Constants.FONT_FAMILY, Font.BOLD, 39));
		tituloHistorico.setHorizontalAlignment(SwingConstants.CENTER);
		tituloHistorico.setBounds(175, 24, 545, 79);
	}
}
