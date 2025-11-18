package logic;

import java.sql.SQLException;

import dao.DBConnector;
import ui.LoginUI;

/**
 * Class that instantiates and creates the rest of the classes, works as the
 * application launcher.
 */
public class Launcher {

	private static DBConnector model;
	private static LoginUI loginUi;

	public static void main(String[] args) {

		try {
			model = new DBConnector();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Here the rest of the classes should be created
		loginUi = new LoginUI(model);
		loginUi.login();
	}
}
