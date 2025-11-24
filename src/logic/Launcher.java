package logic;

import javax.swing.JOptionPane;

import dao.DBConnector;
import dao.DBInitializer;

/**
 * Class that instantiates and creates the rest of the classes, works as the
 * application launcher.
 */
public class Launcher {

	private static DBConnector dbConnector;
	private static Controller controller;
	private static DBInitializer dbInitializer;

	public static void main(String[] args) {
	    try {
	        dbInitializer = new DBInitializer();
	        dbConnector = new DBConnector(dbInitializer.getConnection());
	        
	        controller = new Controller();
	        controller.initialize(dbConnector);
	        controller.getUiController().getLoginUI().setVisible(true);
	        
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, 
	            "Failed to initialize database:\n" + e.getMessage(),
	            "Database Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	        System.exit(1);
	    }
	}

}
