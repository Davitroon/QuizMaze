package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.Login;

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
		lblUsuario.setBounds(94, 11, 233, 27);
		contentPane.add(lblUsuario);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnJugar.setBounds(153, 106, 120, 44);
		contentPane.add(btnJugar);
		
		JButton btnNewButton = new JButton("Mostrar Laberinto");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(133, 155, 155, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1_1 = new JButton("Salir");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario.this.dispose();
				logeo.logearse();
			}
		});
		btnNewButton_1_1.setBackground(Color.RED);
		btnNewButton_1_1.setBounds(15, 231, 84, 23);
		contentPane.add(btnNewButton_1_1);
		
		JLabel lblNewLabel = new JLabel("Hola " + logeo.getNombreUsuario());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(153, 68, 161, 27);
		contentPane.add(lblNewLabel);
	}

}
