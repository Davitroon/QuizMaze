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
	
	
	/**
	 * Método para consultar los datos de un usuario especifico, el usuario y contraseña debe coincidir.
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
	public void insertarUsuario(String usuario, String contrasena) {
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
	
	
	/**
	 * Método para agregar usuario a la base de datos.
	 * @param usuario Nombre del usuario
	 * @param contrasena Contraseña del usuario
	 */
	public void insertarLaberinto(Laberinto laberinto) {
		String consulta = "INSERT INTO laberintos (ancho, alto, num_cocodrilos, daño_cocodrilos, num_botiquines, vida_botiquines, tiempo_pregunta, daño_pregunta, num_preguntas) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, laberinto.getAncho());
            stmt.setInt(2, laberinto.getAlto());
            stmt.setInt(3, laberinto.getNumCocodrilos());
            stmt.setInt(4, laberinto.getDanoCocodrilos());
            stmt.setInt(5, laberinto.getNumBotiquines());
            stmt.setInt(6, laberinto.getVidaBotiquines());
            stmt.setInt(7, laberinto.getTiempoPregunta());
            stmt.setInt(8, laberinto.getDañoPregunta());
            stmt.setInt(9, laberinto.getNumPreguntas());
			stmt.executeUpdate();	
			
	        // Obtener la clave generada (id)
	        var rs = stmt.getGeneratedKeys();
	        if (rs.next()) {
	            int idGenerado = rs.getInt(1);
	            laberinto.setId(idGenerado); // Asignar al objeto
	        }
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Método para agregar una disposicion a la base de datos
	 * @param idLaberinto
	 */
	public void insertarDisposicion(Disposicion disposicion) {
		String consulta = "INSERT INTO disposiciones (id_laberinto) VALUES (?)";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, disposicion.getIdLaberinto());
			stmt.executeUpdate();
			
	        // Obtener la clave generada (id)
	        var rs = stmt.getGeneratedKeys();
	        if (rs.next()) {
	            int idGenerado = rs.getInt(1);
	            disposicion.setId(idGenerado); // Asignar al objeto
	        }
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Método para agregar la matriz de una disposición a la base de datos
	 * @param cordX Coordenada X
	 * @param cordY Coordenada Y
	 * @param idDisposicion Id de la disposición
	 * @param elemento Elemento que se encuentra en la casilla
	 */
    public void insertarDisposicionMatriz(int cordX, int cordY, int idDisposicion, int elemento) {
        String consulta = "INSERT INTO disposiciones_matriz (cord_x, cord_y, id_disposicion, elemento) VALUES (?, ?, ?, ?)";

        try {
        	PreparedStatement stmt = conexion.prepareStatement(consulta);

            stmt.setInt(1, cordX);
            stmt.setInt(2, cordY);
            stmt.setInt(3, idDisposicion);
            stmt.setInt(4, elemento);

            stmt.executeUpdate();
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	
	
}
