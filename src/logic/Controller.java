package logic;

import dao.DBConnector;
import model.User;

public class Controller {
	private DBController dbController;
	private UIController uiController;
	private User user;

	public void initialize(DBConnector dbConnector) {
		dbController = new DBController(dbConnector);
		uiController = new UIController();
		user = new User(null, 0);
		
		uiController.initialize(this);
	}

	public DBController getDbController() {
		return dbController;
	}

	public UIController getUiController() {
		return uiController;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}