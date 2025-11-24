package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Disposition;
import model.Maze;
import model.Question;
import model.User;

public class DBConnector {
	private final Connection connection;

	public DBConnector(Connection connection) {
		this.connection = connection;
	}

	public void deleteMaze(int mazeId) {
		String sql = "DELETE FROM mazes WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, mazeId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int[][] loadDispositionMatrix(int dispositionId, int width, int height) {
		int[][] matrix = new int[height][width];
		String query = "SELECT x_coord, y_coord, element FROM disposition_grid WHERE disposition_id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, dispositionId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int x = rs.getInt("x_coord");
				int y = rs.getInt("y_coord");
				int element = rs.getInt("element");
				matrix[y][x] = element;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matrix;
	}

	public ResultSet queryData(String table, int id) {
		String query = "SELECT * FROM " + table + " WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet queryAll(String table) {
		String query = "SELECT * FROM " + table;
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet queryDispositions(int mazeId) {
		String query = "SELECT * FROM dispositions WHERE maze_id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, mazeId);
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet queryUser(String user, String password) {
		String query = "SELECT id, name, password FROM users WHERE name = ? AND password = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, user);
			stmt.setString(2, password);
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insertDisposition(Disposition disposition) {
		String query = "INSERT INTO dispositions (maze_id) VALUES (?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, disposition.getMazeId());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				disposition.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertDispositionMatrix(int coordX, int coordY, int dispositionId, int element) {
		String query = "INSERT INTO disposition_grid (x_coord, y_coord, disposition_id, element) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, coordX);
			stmt.setInt(2, coordY);
			stmt.setInt(3, dispositionId);
			stmt.setInt(4, element);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertMaze(Maze maze) {
		String query = "INSERT INTO mazes (width, height, num_crocodiles, crocodile_damage, num_medkits, medkit_heal, question_time, question_damage, num_questions) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, maze.getWidth());
			stmt.setInt(2, maze.getHeight());
			stmt.setInt(3, maze.getNumCrocodiles());
			stmt.setInt(4, maze.getCrocodileDamage());
			stmt.setInt(5, maze.getNumMedkits());
			stmt.setInt(6, maze.getMedkitHealth());
			stmt.setInt(7, maze.getQuestionTime());
			stmt.setInt(8, maze.getQuestionDamage());
			stmt.setInt(9, maze.getNumQuestions());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				maze.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertGame(int userId, int mazeId, int dispositionId, boolean victory, int health, int correctQuestions,
			int wrongQuestions, int points, String time) {
		String query = "INSERT INTO games (user_id, maze_id, disposition_id, victory, health, correct_questions, wrong_questions, points, time) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, mazeId);
			stmt.setInt(3, dispositionId);
			stmt.setBoolean(4, victory);
			stmt.setInt(5, health);
			stmt.setInt(6, correctQuestions);
			stmt.setInt(7, wrongQuestions);
			stmt.setInt(8, points);
			stmt.setString(9, time);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User insertUser(String userName, String password) {
		String query = "INSERT INTO users (name, password) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return new User(userName, rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Question> getQuestions() {
		List<Question> questions = new ArrayList<>();
		String sql = "SELECT question, answer, hint FROM questions";
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				questions.add(new Question(rs.getString("question"), rs.getString("answer"), rs.getString("hint")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}

	public ResultSet getTop10(int mazeId, int dispositionId) {
		String sql = "SELECT u.name AS username, m.points, m.time, m.health, m.victory FROM games m "
				+ "JOIN users u ON m.user_id = u.id " + "WHERE m.maze_id = ? AND m.disposition_id = ? "
				+ "ORDER BY m.points DESC " + "LIMIT 10";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, mazeId);
			stmt.setInt(2, dispositionId);
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
