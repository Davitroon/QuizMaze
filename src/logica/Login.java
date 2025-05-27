package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import vista.ElegirLaberinto;
import vista.GestionLaberinto;

public class Login {

	private static String nombreUsuario;
	
	private Modelo modelo;
	private ElegirLaberinto elegirLaberinto;
	private GestionLaberinto gestionLaberinto;
	
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

					System.out.print("Contraseña: ");
					String contraseñaNueva = scn.nextLine();

					System.out.print("Repite la contraseña: ");
					String contraseñaRepetida = scn.nextLine();

					if (!contraseñaRepetida.equals(contraseñaNueva)) {
						System.out.println("Las contraseñas no son iguales");
						
					} else {
						modelo.insertarUsuario(usuarioNuevo, contraseñaRepetida);
						System.out.println("Usuario " + usuarioNuevo + " creado.");
					}
				break;

				// Iniciar sesion
				case 2:
					System.out.print("Usuario: ");
					nombreUsuario = scn.nextLine();

					System.out.print("Contraseña: ");
					String contraseña = scn.nextLine();
					
					
					ResultSet rs = modelo.consultarUsuario(nombreUsuario, contraseña);
					
					try {
						if (rs.next()) {
							String usuario = rs.getString("nombre");					
							
							if (usuario.equals("admin")) {
								if (gestionLaberinto == null) {
									gestionLaberinto = new GestionLaberinto(this, modelo);
								}
								gestionLaberinto.actualizarLaberintos();
								gestionLaberinto.setVisible(true);
								
							} else {
								if (elegirLaberinto == null) {
									elegirLaberinto = new ElegirLaberinto(this, modelo);
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

	
	public Login(Modelo modelo) {
		this.modelo = modelo;
	}
}