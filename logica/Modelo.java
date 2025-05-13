package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para conectar con la base de datos y gestionarla.
 * @author David Forero
 */
public class Modelo {
	private String database = "laberinto25";
	private String login = "root"; 
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;
	private static Connection conexion;
	
	public Modelo() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion=DriverManager.getConnection(url,login,pwd);
		System.out.println (" - Conexión con BD establecida -");
	}
	
	/**
	 * Método para consultar un dato único a la base de datos.
	 * @param tabla Nombre de la tabla a consultar
	 * @param id Id del registro a buscar
	 * @return Resultado de la consulta SQL.
	 */
	public ResultSet consultarDato(String tabla, int id) {
		
		ResultSet rset = null;
		String consulta = "SELECT * FROM " + tabla + "WHERE id = ?";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rset;
	}
	
	
	/**
	 * Método para consultar los datos de un usuario especifico.
	 * @param usuario Nombre del usuario
	 * @param contrasena Contraseña del usuario
	 * @return Resultado de la consulta SQL. null si el dato no existe.
	 */
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
	
	
	/**
	 * Método para agregar usuario a la base de datos.
	 * @param usuario Nombre del usuario
	 * @param contrasena Contraseña del usuario
	 */
	public void agregarUsuario(String usuario, String contrasena) {
		String consulta = "INSERT INTO usuarios (usuario, contraseña) VALUES (?, ?)";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setString(1, usuario);
			stmt.setString(2, contrasena);
			stmt.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
