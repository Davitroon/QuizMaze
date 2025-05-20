import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;


public class InterfazLaberinto {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazLaberinto window = new InterfazLaberinto();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazLaberinto() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/////////////////////////////////////////////// Pantalla Completa
		frame = new JFrame();
		frame.setBounds(100, 100, 452, 700); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//
		/////////////////////////////////////////////// Vista del mapa que ve el jugador
		JPanel panelVistaJugador = new JPanel();
		panelVistaJugador.setBounds(50, 50, 350, 350);
		frame.getContentPane().add(panelVistaJugador);
		panelVistaJugador.setLayout(new GridLayout(3, 3, 5, 5));// Establece un GridLayout de 3x3
		// Celdas estáticas ///////////////////////////////////////////////////////////////////////-----------------------
		JPanel celda0 = new JPanel();
		celda0.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda0);

		JPanel celda1 = new JPanel();
		celda1.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda1);

		JPanel celda2 = new JPanel();
		celda2.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda2);

		JPanel celda3 = new JPanel();
		celda3.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda3);

		JPanel celda4 = new JPanel();
		celda4.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		celda4.setBackground(java.awt.Color.YELLOW); // ESTA CELDA SIEMPRE SERÁ AMARILLA	
		panelVistaJugador.add(celda4);				 // INDICA LA POSICIÓN DEL JUGADOR

		JPanel celda5 = new JPanel();
		celda5.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda5);

		JPanel celda6 = new JPanel();
		celda6.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda6);

		JPanel celda7 = new JPanel();
		celda7.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda7);

		JPanel celda8 = new JPanel();
		celda8.setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE));
		panelVistaJugador.add(celda8);
		///////////////////////////////////////////////////////////////////////////////////////////-----------------------
		///////// BOTONES DIRECCION
		JButton btnARRIBA = new JButton("↑");
		btnARRIBA.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnARRIBA.setBounds(180, 410, 75, 65);
		frame.getContentPane().add(btnARRIBA);
		
		JButton btnABAJO = new JButton("↓");
		btnABAJO.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnABAJO.setBounds(180, 494, 75, 65);
		frame.getContentPane().add(btnABAJO);
		
		JButton btnIZQUIERDA = new JButton("←");
		btnIZQUIERDA.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnIZQUIERDA.setBounds(90, 494, 75, 65);
		frame.getContentPane().add(btnIZQUIERDA);
		
		JButton btnDERECHA = new JButton("→");
		btnDERECHA.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnDERECHA.setBounds(265, 494, 75, 65);
		frame.getContentPane().add(btnDERECHA);
		///////////////////////////////////////////////////
		//!!!!!!!!
		//!!!!!!!!		[FALTA AÑADIR QUE AL CREAR EL GRID INGRESE POR COLORES LOS MUROS]
		//!!!!!!!!
//final
	}
}
