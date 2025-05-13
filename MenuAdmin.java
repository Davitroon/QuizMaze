
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
		
		JLabel lblNewLabel = new JLabel("Administrador");
		lblNewLabel.setFont(new Font("Source Han Sans JP Normal", Font.BOLD, 20));
		lblNewLabel.setBounds(150, 11, 149, 27);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("BorrarLaberinto");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(238, 93, 149, 92);
		contentPane.add(btnNewButton);
		
		JButton btnAadirlaberinto = new JButton("AñadirLaberinto");
		btnAadirlaberinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAadirlaberinto.setBounds(40, 93, 149, 92);
		contentPane.add(btnAadirlaberinto);
		
		JButton btnNewButton_1 = new JButton("Salir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdmin.this.dispose();
				logeo.logearse();
			}
		});
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setBounds(20, 231, 84, 23);
		contentPane.add(btnNewButton_1);
	}
}
