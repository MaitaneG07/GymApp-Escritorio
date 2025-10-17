package vista.pantallas;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Workout extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPerfil;
	private JPopupMenu popupMenuPerfil;
	private JMenuItem menuGestionPerfil;
	private JMenuItem menuHistorico;
	private JMenuItem menuCerrarPerfil;
	private JTable tablaWorkouts;
	private JTable tablaDetallesWorkout;
	private JScrollPane scrollPaneWorkouts;
	private JScrollPane scrollPaneDetallesWorkout;
	private JButton btnSeleccionar;
	private JButton btnFiltrarPorNivel;
	private ImageIcon iconoOriginal;
	private Image imgEscalada;
	private JPopupMenu popupMenuNivel;
	public DefaultTableModel modeloWorkouts;
	private DefaultTableModel modeloDetallesWorkouts;
	
	// PARA OBTENER EL ID DEL USUARIO REGISTRADO
	//public int idUsuario;
	
	/**
	 * Recupera el id de agencia, carga el panel y el logo, y muestra la tabla
	 * actualizada.
	 * 
	 * @param idAgencia
	 */
//	public void setIdAgencia(int idAgencia, int idViaje) {
//		this.idAgencia = idAgencia;
//		this.idViaje = idViaje;
//		if (idAgencia > 0) {
//			System.out.println("🛠️Seteando ID Agencia en PanelViajesEventos: " + idAgencia);// visualizar consola
//			cargarColorPanel(idAgencia);
//			cargarLogo(idAgencia);
//			actualizarTablaViajes(modeloViajes, idAgencia);
//			actualizarTablaEventos(modeloEventos, idViaje);
//			panelViajesEventos.repaint();// Forzar actualización
//		}
//	}

	/**
	 * Create the frame.
	 */
	public Workout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPerfil = new JButton();
		iconoOriginal = new ImageIcon(("C:\\Users\\Usuario\\Desktop\\GymApp\\GymApp-Escritorio\\gymapp_escritorio\\src\\main\\java\\logoapp.png"));
		imgEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		btnPerfil.setIcon(new ImageIcon(imgEscalada));
		btnPerfil.setBounds(10, 11, 60, 60);
		btnPerfil.setFocusPainted(false);
		btnPerfil.setContentAreaFilled(false);
		btnPerfil.setBorderPainted(false);
		contentPane.add(btnPerfil);
		
		popupMenuPerfil = new JPopupMenu();
		
		menuGestionPerfil = new JMenuItem("Gestionar perfil");
		popupMenuPerfil.add(menuGestionPerfil);
		
		menuHistorico = new JMenuItem("Consultar histórico");
		popupMenuPerfil.add(menuHistorico);
		
		menuCerrarPerfil = new JMenuItem("Cerrar sesión");
		popupMenuPerfil.add(menuCerrarPerfil);
		
	    btnPerfil.addActionListener(e -> {
	        popupMenuPerfil.show(btnPerfil, 0, btnPerfil.getHeight());
	        
	    });

	    scrollPaneWorkouts = new JScrollPane();
        scrollPaneWorkouts.setBounds(162, 106, 574, 149);
        contentPane.add(scrollPaneWorkouts);
        
        modeloWorkouts = new DefaultTableModel();
        modeloWorkouts.addColumn("NOMBRE");
        modeloWorkouts.addColumn("EJERCICIOS");
        modeloWorkouts.addColumn("NIVEL");
        modeloWorkouts.addColumn("VÍDEO");
        
        tablaWorkouts = new JTable(modeloWorkouts);
        scrollPaneWorkouts.setViewportView(tablaWorkouts);
        
        scrollPaneDetallesWorkout = new JScrollPane();
        scrollPaneDetallesWorkout.setBounds(162, 351, 574, 149);
        contentPane.add(scrollPaneDetallesWorkout);
        
        modeloDetallesWorkouts = new DefaultTableModel();
        modeloDetallesWorkouts.addColumn("NOMBRE");
        modeloDetallesWorkouts.addColumn("DESCRIPCIÓN");
        modeloDetallesWorkouts.addColumn("SERIES");
        
        tablaDetallesWorkout = new JTable(modeloDetallesWorkouts);
        scrollPaneDetallesWorkout.setViewportView(tablaDetallesWorkout);
        
        btnSeleccionar = new JButton("SELECCIONAR");
        btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 13));
        btnSeleccionar.setBounds(367, 554, 155, 40);
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setContentAreaFilled(false);
        btnSeleccionar.setBorderPainted(false);
        contentPane.add(btnSeleccionar);
        
        btnFiltrarPorNivel = new JButton();
        btnFiltrarPorNivel.setText("Nivel");
        btnFiltrarPorNivel.setFocusPainted(false);
        btnFiltrarPorNivel.setContentAreaFilled(false);
        btnFiltrarPorNivel.setBorderPainted(false);
        btnFiltrarPorNivel.setBounds(761, 11, 102, 40);
        contentPane.add(btnFiltrarPorNivel);
        
        popupMenuNivel = new JPopupMenu();
        //Crear los MenuItem cargandolos de la bbdd
	}
}
