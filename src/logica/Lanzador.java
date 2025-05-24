package logica;

import java.sql.SQLException;

/**
 * Clase que instancia y crea el resto de las clases, funciona como lanzador de la aplicación.
 */
public class Lanzador {
	
	private static Modelo modelo;
	private static Login login;
	
	public static void main(String[] args) {
		
		try {
			modelo = new Modelo ();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Aqui habria que crear el resto de las clases
		login = new Login (modelo);
		login.logearse();
	}
}
