package Logica;

import java.sql.*;
import java.util.Scanner;

import Vista.MenuAdmin;
import Vista.MenuUsuario;

public class Login {

	private String database = "laberinto25";
	private String login = "root";
	private String pwd = "Purruku_2006";
	private String url = "jdbc:mysql://localhost/" + database;
	private Connection conexion;
	private MenuAdmin interfazAdmin;
	private MenuUsuario interfazUsuario;
	private String nombreUsuario;

	public String getNombreUsuario() {
		return nombreUsuario;
	}

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
	}

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
	}

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

	public void logearse() {
		Scanner scn = new Scanner(System.in);
		boolean accesoConcedido = false;
		int opciones = 0;
		while (true) {
			System.out.println("Introduce lo que quieres hacer: 1.Crear Usuario 2.Iniciar Sesión");
			if (scn.hasNextInt()) {
				opciones = scn.nextInt();
				scn.nextLine();
				break;
			} else {
				System.out.println("Error, introduce un valor valido: ");
				scn.nextLine();
			}
		}
			while (!accesoConcedido) {
				switch (opciones) {
				case 1:
					System.out.print("Nombre de usuario nuevo: ");
					String usuarioNuevo = scn.nextLine();

					System.out.print("Contraseña: ");
					String contraseñaNueva = scn.nextLine();

					System.out.print("Repite la contraseña: ");
					String contraseñaRepetida = scn.nextLine();

					if (!contraseñaRepetida.equals(contraseñaNueva)) {
						System.out.println("Las contraseñas no son iguales");
						break;
					}

					try {
						String sql = "INSERT INTO USUARIOS (USUARIO, CONTRASEÑA) VALUES (?, ?)";
						PreparedStatement stmt = conexion.prepareStatement(sql);
						stmt.setString(1, usuarioNuevo);
						stmt.setString(2, contraseñaNueva);
						stmt.executeUpdate();
						System.out.println("Usuario creado correctamente");
					} catch (Exception e) {
						System.out.println("Error al crear usuario");
					}
					return;

				case 2:
					System.out.print("Usuario: ");
					nombreUsuario = scn.nextLine();

					System.out.print("Contraseña: ");
					String contraseña = scn.nextLine();

					ResultSet rs = consultarUsuario(nombreUsuario, contraseña);

					try {
						if (rs != null && rs.next()) {
							System.out.println("Has iniciado sesión como " + nombreUsuario);
							accesoConcedido = true;

							if (nombreUsuario.equals("admin") && contraseña.equals("root")) {
								interfazAdmin = new MenuAdmin(this);
								interfazAdmin.setVisible(true);
							} else {
								interfazUsuario = new MenuUsuario(this);
								interfazUsuario.setVisible(true);
							}
						} else {
							System.out.println("Usuario o contraseña incorrectos");
						}
					} catch (Exception e) {
						System.out.println("Error al iniciar sesión");
						e.printStackTrace();
					}
					break;
				}
			}
		}

	public static void main(String[] args) {
		Login acceder = new Login();
		acceder.logearse();
	}
}

