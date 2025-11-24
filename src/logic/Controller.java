package logic;

import dao.DBConnector;
import model.Disposition;
import model.Maze;
import model.Player;
import model.Question;
import model.Player;

public class Controller {
	private DBController dbController;
	private UIController uiController;
	private Player player;
	
	public void initialize(DBConnector dbConnector) {
		dbController = new DBController(dbConnector);
		uiController = new UIController();
		player = new Player(null);
		
		uiController.initialize(this);
	}

	public DBController getDbController() {
		return dbController;
	}

	public UIController getUiController() {
		return uiController;
	}

	public Player getPlayer() {
		return player;
	}
}