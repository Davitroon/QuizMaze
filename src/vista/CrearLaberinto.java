package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logica.Disposicion;
import logica.Laberinto;
import logica.Modelo;
public class CrearLaberinto {

	private JFrame frame;
	private JButton btnCrear;
	private JTable tablaLaberinto;
	private JSpinner spinnerAltura;
	private JSpinner spinnerAnchura;
	private JSpinner spinnerNumCocodrilos;
	private JSpinner spinnerNumBotiquines;
	private JSlider sliderDanoCocodrilo;
	private JSlider sliderCuraBotiquin;
	private JSpinner spinnerTiempoPregunta;
	private JSlider sliderDanoPregunta;
	private JScrollPane scrollPaneTABLALABERINTO;
	
	private MenuAdmin menuAdmin;
	private Laberinto laberinto;
	private Modelo modelo;
	private Disposicion disposicion;

	/**
	 * Create the application.
	 * @param menuAdmin 
	 */
	public CrearLaberinto(MenuAdmin menuAdmin, Modelo modelo) {
		initialize();
		this.menuAdmin = menuAdmin;
		this.modelo = modelo;
	}	

	public JFrame getFrame() {
		return frame;
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	    // Asignacion variables
	    int valorMINTiempoPrg = 5;
		int valorMAXTiempoPrg = 45;
		
		int valorMAXBotiquines = 10;
		int valorMAXCocodrilos = 10;
		
		int valorMINAltura = 3;
		int valorMAXAltura = 10;
		int valorMINAnchura = 3;
		int valorMAXAnchura = 10;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_IdLaberinto = new JLabel("LABERINTO Nº");
		lbl_IdLaberinto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_IdLaberinto.setBounds(10, 10, 152, 24);
		frame.getContentPane().add(lbl_IdLaberinto);
		
		JLabel lbl_AltoLaberinto = new JLabel("Alto =");
		lbl_AltoLaberinto.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_AltoLaberinto.setBounds(10, 64, 74, 24);
		frame.getContentPane().add(lbl_AltoLaberinto);
		
		JLabel lbl_AnchoLaberinto = new JLabel("Ancho =");
		lbl_AnchoLaberinto.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_AnchoLaberinto.setBounds(10, 98, 74, 24);
		frame.getContentPane().add(lbl_AnchoLaberinto);
		
		JLabel lbl_NumCocodrilos = new JLabel("Número de Cocodrilos =");
		lbl_NumCocodrilos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NumCocodrilos.setBounds(10, 145, 182, 24);
		frame.getContentPane().add(lbl_NumCocodrilos);
		
		JLabel lbl_NumBotiquines = new JLabel("Número de Botiquines=");
		lbl_NumBotiquines.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NumBotiquines.setBounds(10, 176, 182, 24);
		frame.getContentPane().add(lbl_NumBotiquines);
		
		JLabel lbl_DanoCocodrilo = new JLabel("Daño Cocodrilo = 25 HP");
		lbl_DanoCocodrilo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_DanoCocodrilo.setBounds(10, 225, 182, 24);
		frame.getContentPane().add(lbl_DanoCocodrilo);
		
		JLabel lbl_CuraBotiquin = new JLabel("Cura =");
		lbl_CuraBotiquin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_CuraBotiquin.setBounds(243, 225, 128, 24);
		frame.getContentPane().add(lbl_CuraBotiquin);
		
		JLabel lbl_TiempoPregunta_1 = new JLabel("segundos");
	    lbl_TiempoPregunta_1.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lbl_TiempoPregunta_1.setBounds(275, 311, 182, 24);
	    frame.getContentPane().add(lbl_TiempoPregunta_1);
		    
		JLabel lbl_DanoPregunta = new JLabel("Daño Pregunta =");
	    lbl_DanoPregunta.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lbl_DanoPregunta.setBounds(446, 225, 182, 24);
	    frame.getContentPane().add(lbl_DanoPregunta);
	    	    
	    JLabel lbl_TiempoPregunta = new JLabel("Tiempo por Pregunta =");
	    lbl_TiempoPregunta.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lbl_TiempoPregunta.setBounds(10, 311, 182, 24);
	    frame.getContentPane().add(lbl_TiempoPregunta);	 
		
		SpinnerNumberModel modeloTiempoPregunta = new SpinnerNumberModel(valorMINTiempoPrg, valorMINTiempoPrg, valorMAXTiempoPrg, 5);
	    spinnerTiempoPregunta = new JSpinner(modeloTiempoPregunta);
	    spinnerTiempoPregunta.setBounds(202, 313, 63, 25);
	    frame.getContentPane().add(spinnerTiempoPregunta);
		// % DAÑO COCODRILO ----------------------------------------
	    sliderDanoCocodrilo = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderDanoCocodrilo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_DanoCocodrilo.setText("Daño Cocodrilo = "+sliderDanoCocodrilo.getValue()+" HP");
			}
		});
		sliderDanoCocodrilo.setValue(25);
		sliderDanoCocodrilo.setSnapToTicks(true);
		sliderDanoCocodrilo.setMinorTickSpacing(5);
		sliderDanoCocodrilo.setPaintTicks(true); 
		sliderDanoCocodrilo.setBounds(10, 266, 200, 26);
		frame.getContentPane().add(sliderDanoCocodrilo);
		// % CURA BOTIQUÍN -----------------------------------------
		sliderCuraBotiquin = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderCuraBotiquin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_CuraBotiquin.setText("Cura = "+sliderCuraBotiquin.getValue()+" HP");
			}
		});
		sliderCuraBotiquin.setValue(10);
		sliderCuraBotiquin.setSnapToTicks(true);
		sliderCuraBotiquin.setMinorTickSpacing(5);
		sliderCuraBotiquin.setPaintTicks(true); 
		sliderCuraBotiquin.setBounds(236, 266, 200, 26);
		frame.getContentPane().add(sliderCuraBotiquin);
		// DAÑO PREGUNTA -------------------------------------------
		sliderDanoPregunta = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderDanoPregunta.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_DanoPregunta.setText("Daño Pregunta = "+sliderDanoPregunta.getValue()+" HP");
			}
		});
	    sliderDanoPregunta.setValue(10);
	    sliderDanoPregunta.setSnapToTicks(true);
	    sliderDanoPregunta.setMinorTickSpacing(5);     // 🔹 Avance de 5 en 5
	    sliderDanoPregunta.setPaintTicks(true);        
	    sliderDanoPregunta.setBounds(446, 266, 200, 26);
	    frame.getContentPane().add(sliderDanoPregunta);
		//.............................

		SpinnerModel valorBotiquin = new SpinnerNumberModel(1, 0, valorMAXBotiquines, 1); //
		SpinnerModel valorCocodrilo = new SpinnerNumberModel(2, 0, valorMAXCocodrilos, 1); //
		// CANTIDAD BOTIQUINES -------------------------------------------
		spinnerNumBotiquines = new JSpinner(valorBotiquin);
		spinnerNumBotiquines.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerNumBotiquines.setBounds(202, 178, 40, 25);
		frame.getContentPane().add(spinnerNumBotiquines);
		
		// CANTIDAD COCODRILOS -------------------------------------------
		spinnerNumCocodrilos = new JSpinner(valorCocodrilo);
		spinnerNumCocodrilos.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerNumCocodrilos.setBounds(202, 147, 40, 25);
		//
		frame.getContentPane().add(spinnerNumCocodrilos);
		///////////////////////// VARIABLES QUE ASIGNAN LOS LIMITES PERMITIDOS DE ANCHO Y ALTO

		/////////////////////////	
		
		//.............
		SpinnerNumberModel modeloAltura = new SpinnerNumberModel(valorMINAltura, valorMINAltura, valorMAXAltura, 1);
		// ALTURA LABERINTO -------------------------------------------------
		spinnerAltura = new JSpinner(modeloAltura);
		spinnerAltura.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerAltura.setBounds(94, 64, 40, 25);
		frame.getContentPane().add(spinnerAltura);

		//.............		
		SpinnerNumberModel modeloAnchura = new SpinnerNumberModel(valorMINAnchura, valorMINAnchura, valorMAXAnchura, 1);
		// ANCHURA LABERINTO -------------------------------------------------
		spinnerAnchura = new JSpinner(modeloAnchura);
		spinnerAnchura.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerAnchura.setBounds(94, 98, 40, 25);
		frame.getContentPane().add(spinnerAnchura);
		
		scrollPaneTABLALABERINTO = new JScrollPane();
		scrollPaneTABLALABERINTO.setBounds(327, 47, 163, 163);
		frame.getContentPane().add(scrollPaneTABLALABERINTO);
		
		///////////////////// CREACION DE LA TABLA LABERINTO
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnActualizar.setBounds(155, 66, 110, 53); 
		frame.getContentPane().add(btnActualizar);
		//
		btnActualizar.addActionListener(e -> {
			btnCrear.setEnabled(true);  // Mostrar botón CREAR cuando se pulsa Actualizar

		    int filas = (Integer) spinnerAltura.getValue();  // Alto
		    int columnas = (Integer) spinnerAnchura.getValue();       // Ancho

		    // Crear datos vacíos y cabeceras
		    String[] columnasTabla = new String[columnas];
		    for (int i = 0; i < columnas; i++) {
		        columnasTabla[i] = "Col " + (i + 1);
		    }

		    String[][] datosTabla = new String[filas][columnas];
		    for (int i = 0; i < filas; i++) {
		        for (int j = 0; j < columnas; j++) {
		            datosTabla[i][j] = ""; // Celdas vacías inicialmente
		        }
		    }

		    tablaLaberinto = new JTable(datosTabla, columnasTabla) {
		    	
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return false; // DESACTIVA QUE SEA EDITABLE POR DOBLE CLICK O TECLADO
		            // Evita que el administrador pueda meter otros datos que no sean muros
		            // IDEA = (desactivar para que el administrador pueda meter manusalmente botiquines y cocodrilos)
		        }
		    };
		    tablaLaberinto.setTableHeader(null); // Oculta encabezados
		    scrollPaneTABLALABERINTO.setViewportView(tablaLaberinto); // Mostrar en el scroll pane	
		    ///////////////////////////////////////// Mouse listener
		    tablaLaberinto.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = tablaLaberinto.rowAtPoint(evt.getPoint());
		            int col = tablaLaberinto.columnAtPoint(evt.getPoint());
		            //
		            int ultimaFila = tablaLaberinto.getRowCount() - 1;
		            int ultimaCol = tablaLaberinto.getColumnCount() - 1;
		            // No permitir clic en (0,0) ni en (ultimaFila, ultimaCol) !!
		            if ((row == 0 && col == 0) || (row == ultimaFila && col == ultimaCol)) {
		                return; // No hacer nada si es una de las celdas bloqueadas
		            }
		            //----------------------------------------------------
		            Object valor = tablaLaberinto.getValueAt(row, col);
		            if (valor == null || valor.toString().trim().isEmpty()) {
		                tablaLaberinto.setValueAt("3", row, col);
		            } else if (valor.toString().trim().equals("3")) {
		                tablaLaberinto.setValueAt("", row, col);
		            }
		        }
		    });///////////////////////////////////////// Mouse listener
		});////////////////////////////// Boton Actualizar
		
		btnCrear = new JButton("CREAR");
		btnCrear.setEnabled(false); 
	    btnCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnCrear.setBounds(518, 95, 110, 53);
	    frame.getContentPane().add(btnCrear);
	    
	    JButton btnVolver = new JButton("VOLVER");
	    btnVolver.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnVolver.setBounds(546, 311, 100, 33);
	    frame.getContentPane().add(btnVolver);
	    
	    JLabel lblAviso = new JLabel("* El \"3\" representa un Muro ");
	    lblAviso.setToolTipText("");
	    lblAviso.setFont(new Font("Tahoma", Font.BOLD, 12));
	    lblAviso.setBounds(327, 8, 182, 32);
	    frame.getContentPane().add(lblAviso);

	    // Clic boton volver
	    btnVolver.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.setVisible(false);
	    		menuAdmin.setVisible(true);
	    	}
	    });
	    
	    // Clic boton crear
	    btnCrear.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int ancho = (int) spinnerAnchura.getValue();
	    		int alto = (int) spinnerAltura.getValue();
	    		int num_cocodrilos = (int) spinnerNumCocodrilos.getValue();
	    		int dano_cocodrilos =  sliderDanoCocodrilo.getValue();
	    		int num_botiquines = (int) spinnerNumBotiquines.getValue();
	    		int vida_botiquines = sliderCuraBotiquin.getValue();
	    		int tiempo_pregunta = (int) spinnerTiempoPregunta.getValue();
	    		int dano_pregunta = sliderDanoPregunta.getValue();
	    		int num_preguntas = 20;
	    		
	    		laberinto = new Laberinto(ancho, alto, num_cocodrilos, dano_cocodrilos, num_botiquines,
	    				vida_botiquines, tiempo_pregunta, dano_pregunta, num_preguntas);
	    		modelo.insertarLaberinto(laberinto);
	    		
	    		disposicion = new Disposicion(laberinto.getMapa(), laberinto.getId(), modelo);	    		
	    		modelo.insertarDisposicion(disposicion);
	    		
	    		disposicion.generarMatriz(num_botiquines, num_cocodrilos);
	    		disposicion.guardarMatriz();
	    	}
	    });
	}	
}