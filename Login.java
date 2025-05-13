import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
	
	private String database = "laberinto";		    
	private String login = "root"; 
	private String pwd = "Purruku_2006";
	private String url = "jdbc:mysql://localhost/" + database;
	private Connection conexion;
	private MenuAdmin interfazAdmin = new MenuAdmin(this);
	private MenuUsuario interfazUsuario = new MenuUsuario(this);
	
	public Login() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			System.out.println("-> Conexión con BD establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC No encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error al conectarse a la BD");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general de Conexión");
			e.printStackTrace();
		}
	}
	
	public void logearse() {
		Scanner scn = new Scanner(System.in);
		Boolean accesoConcedido = false;
		while(!accesoConcedido) {
			System.out.print("Usuario: ");
			String nombreUsuario = scn.nextLine();
			System.out.print("Contraseña: ");
			String contraseña = scn.nextLine();
			
			try {
				Statement stmnt = conexion.createStatement();
				String sql = "SELECT * FROM LOGIN WHERE USUARIO = ('" + nombreUsuario + "') AND CONTRASEÑA = ('" + contraseña + "')" ;
				ResultSet rs = stmnt.executeQuery(sql);
				
				if(rs.next()) {
					System.out.println("Has iniciado sesion como " + nombreUsuario);
					accesoConcedido = true;
				}else {
					System.out.println("Contraseña o usuario incorrectas");
					
				}
				
				if(nombreUsuario.equals("usuario") && contraseña.equals("user123")) {
					interfazUsuario.setVisible(true);
				} else if(nombreUsuario.equals("admin") && contraseña.equals("root")) {
					interfazAdmin.setVisible(true);
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Login acceder = new Login();
		acceder.logearse();
	}
}
