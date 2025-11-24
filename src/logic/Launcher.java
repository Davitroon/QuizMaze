package logic;

import java.sql.SQLException;

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

		dbInitializer = new DBInitializer();
		try {
			dbConnector = new DBConnector(dbInitializer.getConnection());

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Database driver not found.\n" + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to the database.\n" + e.getMessage(),
					"Database Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}

		if (dbConnector == null) {
			JOptionPane.showMessageDialog(null, "Database connection failed. Exiting.", "Database Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		controller = new Controller();
		controller.initialize(dbConnector);

		controller.getUiController().getLoginUI().setVisible(true);
	}
}
