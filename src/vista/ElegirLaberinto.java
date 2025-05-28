
package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import logica.Disposicion;
import logica.Jugador;
import logica.Laberinto;
import logica.Login;
import logica.Modelo;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

public class ElegirLaberinto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableLaberintos;
	private JTable tableDisposicion;
	private DefaultTableModel modeloLaberintos;
	private DefaultTableModel modeloDisposiciones;
	
	private Modelo modelo;
	private JButton btnJugar;
	private JButton btnJugarNuevaDisp;

	/**
	 * Create the frame.
	 * @param menuUsuario 
	 */
	public ElegirLaberinto(Login login, Modelo modelo, Jugador jugador) {
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 396);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJugar = new JLabel("Bienvenido " + login.getNombreUsuario() + ", elige una laberinto y disposición para jugar.");
		lblJugar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblJugar.setBounds(10, 21, 803, 27);
		lblJugar.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblJugar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 80, 557, 188);
		contentPane.add(scrollPane);
		
		modeloLaberintos = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "id", "ancho", "alto", "num_cocodrilos", "daño_cocodrilos", "num_botiquines",
			        "vida_botiquines", "tiempo_pregunta", "daño_pregunta", "num_preguntas"
			    }
			    
			);

			
		modeloDisposiciones = new DefaultTableModel(
			    new Object[][] {
			        
			    },
			    new String[] {
			        "id_disposicion"
			    }
			) {
			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return false; // No permitir edición de celdas
			    }
			};
		
		tableLaberintos = new JTable();
		tableLaberintos.setModel(modeloLaberintos);
		scrollPane.setViewportView(tableLaberintos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(600, 80, 171, 188);
		contentPane.add(scrollPane_1);
		
		tableDisposicion = new JTable();
		tableDisposicion.setModel(modeloDisposiciones);
		scrollPane_1.setViewportView(tableDisposicion);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setForeground(new Color(0, 0, 0));
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(25, 298, 99, 34);
		contentPane.add(btnVolver);
		
		btnJugar = new JButton("Jugar");
		btnJugar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnJugar.setBackground(new Color(128, 255, 255));
		btnJugar.setEnabled(false);
		btnJugar.setBounds(672, 298, 99, 34);
		contentPane.add(btnJugar);
		
		btnJugarNuevaDisp = new JButton("Jugar con nueva disposicion");
		btnJugarNuevaDisp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnJugarNuevaDisp.setEnabled(false);
		btnJugarNuevaDisp.setBackground(new Color(128, 255, 255));
		btnJugarNuevaDisp.setBounds(469, 298, 193, 34);
		contentPane.add(btnJugarNuevaDisp);
		
		// Clic boton volver 
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarVentana();
				dispose();
				login.logearse();
			}
		});
		
		// Clic en una celda de tabla laberintos
		tableLaberintos.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	btnJugar.setEnabled(false);
		        int fila = tableLaberintos.rowAtPoint(e.getPoint());
		        int columna = tableLaberintos.columnAtPoint(e.getPoint());

	            // Comprobar si se ha hecho clic en una celda válida
		        if (fila >= 0 && columna >= 0) {
		        	btnJugarNuevaDisp.setEnabled(true);
		            // Limpiar modeloDisposiciones si tiene datos previos
		            if (modeloDisposiciones.getRowCount() > 0) {
		                modeloDisposiciones.setRowCount(0);
		            }
		            
		        	// Consultar las disposiciones mandando el id del laberinto elegido
		            ResultSet rset = modelo.consultarDisposiciones((int) modeloLaberintos.getValueAt(fila, 0));
		            
		            try {
		            	// Meter las disposiciones nuevas al modelo
						while (rset.next()) {
						    Object[] filaDisposicion = new Object[] {
						        rset.getInt("id"),
						    };
						    modeloDisposiciones.addRow(filaDisposicion);
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        }
		    }
		});
		
		// Clic en una celda de la tabla de disposiciones
		tableDisposicion.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int fila = tableDisposicion.rowAtPoint(e.getPoint());
		        int columna = tableDisposicion.columnAtPoint(e.getPoint());

		        // Comprobar si se ha hecho clic en una celda válida
		        if (fila >= 0 && columna >= 0) {
		            btnJugar.setEnabled(true);
		        }
		    }
		});
		//////////////////////////////////////////////////////////////////////////////////* AL JUEGO
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int filaLaberinto = tableLaberintos.getSelectedRow();
		        int filaDisposicion = tableDisposicion.getSelectedRow();
		        if (filaLaberinto == -1 || filaDisposicion == -1) {
		            JOptionPane.showMessageDialog(null, "Selecciona un laberinto y una disposición.");
		            return;
		        }

		        // Obtener datos del laberinto y disposición seleccionados
		        int idDisposicion = (int) modeloDisposiciones.getValueAt(filaDisposicion, 0);
		        int ancho = (int) modeloLaberintos.getValueAt(filaLaberinto, 1);
		        int alto = (int) modeloLaberintos.getValueAt(filaLaberinto, 2);

		        // Ajustamos los índices de columna según el orden de tu modeloLaberintos
		        int danoCocodrilo = (int) modeloLaberintos.getValueAt(filaLaberinto, 4);    // daño_cocodrilos
		        int vidaBotiquin = (int) modeloLaberintos.getValueAt(filaLaberinto, 6);     // vida_botiquines
		        int tiempoPregunta = (int) modeloLaberintos.getValueAt(filaLaberinto, 7);   // tiempo_pregunta
		        int danoPregunta = (int) modeloLaberintos.getValueAt(filaLaberinto, 8);     // daño_pregunta

		        int[][] matriz = modelo.cargarMatrizDisposicion(idDisposicion, ancho, alto);
		        int fila = tableLaberintos.getSelectedRow();
		        int idLab  = (int) modeloLaberintos.getValueAt(fila, 0);
		        fila = tableDisposicion.getSelectedRow();
		        int idDisp = (int) modeloDisposiciones.getValueAt(fila, 0);

		        // Llamamos al constructor ampliado de InterfazLaberinto
		        new InterfazLaberinto(matriz, ancho, alto, jugador, modelo, tiempoPregunta, vidaBotiquin, danoCocodrilo, danoPregunta, idLab, idDisp, ElegirLaberinto.this);

		        dispose();
		    }
		});

		//////////////////////////////////////////////////////////////////////////////////* AL JUEGO

		btnJugarNuevaDisp.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int filaLab = tableLaberintos.getSelectedRow();
		        if (filaLab == -1) {
		            JOptionPane.showMessageDialog(null,
		                "Selecciona primero un laberinto.",
		                "Atención", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        // Extraer datos del laberinto
		        int idLab           = (int) modeloLaberintos.getValueAt(filaLab, 0);
		        int ancho           = (int) modeloLaberintos.getValueAt(filaLab, 1);
		        int alto            = (int) modeloLaberintos.getValueAt(filaLab, 2);
		        int numCocodrilos   = (int) modeloLaberintos.getValueAt(filaLab, 3);
		        int dañoCocodrilos  = (int) modeloLaberintos.getValueAt(filaLab, 4);
		        int numBotiquines   = (int) modeloLaberintos.getValueAt(filaLab, 5);
		        int vidaBotiquines  = (int) modeloLaberintos.getValueAt(filaLab, 6);
		        int tiempoPregunta  = (int) modeloLaberintos.getValueAt(filaLab, 7);
		        int dañoPregunta    = (int) modeloLaberintos.getValueAt(filaLab, 8);
		        int numPreguntas    = (int) modeloLaberintos.getValueAt(filaLab, 9);

		        // Carga la matriz base (ceros+muros) y crea la Disposicion
		        int[][] base = modelo.cargarMatrizDisposicion(idLab, ancho, alto);
		        Disposicion nuevaDisp = new Disposicion(base, idLab, modelo);

		        // Genera elementos y guarda sus posiciones
		        try {
		            nuevaDisp.generarMatriz(numBotiquines, numCocodrilos);
		        } catch (IllegalArgumentException ex) {
		            JOptionPane.showMessageDialog(null,
		                ex.getMessage(),
		                "Error al generar matriz",
		                JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        modelo.insertarDisposicion(nuevaDisp);      // persiste Disposicion y asigna ID
		        nuevaDisp.guardarMatriz();                  // guarda cada casilla

		        new InterfazLaberinto(nuevaDisp.getMapa(), ancho, alto, jugador, modelo, tiempoPregunta, vidaBotiquines, dañoCocodrilos, dañoPregunta, idLab, idLab, ElegirLaberinto.this);

		        dispose();
		    }
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////  MÉTODOS
	/**
	 * Cargar los laberintos de la BD y meterlos en la tabla
	 */
	public void cargarLaberintos() {			//Cargar los laberintos de la BD y meterlos en la tabla
	    ResultSet rset = modelo.consultarDatos("laberintos");

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
	    btnJugar.setEnabled(false); // Desactivar botón Jugar
	    btnJugarNuevaDisp.setEnabled(false);
	    tableLaberintos.clearSelection();

	    tableLaberintos.clearSelection(); // Quitar selección

	    if (modeloDisposiciones.getRowCount() > 0) {
	        modeloDisposiciones.setRowCount(0); // Vaciar la tabla de disposiciones
	    }
	    
	    if (modeloLaberintos.getRowCount() > 0) {
	    	modeloLaberintos.setRowCount(0); // Vaciar la tabla de disposiciones
	    }
	}
}
