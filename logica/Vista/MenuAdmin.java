package Vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Login;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Login logeo;

	public MenuAdmin(Login logeo) {
		this.logeo = logeo;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLabelAdmin = new JLabel("Administrador");
		lblLabelAdmin.setFont(new Font("Source Han Sans JP Normal", Font.BOLD, 20));
		lblLabelAdmin.setBounds(150, 11, 149, 27);
		contentPane.add(lblLabelAdmin);
		
		JButton btnBorrarLaberinto = new JButton("BorrarLaberinto");
		btnBorrarLaberinto.setBounds(238, 93, 149, 92);
		contentPane.add(btnBorrarLaberinto);
		
		JButton btnAgregarLaberinto = new JButton("AñadirLaberinto");
		btnAgregarLaberinto.setBounds(40, 93, 149, 92);
		contentPane.add(btnAgregarLaberinto);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 0, 0));
		btnVolver.setBounds(20, 231, 84, 23);
		contentPane.add(btnVolver);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdmin.this.dispose();
				logeo.logearse();
			}
		});
		
		// Clic boton añadir laberinto
		btnAgregarLaberinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pendiente
			}
		});
		
		// Clic boton borran laberinto
		btnBorrarLaberinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 // pendiente	
			}
		});
	}
}
