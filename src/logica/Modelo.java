
package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para conectar con la base de datos y gestionarla.
 * @author David Forero
 */
public class Modelo {
	private static Connection conexion;
	private String database = "laberinto25"; 
	private String login = "root";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + database;
	
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
	 * Método para consultar todos los datos de una tabla de la BD.
	 * @param tabla Nombre de la tabla a consultar.
	 * @return Resultado de la consulta SQL.
	 */
	public ResultSet consultarDatos(String tabla) {
		
		ResultSet rset = null;
		String consulta = "SELECT * FROM " + tabla + ";";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
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
		String consulta = "SELECT nombre, contraseña FROM usuarios WHERE nombre = ? AND contraseña = ?";
		
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
	        stmt.close();
	        
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
            stmt.close();
            
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
	        stmt.close();
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
	}
	
	

	/**
	 * Método para agregar usuario a la base de datos.
	 * @param usuario Nombre del usuario
	 * @param contrasena Contraseña del usuario
	 */
	public void insertarUsuario(String usuario, String contrasena) {
		String consulta = "INSERT INTO usuarios (nombre, contraseña) VALUES (?, ?)";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setString(1, usuario);
			stmt.setString(2, contrasena);
			stmt.executeUpdate();	
			stmt.close();
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
	}
	
	
	public ResultSet consultarDisposiciones(int idLaberinto) {
		ResultSet rset = null;
		String consulta = "SELECT * FROM disposiciones WHERE id_laberinto = ?";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, idLaberinto);
			rset = stmt.executeQuery();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return rset;
	}
	
	
	public void borrarLaberinto(int idLaberinto) {
	    String sql = "DELETE FROM laberintos WHERE id = ?";
	    
	    try {
	        PreparedStatement stmt = conexion.prepareStatement(sql);
	        stmt.setInt(1, idLaberinto);	        
	        stmt.executeUpdate();
	        
	        stmt.close();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al borrar: " + e.getMessage());
	    }
	}
	
	////////// Método que carga la matriz de una disposición específica desde la BD.
	////////// Este método reconstruye la matriz bidimensional que representa una disposición
	////////// del laberinto, utilizando los datos almacenados en la tabla 'disposiciones_matriz'.
	 
	public int[][] cargarMatrizDisposicion(int idDisposicion, int ancho, int alto) {
	    int[][] matriz = new int[alto][ancho];
	    String consulta = "SELECT cord_x, cord_y, elemento FROM disposiciones_matriz WHERE id_disposicion = ?"; // Consulta SQL para obtener las coordenadas y el elemento correspondiente a la disposición
	    try {
	        PreparedStatement stmt = conexion.prepareStatement(consulta);
	        stmt.setInt(1, idDisposicion);
	        ResultSet rs = stmt.executeQuery();
	        //
	        while (rs.next()) {	// Se recorre el resultado fila por fila
	            int x = rs.getInt("cord_x");
	            int y = rs.getInt("cord_y");
	            int elemento = rs.getInt("elemento");
	            matriz[y][x] = elemento;
	        }
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return matriz;
	}
	
	

	public List<Pregunta> obtenerPreguntas() {
	    List<Pregunta> preguntas = new ArrayList<>();
	    String sql = "SELECT pregunta, respuesta, pista FROM preguntas";
	    try (
	        Connection conn = DriverManager.getConnection(url, login, pwd);
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()
	    ) {
	        while (rs.next()) {
	            preguntas.add(new Pregunta(
	                rs.getString("pregunta"),
	                rs.getString("respuesta"),
	                rs.getString("pista")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return preguntas;
	}

}
