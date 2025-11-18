package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Model;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResultsMazeUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Top 10
    private DefaultTableModel modeloEstadisticas;
    private JTable tablaEstadisticas;

    private Model modeloLogica;
    private int idLaberinto, idDisposicion;

    // Labels dinámicos
    private JLabel lblUsuarioValue;
    private JLabel lblPuntosValue;
    private JLabel lblVidaValue;
    private JLabel lblTiempoValue;
    
    private int[][] matriz;

    public ResultsMazeUI(String nombreUsuario, int preguntasCorrectas, int preguntasIncorrectas,
                               int vidaFinal, int puntos, String tiempo,
                               boolean victoria, Model modelo, int idLaberinto,
                               int idDisposicion, ChooseMazeUI elegirLaberinto, int[][] matriz) {
        this.modeloLogica   = modelo;
        this.idLaberinto    = idLaberinto;
        this.idDisposicion  = idDisposicion;
        this.matriz = matriz;

        setTitle("Resumen de la partida");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 486);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ——— Panel resumen partida (izquierda) ———
        JPanel panelResumen = new JPanel(null);
        panelResumen.setBounds(10, 58, 300, 300);
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen"));
        contentPane.add(panelResumen);

        JLabel lblResultado = new JLabel(victoria ? "¡Has ganado!" : "Has perdido");
        lblResultado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblResultado.setBounds(80, 20, 150, 25);
        panelResumen.add(lblResultado);
        
        // Etiquetas fijas
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 60, 80, 14);
        panelResumen.add(lblUsuario);
        lblUsuarioValue = new JLabel(nombreUsuario);
        lblUsuarioValue.setBounds(100, 60, 180, 14);
        panelResumen.add(lblUsuarioValue);

        JLabel lblPuntos = new JLabel("Puntos:");
        lblPuntos.setBounds(10, 90, 80, 14);
        panelResumen.add(lblPuntos);
        lblPuntosValue = new JLabel(String.valueOf(puntos));
        lblPuntosValue.setBounds(100, 90, 180, 14);
        panelResumen.add(lblPuntosValue);

        JLabel lblVida = new JLabel("Vida:");
        lblVida.setBounds(10, 120, 80, 14);
        panelResumen.add(lblVida);
        lblVidaValue = new JLabel(String.valueOf(vidaFinal));
        lblVidaValue.setBounds(100, 120, 180, 14);
        panelResumen.add(lblVidaValue);

        JLabel lblTiempo = new JLabel("Tiempo:");
        lblTiempo.setBounds(10, 150, 80, 14);
        panelResumen.add(lblTiempo);
        lblTiempoValue = new JLabel(tiempo);
        lblTiempoValue.setBounds(100, 150, 180, 14);
        panelResumen.add(lblTiempoValue);

        // Detalle de preguntas
        JLabel lblCorrectas = new JLabel("Preguntas correctas:");
        lblCorrectas.setBounds(10, 180, 150, 14);
        panelResumen.add(lblCorrectas);
        JTextField tfCorrectas = new JTextField(String.valueOf(preguntasCorrectas));
        tfCorrectas.setBounds(160, 180, 50, 20);
        tfCorrectas.setEditable(false);
        panelResumen.add(tfCorrectas);

        JLabel lblIncorrectas = new JLabel("Preguntas incorrectas:");
        lblIncorrectas.setBounds(10, 210, 150, 14);
        panelResumen.add(lblIncorrectas);
        JTextField tfIncorrectas = new JTextField(String.valueOf(preguntasIncorrectas));
        tfIncorrectas.setBounds(160, 210, 50, 20);
        tfIncorrectas.setEditable(false);
        panelResumen.add(tfIncorrectas);

        // ——— Panel estadísticas top 10 (derecha) ———
        JPanel panelStats = new JPanel(new BorderLayout());
        panelStats.setBounds(330, 10, 350, 370);
        panelStats.setBorder(BorderFactory.createTitledBorder("Top 10 Jugadores"));
        contentPane.add(panelStats);

        modeloEstadisticas = new DefaultTableModel(
            new Object[] { "Usuario", "Puntos", "Tiempo", "Vida", "Victoria" }, 0
        );
        tablaEstadisticas = new JTable(modeloEstadisticas);
        panelStats.add(new JScrollPane(tablaEstadisticas), BorderLayout.CENTER);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnVolver.setBackground(Color.GRAY);
        btnVolver.setBounds(10, 402, 99, 34);
        contentPane.add(btnVolver);
        
        JButton btnMostrar = new JButton("Mostrar disposición");
        btnMostrar.setBounds(119, 402, 147, 34);
        contentPane.add(btnMostrar);

        cargarEstadisticas();
        
        // Clicb boton volver
        btnVolver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		elegirLaberinto.reiniciarVentana();
        		elegirLaberinto.setVisible(true);
        	}
        });
        
        // Boton mostrar matriz
        btnMostrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		imprimirMatriz();
        	}
        });
    }

    private void cargarEstadisticas() {
        modeloEstadisticas.setRowCount(0);
        try (ResultSet rs = modeloLogica.obtenerTop10(idLaberinto, idDisposicion)) {
            while (rs != null && rs.next()) {
                modeloEstadisticas.addRow(new Object[] {
                    rs.getString("usuario"),
                    rs.getInt("puntos"),
                    rs.getString("tiempo"),
                    rs.getInt("vida"),
                    rs.getBoolean("victoria") ? "Sí" : "No"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al cargar estadísticas",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void imprimirMatriz() {
	    int filas = matriz.length;
	    int columnas = matriz[0].length;
	    
	    for (int y = 0; y < filas + 2; y++) {
	        for (int x = 0; x < columnas + 2; x++) {
	            // Bordes exteriores
	            if (y == 0 || y == filas + 1 || x == 0 || x == columnas + 1) {
	                System.out.print("#");  // muro borde
	            } else {
	                // Dentro del borde: mapa real
	                int mapaY = y - 1;
	                int mapaX = x - 1;
	                
	                // Si es la esquina superior izquierda del mapa (inicio)
	                if (mapaY == 0 && mapaX == 0) {
	                    System.out.print("O");
	                } 
	                // Si es la esquina inferior derecha del mapa (salida)
	                else if (mapaY == filas - 1 && mapaX == columnas - 1) {
	                    System.out.print("X");
	                } 
	                else {
	                    // Resto de celdas según mapa
	                    switch (matriz[mapaY][mapaX]) {
	                        case 0: System.out.print(" "); break;    // vacío
	                        case 1: System.out.print("B"); break;    // botiquín
	                        case 2: System.out.print("C"); break;    // cocodrilo
	                        case 3: System.out.print("#"); break;    // muro
	                        default: System.out.print("?"); break;   // otro
	                    }
	                }
	            }
	        }
	        System.out.println();
	    }
	}
}
