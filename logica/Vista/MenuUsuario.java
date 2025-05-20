package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Login;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MenuUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Login logeo;
	private String nombreUsuario;

	public MenuUsuario(Login logeo) {
		this.logeo = logeo;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Bienvenido al Laberinto");
		lblUsuario.setFont(new Font("Source Han Sans JP Normal", Font.BOLD, 20));
		lblUsuario.setBounds(96, 11, 233, 27);
		contentPane.add(lblUsuario);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setBounds(153, 106, 120, 44);
		contentPane.add(btnJugar);
		
		JButton btnMostrar = new JButton("Mostrar Laberinto");
		btnMostrar.setBounds(133, 155, 155, 23);
		contentPane.add(btnMostrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.RED);
		btnVolver.setBounds(15, 231, 84, 23);
		contentPane.add(btnVolver);
		
		JLabel lblBienvenida = new JLabel("Hola " + logeo.getNombreUsuario());
		lblBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBienvenida.setBounds(153, 68, 161, 27);
		contentPane.add(lblBienvenida);
		
		// Clic boton mostrar laberinto
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario.this.dispose();
				logeo.logearse();
			}
		});
		
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}

}
