package logic;

import java.sql.ResultSet;
import java.util.List;

import model.Disposition;
import model.Maze;
import model.Question;
import model.User;

/**
 * Controller class that acts as a bridge between the logic layer and the database.
 * It wraps the DBConnector methods and provides higher-level access to database operations.
 */
public class DBController {

    private dao.DBConnector dbConnector;

    /**
     * Constructs a DBController with the given DBConnector.
     *
     * @param dbConnector the database connector instance
     */
    public DBController(dao.DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    /**
     * Retrieves all dispositions associated with a specific maze.
     *
     * @param mazeId the ID of the maze
     * @return a ResultSet containing the dispositions
     */
    public ResultSet queryDispositions(int mazeId) {
        return dbConnector.queryDispositions(mazeId);
    }

    /**
     * Loads the disposition matrix for a given disposition.
     *
     * @param dispositionId the ID of the disposition
     * @param width the width of the maze
     * @param height the height of the maze
     * @return a 2D integer array representing the disposition grid
     */
    public int[][] loadDispositionMatrix(int dispositionId, int width, int height) {
        return dbConnector.loadDispositionMatrix(dispositionId, width, height);
    }

    /**
     * Inserts a value into the disposition matrix at a specific coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param dispositionId the ID of the disposition
     * @param element the element to insert
     */
    public void insertDispositionMatrix(int x, int y, int dispositionId, Integer element) {
        dbConnector.insertDispositionMatrix(x, y, dispositionId, element);
    }

    /**
     * Inserts a new disposition into the database.
     *
     * @param newDisp the disposition object to insert
     */
    public void insertDisposition(Disposition newDisp) {
        dbConnector.insertDisposition(newDisp);
    }

    /**
     * Retrieves all questions from the database.
     *
     * @return a list of Question objects
     */
    public List<Question> getQuestions() {
        return dbConnector.getQuestions();
    }

    /**
     * Inserts a completed game record into the database.
     *
     * @param userId the ID of the user
     * @param mazeId the ID of the maze
     * @param dispositionId the ID of the disposition used
     * @param victory whether the user won the game
     * @param health remaining health points
     * @param correctAnswers number of correct answers
     * @param incorrectAnswers number of incorrect answers
     * @param points points earned
     * @param time the game duration as a string
     */
    public void insertGame(int userId, int mazeId, int dispositionId, boolean victory, int health,
                           int correctAnswers, int incorrectAnswers, int points, String time) {
        dbConnector.insertGame(userId, mazeId, dispositionId, victory, health, correctAnswers, incorrectAnswers, points, time);
    }

    /**
     * Queries all rows from a specific table.
     *
     * @param table the table name
     * @return a ResultSet containing all rows from the table
     * @throws Exception if the query fails
     */
    public ResultSet queryAll(String table) {
        try {
            return dbConnector.queryAll(table);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Inserts a new maze into the database.
     *
     * @param maze the Maze object to insert
     */
    public void insertMaze(Maze maze) {
        dbConnector.insertMaze(maze);
    }

    /**
     * Queries a user by username and password.
     *
     * @param username the username
     * @param password the password
     * @return a ResultSet containing the user record, if found
     */
    public ResultSet queryUser(String username, String password) {
        return dbConnector.queryUser(username, password);
    }

    /**
     * Inserts a new user into the database.
     *
     * @param newUser the username
     * @param newPassword the user's password
     * @return the created User object with ID assigned
     */
    public User insertUser(String newUser, String newPassword) {
        return dbConnector.insertUser(newUser, newPassword);
    }

    /**
     * Deletes a maze by its ID.
     *
     * @param mazeId the ID of the maze to delete
     */
    public void deleteMaze(int mazeId) {
        dbConnector.deleteMaze(mazeId);
    }

    /**
     * Retrieves the top 10 game results for a specific maze and disposition.
     *
     * @param mazeId the ID of the maze
     * @param dispositionId the ID of the disposition
     * @return a ResultSet containing the top 10 scores
     */
    public ResultSet getTop10(int mazeId, int dispositionId) {
        return dbConnector.getTop10(mazeId, dispositionId);
    }
}
