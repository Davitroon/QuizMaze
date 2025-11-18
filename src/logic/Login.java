package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ui.ChooseMazeUI;
import ui.MazeManagementUI;

public class Login {

	private static String nombreUsuario;
	
	private Model modelo;
	private ChooseMazeUI elegirLaberinto;
	private MazeManagementUI gestionLaberinto;
	private Player jugador;
	
	public Login(Model modelo) {
		this.modelo = modelo;
	}
	

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	
	public void logearse() {
		Scanner scn = new Scanner(System.in);
		boolean accesoConcedido = false;
		int opciones = 0;
		
		// Menú de opciones
		while (!accesoConcedido) {
			System.out.println("Eliga una opción: \n1 - Crear Usuario \n2 - Iniciar Sesión \n0 - Salir");
			if (scn.hasNextInt()) {
				opciones = scn.nextInt();
				scn.nextLine();
				
				switch (opciones) {
				// Crear usuario
				case 1:
					System.out.print("Nombre de usuario nuevo: ");
					String usuarioNuevo = scn.nextLine();

					if (!validarNombreUsuario(usuarioNuevo)) {
					    break; 
					}
					
					System.out.print("Contraseña: ");
					String contraseñaNueva = scn.nextLine();
					
					if (!validarContraseña(contraseñaNueva)) {
					    break; 
					}

					System.out.print("Repite la contraseña: ");
					String contraseñaRepetida = scn.nextLine();
					
					if (!validarContraseña(contraseñaRepetida)) {
					    break; 
					}
					
					if (!contraseñaRepetida.equals(contraseñaNueva)) {
						System.out.println("Las contraseñas no son iguales");
						
					} else {
						int idJugador = modelo.insertarUsuario(usuarioNuevo, contraseñaRepetida);

						if (idJugador != -1) {
						    jugador = new Player(usuarioNuevo); // Constructor existente
						    jugador.setId(idJugador);            // Asegúrate de tener este setter

						    System.out.println("Usuario " + usuarioNuevo + " creado con ID: " + idJugador);

						    if (elegirLaberinto == null) {
						        elegirLaberinto = new ChooseMazeUI(this, modelo, jugador);
						    }
						    elegirLaberinto.cargarLaberintos();
						    elegirLaberinto.setVisible(true);

						    accesoConcedido = true;
						} else {
						    System.out.println("Error al crear el usuario.");
						}
					}
				break;

				// Iniciar sesion
				case 2:
				    System.out.print("Usuario: ");
				    nombreUsuario = scn.nextLine();
				    
					if (!validarNombreUsuario(nombreUsuario)) {
					    break; 
					}

				    System.out.print("Contraseña: ");
				    String contraseña = scn.nextLine();
				    
					if (!validarContraseña(contraseña)) {
					    break; 
					}

				    ResultSet rs = modelo.consultarUsuario(nombreUsuario, contraseña);

				    try {
				        if (rs.next()) {
				            // Obtener id y nombre del usuario desde la consulta
				            int idUsuario = rs.getInt("id");
				            String usuario = rs.getString("nombre");

				            // Crear el objeto Jugador con nombre e id
				            jugador = new Player(usuario);
				            jugador.setId(idUsuario);  // Asegúrate de que Jugador tiene setId(int)

				            if (usuario.equals("admin")) {
				                if (gestionLaberinto == null) {
				                    gestionLaberinto = new MazeManagementUI(this, modelo);
				                }
				                gestionLaberinto.actualizarLaberintos();
				                gestionLaberinto.setVisible(true);

				            } else {
				                if (elegirLaberinto == null) {
				                    elegirLaberinto = new ChooseMazeUI(this, modelo, jugador);
				                }
				                elegirLaberinto.cargarLaberintos();
				                elegirLaberinto.setVisible(true);
				            }

				            accesoConcedido = true;

				        } else {
				            System.out.println("Usuario o contraseña incorrectos");
				        }

				    } catch (SQLException e) {
				        System.out.println("Error al iniciar sesión");
				        e.printStackTrace();
				    }
				    break;
				
				case 0:
					System.out.println("Saliendo del prograna...");
					System.exit(1);
					
				// Opción fuera del rango
				default:
					System.out.println("Error: Opción no válida.");
				}
				
			// Si el usuario no ha ingresado un numero en las opciones
			} else {
				System.out.println("Error: introduce un valor valido. ");
				scn.nextLine();
			}
		}
	}
	
	/**
	 * Valida la contraseña.
	 * @param contraseña contraseña a validar
	 * @return true si la contraseña es válida, false en caso contrario
	 */
	private boolean validarContraseña(String contraseña) {
	    if (contraseña == null || contraseña.length() < 4) {
	        System.out.println("La contraseña debe tener al menos 4 caracteres.");
	        return false;
	    }
	    return true;
	}

	/**
	 * Valida el nombre de usuario.
	 * @param nombre nombre de usuario a validar
	 * @return true si el nombre es válido, false en caso contrario
	 */
	private boolean validarNombreUsuario(String nombre) {
	    if (nombre == null || nombre.trim().isEmpty()) {
	        System.out.println("El nombre de usuario no puede estar vacío.");
	        return false;
	    }
	    if (!nombre.matches("^[a-zA-Z0-9_]{3,20}$")) {
	        System.out.println("El nombre de usuario debe tener entre 3 y 20 caracteres y solo puede contener letras, números y guiones bajos.");
	        return false;
	    }
	    return true;
	}
}