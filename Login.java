package Ejercicioractico;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Principal {

	private JFrame frame;
	private JTextField txtCantidad;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel txtError;
	private JRadioButton rdbtnGasto;
	private JRadioButton rdbtnIngreso;
	private JButton btnAñadir;
	private TablaGastosIngresos pantalla1 = new TablaGastosIngresos(this);;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Principal window = new Principal();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Principal() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel txtImporte = new JLabel("Importe: ");
		txtImporte.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtImporte.setBounds(95, 75, 84, 25);
		frame.getContentPane().add(txtImporte);

		txtCantidad = new JTextField();
		txtCantidad.setBounds(180, 75, 143, 29);
		frame.getContentPane().add(txtCantidad);
		txtCantidad.setColumns(10);

		rdbtnIngreso = new JRadioButton("Ingreso");
		rdbtnIngreso.setSelected(true);
		rdbtnIngreso.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnIngreso);
		rdbtnIngreso.setBounds(95, 125, 84, 29);
		frame.getContentPane().add(rdbtnIngreso);

		rdbtnGasto = new JRadioButton("Gasto");
		rdbtnGasto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnGasto);
		rdbtnGasto.setBounds(95, 157, 84, 29);
		frame.getContentPane().add(rdbtnGasto);

		txtError = new JLabel("");
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("Tahoma", Font.ITALIC, 11));
		txtError.setBounds(180, 106, 200, 25);
		frame.getContentPane().add(txtError);

		btnAñadir = new JButton("Añadir");
		btnAñadir.setEnabled(false);
		btnAñadir.setBounds(190, 130, 133, 56);
		frame.getContentPane().add(btnAñadir);

		txtCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int cantidad = Integer.parseInt(txtCantidad.getText());

					if (cantidad <= 0) {
						txtError.setText("La cantidad debe ser mayor que 0");
						btnAñadir.setEnabled(false);
						return;
					}

					if (rdbtnIngreso.isSelected()) {
						txtError.setText("");
						btnAñadir.setEnabled(true);
					} else if (rdbtnGasto.isSelected()) {
						if (cantidad <= pantalla1.getSaldoActual()) {
							txtError.setText("");
							btnAñadir.setEnabled(true);
						} else {
							txtError.setText("Saldo insuficiente para el gasto");
							btnAñadir.setEnabled(false);
						}
					}
				} catch (NumberFormatException ex) {
					btnAñadir.setEnabled(false);
					txtError.setText("Formato inválido");
				}
			}
		});

		btnAñadir.addActionListener(e -> {
			int cantidad = Integer.parseInt(txtCantidad.getText());

			if (rdbtnIngreso.isSelected()) {
				pantalla1.agregarMovimiento(0, cantidad);
			} else {
				pantalla1.agregarMovimiento(cantidad, 0);
			}

			txtCantidad.setText("");
			btnAñadir.setEnabled(false);
			pantalla1.setVisible(true);
			frame.setVisible(false);
		});
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
