package logica;

import java.sql.*;
import java.util.Scanner;

import Vista.MenuAdmin;
import Vista.MenuUsuario;

public class Login {

	private String database = "laberinto25";
	private String login = "root";
	private String pwd = "Coco2006"; // Necesario cambiar la contraseña aqui al de tu SQL
	private String url = "jdbc:mysql://localhost/" + database;
	private Connection conexion;
	private MenuAdmin interfazAdmin;
	private MenuUsuario interfazUsuario;
	private static String nombreUsuario;
	
	private Modelo modelo;

	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	/* - Esta conexión ya se esta haciendo en la clase Modelo -
	public Login() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			System.out.println("-> Conexión con BD establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC No encontrado");
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			System.out.println("Error al conectarse a la BD");
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Error general de Conexión");
			e.printStackTrace();
			System.exit(0);
		}
	}*/
	
	/* - Método en Modelo -
	public ResultSet consultarDato(String tabla, int id) {
		ResultSet rset = null;
		String consulta = "SELECT * FROM " + tabla + " WHERE id = ?";

		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}*/

	/* - Método en modelo -
	public ResultSet consultarUsuario(String usuario, String contrasena) {
		ResultSet rset = null;
		String consulta = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";

		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setString(1, usuario);
			stmt.setString(2, contrasena);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}
	*/
	

	public void logearse() {
		Scanner scn = new Scanner(System.in);
		boolean accesoConcedido = false;
		int opciones = 0;
		
		// Menú de opciones
		while (!accesoConcedido) {
			System.out.println("Eliga una opción: \n1 - Crear Usuario \n2 - Iniciar Sesión");
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
							String usuario = rs.getString(1);
							
							if (usuario.equals("admin")) {
								if (interfazAdmin == null) {
									interfazAdmin = new MenuAdmin(Login.this);
								}
								
								interfazAdmin.setVisible(true);
								accesoConcedido = true;
							} else {
								if (interfazUsuario == null) {
									interfazUsuario = new MenuUsuario(Login.this);
								}
								
								interfazUsuario.setVisible(true);
								accesoConcedido = true;
							}
							
						} else {
							System.out.println("Usuario o contraseña incorrectos");
						}
						
					} catch (SQLException e) {
						System.out.println("Error al iniciar sesión");
						e.printStackTrace();
					}
				break;
					
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

	
	public static void main(String[] args) {
		Login login = new Login();
		login.logearse();
	}

	
	public Login() {
		try {
			modelo = new Modelo();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}