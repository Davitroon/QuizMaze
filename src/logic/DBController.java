package logic;

import java.sql.ResultSet;
import java.util.List;

import dao.DBConnector;
import model.Disposition;
import model.Maze;
import model.Question;

public class DBController {
	private dao.DBConnector dbConnector;

	public DBController(dao.DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public ResultSet queryDispositions(int valueAt) {
		return dbConnector.queryDispositions(valueAt);
	}

	public int[][] loadDispositionMatrix(int idDisposition, int xCoord, int yCoord) {
		return dbConnector.loadDispositionMatrix(idDisposition, xCoord, yCoord);
	}

	public void insertDispositionMatrix(int x, int y, int id, Integer valueOf) {
		dbConnector.insertDispositionMatrix(x, y, id, valueOf);
	}

	public void insertDisposition(Disposition newDisp) {
		dbConnector.insertDisposition(newDisp);
	}

	public List<Question> getQuestions() {
		return dbConnector.getQuestions();
	}

	public void insertGame(int id, int mazeId, int layoutId, boolean victory, int health, int correctAnswers,
			int incorrectAnswers, int points, String time) {
		dbConnector.insertGame(id, mazeId, layoutId, victory, health, correctAnswers, incorrectAnswers, points,
				time);
	}

	public ResultSet queryAll(String string) {
		try {
			ResultSet rset = dbConnector.queryAll(string);
			return rset;
		} catch (Exception e) {
			throw e;
		}
		
	}

	public void insertMaze(Maze maze) {
		dbConnector.insertMaze(maze);
		
	}

	public ResultSet queryUser(String username, String password) {

		ResultSet rset = dbConnector.queryUser(username, password);
		return rset;
	}

	public int insertUser(String newUser, String newPassword) {
		return dbConnector.insertUser(newUser, newPassword);
	}

	public void deleteMaze(int mazeId) {
		dbConnector.deleteMaze(mazeId);
		
	}
}