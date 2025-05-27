package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Login;
import logica.Modelo;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class GestionLaberinto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableLaberintos;
	private DefaultTableModel modeloLaberintos;
	
	private Modelo modelo;
	private CrearLaberinto crearLaberinto;
	private JButton btnBorrar;
	

	/**
	 * Create the frame.
	 * @param modelo 
	 * @param login 
	 */
	public GestionLaberinto(Login login, Modelo modelo) {
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 404);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.setBackground(new Color(128, 128, 128));
		btnSalir.setBounds(31, 321, 94, 33);
		contentPane.add(btnSalir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 102, 806, 208);
		contentPane.add(scrollPane);
		
		modeloLaberintos = new DefaultTableModel(
			    new Object[][] {},
			    new String[] { "id", "ancho", "alto", "cocodrilos", "daño_cocodrilos", "botiquines", "vida_botiquines", "tiempo_pregunta", "daño_pregunta", "preguntas" }
			) {
			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return false; // No permitir edición de celdas
			    }
			};
		
		tableLaberintos = new JTable();
		tableLaberintos.setModel(modeloLaberintos);
		scrollPane.setViewportView(tableLaberintos);
		
		JLabel lblGestionLaberintos = new JLabel("Gestión de laberintos");
		lblGestionLaberintos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionLaberintos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGestionLaberintos.setBounds(31, 11, 806, 36);
		contentPane.add(lblGestionLaberintos);
		
		JButton btnCrearLaberinto = new JButton("Crear laberinto");
		btnCrearLaberinto.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCrearLaberinto.setBackground(new Color(128, 255, 255));
		btnCrearLaberinto.setBounds(569, 58, 129, 33);
		contentPane.add(btnCrearLaberinto);
		
		btnBorrar = new JButton("Borrar laberinto");
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBorrar.setBackground(new Color(255, 128, 128));
		btnBorrar.setEnabled(false);
		btnBorrar.setBounds(708, 58, 129, 33);
		contentPane.add(btnBorrar);
		
		// Clic boton volver
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarVentana();
				dispose();
				login.logearse();
			}
		});
		
		// Clic boton crear laberinto
		btnCrearLaberinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (crearLaberinto == null) {
					crearLaberinto = new CrearLaberinto(GestionLaberinto.this, modelo);
				}	
				crearLaberinto.getFrame().setVisible(true);
				btnBorrar.setEnabled(false);
				setVisible(false);
			}
		});
		
		// Clic boton borrar laberinto
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = tableLaberintos.getSelectedRow();
	            int idLaberinto = (int) tableLaberintos.getValueAt(filaSeleccionada, 0); // Columna 0 = id
	            modelo.borrarLaberinto(idLaberinto);
	            btnBorrar.setEnabled(false);
		        actualizarLaberintos();
			}
		});
		
		// Clic en la tabla laberintos
		tableLaberintos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        int filaSeleccionada = tableLaberintos.getSelectedRow();
		        if (filaSeleccionada != -1) { // -1 significa que no hay fila seleccionada
		            btnBorrar.setEnabled(true);
		        }
			}
		});
	}
	
	/**
	 * Cargar los laberintos de la BD y meterlos en la tabla
	 */
	public void actualizarLaberintos() {
	    ResultSet rset = modelo.consultarDatos("laberintos");

	    if (modeloLaberintos.getRowCount() > 0) {
	    	modeloLaberintos.setRowCount(0); // Vaciar la tabla de disposiciones
	    }
	    
	    try {
	        while (rset.next()) {
	            Object[] fila = new Object[] {
	                    rset.getInt("id"),
	                    rset.getInt("ancho"),
	                    rset.getInt("alto"),
	                    rset.getInt("num_cocodrilos"),
	                    rset.getInt("daño_cocodrilos"),
	                    rset.getInt("num_botiquines"),
	                    rset.getInt("vida_botiquines"),
	                    rset.getInt("tiempo_pregunta"),
	                    rset.getInt("daño_pregunta"),
	                    rset.getInt("num_preguntas")
                };
	            modeloLaberintos.addRow(fila);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Reinicia el estado visual de la ventana.
	 */
	public void reiniciarVentana() {
	    btnBorrar.setEnabled(false); // Desactivar botón Jugar

	    tableLaberintos.clearSelection(); // Quitar selección
	    
	    if (modeloLaberintos.getRowCount() > 0) {
	    	modeloLaberintos.setRowCount(0); // Vaciar la tabla de disposiciones
	    }
	}
}
