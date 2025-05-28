package vista;

import java.awt.Color;	// Permitirá definir colores (ej: Color.WHITE, Color.RED)
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory; //Para crear bordes alrededor de componentes
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import logica.Jugador;
import logica.Modelo;
import logica.Pregunta;


public class InterfazLaberinto {
    private JFrame frame;
    private JPanel[] celdasGrid = new JPanel[9]; //Array de JPanels para representar la visión del jugador
    private int[][] matriz;
    private int ancho, alto;
    private Jugador jugador;
    private Modelo modelo;
    private final int idLaberinto;
    private final int idDisposicion;
    //
    
    private JLabel labelVida;
    private JLabel labelCronometro;
    private Timer cronometro; //!!!
    private int tiempoInicio;
    private int tiempoPregunta; // segundos, lo obtienes del laberinto/disposición
    
    private int vidaBotiquin;
    private int danoCocodrilo;
    private int danoPregunta;
    
    private List<Pregunta> preguntas;	// Le pasamos una lista de objetos Pregunta
    private int indicePregunta = 0;
    
    private int intentosFallidos = 0;
    private int correctas = 0;
    private int incorrectas = 0;
    private JLabel labelPuntos;
    
    private ElegirLaberinto elegirLaberinto;
    
    //// COnstructor añadido ya que debe recibir parametroint, haciendo que cada vista sea diferente
    public InterfazLaberinto(int[][] matriz, int ancho, int alto, Jugador jugador, Modelo modelo, int tiempoPregunta, int vidaBotiquin, 
    							int danoCocodrilo, int danoPregunta, int idLaberinto, int idDisposicion, ElegirLaberinto elegirLaberinto){
    		this.elegirLaberinto = elegirLaberinto;
    	    this.matriz = matriz;
    	    this.ancho = ancho;
    	    this.alto = alto;
    	    this.jugador = jugador;
    	    this.modelo = modelo;
    	    this.tiempoPregunta = tiempoPregunta;
    	    this.vidaBotiquin = vidaBotiquin;
    	    this.danoCocodrilo = danoCocodrilo;
    	    this.danoPregunta = danoPregunta;
    	    this.preguntas = modelo.obtenerPreguntas(); //!!!
    	    this.idLaberinto   = idLaberinto;
			this.idDisposicion = idDisposicion;
    	    Collections.shuffle(this.preguntas); // Con esto las preguntas saldrán de forma deosrdenada 
    	    // shuffle es un metodo de Collection (de donde herada nuestro List) que mezcla aleatoriamente los elementos de una lista
    	    initialize();
    	    actualizarVista();
    	    iniciarCronometro();
    	    frame.setVisible(true);
    	}


