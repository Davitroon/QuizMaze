package logic;

import java.sql.SQLException;

import dao.DBConnector;
import ui.LoginUI;

/**
 * Class that instantiates and creates the rest of the classes, works as the
 * application launcher.
 */
public class Launcher {

	private static DBConnector dbConnector;
	private static Controller controller;

	public static void main(String[] args) {

		try {
			dbConnector = new DBConnector();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		controller = new Controller();
		controller.initialize(dbConnector);

		controller.getUiController().getLoginUI().setVisible(true);
	}
}
