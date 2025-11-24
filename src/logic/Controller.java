package logic;

import dao.DBConnector;
import model.User;

/**
 * Central controller for the application that manages the database controller,
 * UI controller, and the currently logged-in user.
 */
public class Controller {

    /**
     * Controller responsible for database operations.
     */
    private DBController dbController;

    /**
     * Controller responsible for user interface operations.
     */
    private UIController uiController;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Initializes the controller with the given database connector.
     *
     * @param dbConnector the database connector to be used by the DBController
     */
    public void initialize(DBConnector dbConnector) {
        dbController = new DBController(dbConnector);
        uiController = new UIController();
        user = new User(null, 0);

        uiController.initialize(this);
    }

    // ----------------------------
    // Getters and Setters
    // ----------------------------

    /**
     * Returns the database controller.
     *
     * @return the DBController instance
     */
    public DBController getDbController() {
        return dbController;
    }

    /**
     * Returns the UI controller.
     *
     * @return the UIController instance
     */
    public UIController getUiController() {
        return uiController;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param user the User object to set as the current user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
