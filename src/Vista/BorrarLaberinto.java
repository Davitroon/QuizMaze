package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Modelo;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BorrarLaberinto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param modelo 
	 * @param menuAdmin 
	 */
	public BorrarLaberinto(MenuAdmin menuAdmin, Modelo modelo) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.RED);
		btnVolver.setBounds(10, 227, 84, 23);
		contentPane.add(btnVolver);
		
		JLabel lblNewLabel = new JLabel("Aqui hay que configurar para borar los laberintos");
		lblNewLabel.setBounds(88, 119, 246, 32);
		contentPane.add(lblNewLabel);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				menuAdmin.setVisible(true);
			}
		});
	}
	
	
}