    /**
	 * Create the application.
	 */
    private void initialize() {
        frame = new JFrame("Laberinto");
        frame.setResizable(false);
        frame.setBounds(100, 100, 452, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Label de vida
        labelVida = new JLabel("Vida: " + jugador.getVida());
        labelVida.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelVida.setBounds(50, 10, 200, 30);
        frame.getContentPane().add(labelVida);

        // Label de cronómetro
        labelCronometro = new JLabel("Tiempo: 00:00");
        labelCronometro.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelCronometro.setBounds(250, 10, 150, 30);
        frame.getContentPane().add(labelCronometro);
        JPanel panelVistaJugador = new JPanel();
        panelVistaJugador.setBounds(50, 50, 350, 350);
        frame.getContentPane().add(panelVistaJugador);
        panelVistaJugador.setLayout(new GridLayout(3, 3, 5, 5));

        for (int i = 0; i < 9; i++) {        // Crear las 9 celdas y guardarlas en el array
            JPanel celda = new JPanel();
            celda.setBorder(BorderFactory.createLineBorder(Color.ORANGE)); 
            panelVistaJugador.add(celda);
            celdasGrid[i] = celda;
        }

        JButton btnARRIBA = new JButton("↑");
        btnARRIBA.setFont(new Font("Tahoma", Font.BOLD, 40));
        btnARRIBA.setBounds(180, 410, 75, 65);
        frame.getContentPane().add(btnARRIBA);

        JButton btnABAJO = new JButton("↓");
        btnABAJO.setFont(new Font("Tahoma", Font.BOLD, 40));
        btnABAJO.setBounds(180, 494, 75, 65);
        frame.getContentPane().add(btnABAJO);

        JButton btnIZQUIERDA = new JButton("←");
        btnIZQUIERDA.setFont(new Font("Tahoma", Font.BOLD, 40));
        btnIZQUIERDA.setBounds(90, 494, 75, 65);
        frame.getContentPane().add(btnIZQUIERDA);

        JButton btnDERECHA = new JButton("→");
        btnDERECHA.setFont(new Font("Tahoma", Font.BOLD, 40));
        btnDERECHA.setBounds(265, 494, 75, 65);
        frame.getContentPane().add(btnDERECHA);
        
        labelPuntos = new JLabel("Puntuación = ");
        labelPuntos.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelPuntos.setBounds(180, 590, 200, 30);
        frame.getContentPane().add(labelPuntos);

        // Listeners de movimiento				(se podria integrar funciones Lamda)
        btnARRIBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                intentarMover(0, -1);
            }
        });
        btnABAJO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                intentarMover(0, 1);
            }
        });
        btnIZQUIERDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                intentarMover(-1, 0);
            }
        });
        btnDERECHA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                intentarMover(1, 0);
            }
        });

    }

    private void intentarMover(int desplazamientoX, int desplazamientoY) {
        int destinoX = jugador.getX() + desplazamientoX;
        int destinoY = jugador.getY() + desplazamientoY;
        if (destinoX < 0 || destinoX >= ancho || destinoY < 0 || destinoY >= alto) {
            JOptionPane.showMessageDialog(frame, "No puedes salir del laberinto.");
            return;
        }
        if (matriz[destinoY][destinoX] == 3) {
            JOptionPane.showMessageDialog(frame, "¡Hay un muro!");
            return;
        }	
        ///////////////////////
        // Pregunta con tiempo límite
        if (mostrarPreguntaConTiempo()) {
            jugador.moverA(destinoX, destinoY);

            // Efectos de casilla
            if (matriz[destinoY][destinoX] == 1) { // Botiquín ACTUALIZADO
            	 int sobrante = jugador.curar(vidaBotiquin); // Recibe el sobrante
            	 
            	    if (sobrante > 0) {
            	        jugador.sumarPuntos(sobrante); // convertimos el sobrante en puntos
            	        JOptionPane.showMessageDialog(frame, "¡Has encontrado un botiquín! +" + (vidaBotiquin-sobrante) + " vida, +" + sobrante + " puntos");
            	    } else {
            	        JOptionPane.showMessageDialog(frame, "¡Has encontrado un botiquín! +" + vidaBotiquin + " vida");
            	    }
            	    labelPuntos.setText("Puntuación = " + jugador.getPuntos());
            	    
            	    matriz[destinoY][destinoX] = 0; // Elimina el botiquín
          
            } else if (matriz[destinoY][destinoX] == 2) { // Cocodrilo
                jugador.reducirVida(danoCocodrilo);
                JOptionPane.showMessageDialog(frame, "¡Un cocodrilo te ha mordido! -" + danoCocodrilo + " vida");
                matriz[destinoY][destinoX] = 0; // Elimina el cocodrilo
            }


            actualizarVista();

            // Comprobar si es la meta (esquina inferior derecha)
            if (destinoX == ancho - 1 && destinoY == alto - 1) { 
                cronometro.stop();
                JOptionPane.showMessageDialog(frame, "¡Has llegado a la meta!\nTiempo: " + labelCronometro.getText().replace("Tiempo: ", ""));
                mostrarResumen();
                frame.dispose();
            }
        }
        else {// Si la vida es 0 o menos tras fallar la pregunta, muestra el mensaje y termina la partida
            if (jugador.getVida() <= 0) {
                cronometro.stop();
                JOptionPane.showMessageDialog(frame, "¡Has perdido! Te has quedado sin vida.");
                mostrarResumen();
                frame.dispose();
            }
            // Si no, simplemente no avanza
        }
        
    }

    /*
     * Este método es fundamental.
     * Renderiza una ventana centrada en el jugador, mostrando las 9 celdas (3x3) alrededor de su posición,
     * con colores según el contenido del laberinto.
     */
    private void actualizarVista() {		
        int posicionJugadorX = jugador.getX();		//posición X JUGADOR
        int posicionJugadorY = jugador.getY();		//posición Y JUGADOR
        
        for (int desplazamientoY = -1; desplazamientoY <= 1; desplazamientoY++) {
            for (int desplazamientoX = -1; desplazamientoX <= 1; desplazamientoX++) {
                int x = posicionJugadorX + desplazamientoX;
                int y = posicionJugadorY + desplazamientoY;
                ///////
                int indiceEnArray = (desplazamientoY + 1) * 3 + (desplazamientoX + 1); //!!!!
                //Transforma la posición 2D (-1 a 1) a un índice 1D de 0 a 8 para acceder al celdasGrid[]
                ///////
                JPanel celda = celdasGrid[indiceEnArray];
                if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                    int elemento = matriz[y][x];
                    switch (elemento) {
                        case 0: celda.setBackground(Color.WHITE); break;     // Vacío
                        case 1: celda.setBackground(Color.GREEN); break;     // Botiquín
                        case 2: celda.setBackground(Color.RED); break;       // Cocodrilo
                        case 3: celda.setBackground(Color.GRAY); break;      // Muro
                        default: celda.setBackground(Color.LIGHT_GRAY); break;
                    }
                } else {
                    celda.setBackground(Color.BLACK); // Fuera de los límites
                }
            }
        }
        
        // La celda central es el jugador
        celdasGrid[4].setBackground(Color.YELLOW);
        labelVida.setText("Vida: " + jugador.getVida());
    }

    /**	Guardaremos el momento de inicio (tiempoInicio
     * 	LLamaremos al cronometro para que empiezew a contar
     */
    private void iniciarCronometro() {		
        tiempoInicio = (int) System.currentTimeMillis();
        cronometro = new Timer(1000, new java.awt.event.ActionListener() {// Creamos un Timer de Swing que ejecuta su acción cada 1000 ms (1 segundo)
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actualizarCronometro();
            }
        });

        cronometro.start();// Inicia el timer (empieza a contar)
    }
    /*	Calculamos cuantos segundos han pasado desde que empezó el juego
     * 	Actualizamos el texto del labelCronometro para mostrar el tiempo en formato MM:SS
     */
    private void actualizarCronometro() {
        int tiempoActual = (int) System.currentTimeMillis();		// Es necesario llamar a System para obtener la hora
        int segundos = (int) ((tiempoActual - tiempoInicio) / 1000);
        int min = (int) (segundos / 60); //	MIN COMPLETOS
        int seg = (int) (segundos % 60); // 	SEG RESTANTES
        
        // USO DE STRING BUILDER
        StringBuilder minStr = new StringBuilder();
        if (min < 10) minStr.append('0'); // Simplemente es para tener un formato 00:00 
        minStr.append(min);

        StringBuilder segStr = new StringBuilder();
        if (seg < 10) segStr.append('0');
        segStr.append(seg);

        labelCronometro.setText("Tiempo: " + minStr + ":" + segStr);
    }
    
    private boolean mostrarPreguntaConTiempo() {
        if (preguntas == null || preguntas.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay preguntas disponibles.");
            return false;
        }
        if (indicePregunta >= preguntas.size()) {
            indicePregunta = 0;
        }
        Pregunta p = preguntas.get(indicePregunta++);

        JTextField respuesta = new JTextField();
        JButton botonResponder = new JButton("Responder");
        final boolean[] resultado = {false};
        final int[] intentos = {0}; // Contador de intentos
        final JDialog dialog = new JDialog(frame, "Pregunta", true);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel(p.getEnunciado()));
        JLabel labelTiempo = new JLabel("Tiempo: " + tiempoPregunta + " segundos");
        panel.add(labelTiempo);
        panel.add(new JLabel("Pista: " + p.getPista()));
        panel.add(new JLabel("Respuesta:"));
        panel.add(respuesta);
        panel.add(botonResponder);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        Timer timer = new Timer(1000, null);
        final int[] tiempoRestante = {tiempoPregunta};
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempoRestante[0]--;
                labelTiempo.setText("Tiempo: " + tiempoRestante[0] + " segundos");
                if (tiempoRestante[0] <= 0) {
                    timer.stop();
                    dialog.dispose();
                }
            }
        });

        timer.start();

        // Acción para el botón
        botonResponder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String respuestaUsuario = respuesta.getText().trim();
                if (respuestaUsuario.equalsIgnoreCase(p.getRespuestaCorrecta())) {
                    resultado[0] = true;
                    timer.stop();
                    dialog.dispose();
                } else {
                    intentos[0]++;
                    if (intentos[0] >= 3) {
                        JOptionPane.showMessageDialog(dialog, "Has fallado 3 veces.");
                        timer.stop();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Respuesta incorrecta. Intento " + intentos[0] + " de 3.");
                    }
                }
            }
        });

        // Esta opción permite pulsar Enter para contestar
        respuesta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                botonResponder.doClick();
            }
        });

        dialog.setVisible(true);
        timer.stop();

        if (resultado[0]) {
            correctas++;
            jugador.sumarPuntos(10);
            labelPuntos.setText("Puntuación = " + jugador.getPuntos());
            JOptionPane.showMessageDialog(frame, "¡Correcto!");
        } else {
            incorrectas++;
            jugador.reducirVida(danoPregunta); 
            JOptionPane.showMessageDialog(frame, "¡Demasiadas respuestas incorrectas o tiempo agotado! Pierdes " + danoPregunta + " de vida.");
            actualizarVista();
        }

        return resultado[0];
    }


    
    
    private void mostrarResumen() {
        String tiempo = labelCronometro.getText().replace("Tiempo: ", "");
        boolean victoria = jugador.getVida() > 0;

        // Inserta la partida en la base de datos
        modelo.insertarPartida(
            jugador.getId(),
            this.idLaberinto,
            this.idDisposicion,
            victoria,
            jugador.getVida(),
            correctas,
            incorrectas,
            jugador.getPuntos(),
            tiempo
        );

        // Abre la ventana de resultados
        ResultadosLaberinto ventanaResultados = new ResultadosLaberinto(
    	    jugador.getNombre(),    // nombreUsuario
    	    correctas,              // preguntasCorrectas
    	    incorrectas,            // preguntasIncorrectas
    	    jugador.getVida(),      // vidaFinal
    	    jugador.getPuntos(),    // puntos
    	    tiempo,                 // tiempo en MM:SS
    	    victoria,               // boolean victoria
    	    modelo,                 // tu objeto Modelo
    	    idLaberinto,            // el int que recibiste al crear la vista
    	    idDisposicion,           // idem para disposición
    	    elegirLaberinto,
    	    matriz
        );
        ventanaResultados.setLocationRelativeTo(frame);  // Centrar sobre la ventana actual
        ventanaResultados.setVisible(true);

        // Cierra la ventana del laberinto
        frame.dispose();
    }



}
