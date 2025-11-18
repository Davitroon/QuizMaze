package logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
	private static Connection connection;
	private String database = "maze";
	private String login = "root";
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;

	public Model() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, login, pwd);
		System.out.println(" - Database connection established -");
	}

	public void deleteMaze(int mazeId) {
		String sql = "DELETE FROM mazes WHERE id = ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, mazeId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error deleting: " + e.getMessage());
		}
	}

	public int[][] loadDispositionMatrix(int dispositionId, int width, int height) {
		int[][] matrix = new int[height][width];
		String query = "SELECT coord_x, coord_y, element FROM disposition_grid WHERE disposition_id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, dispositionId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int x = rs.getInt("coord_x");
				int y = rs.getInt("coord_y");
				int element = rs.getInt("element");
				matrix[y][x] = element;
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return matrix;
	}

	public ResultSet queryData(String table, int id) {
		ResultSet rset = null;
		String query = "SELECT * FROM " + table + " WHERE id = ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}

	public ResultSet queryAll(String table) {
		ResultSet rset = null;
		String query = "SELECT * FROM " + table + ";";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}

	public ResultSet queryDispositions(int mazeId) {
		ResultSet rset = null;
		String query = "SELECT * FROM dispositions WHERE maze_id = ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, mazeId);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}

	public ResultSet queryUser(String user, String password) {
		ResultSet rset = null;
		String query = "SELECT id, name, password FROM users WHERE name = ? AND password = ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, user);
			stmt.setString(2, password);
			rset = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rset;
	}

	public void insertDisposition(Disposition disposition) {
		String query = "INSERT INTO dispositions (maze_id) VALUES (?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, disposition.getMazeId());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1);
				disposition.setId(generatedId);
			}
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertDispositionMatrix(int coordX, int coordY, int dispositionId, int element) {
		String query = "INSERT INTO disposition_grid (coord_x, coord_y, disposition_id, element) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, coordX);
			stmt.setInt(2, coordY);
			stmt.setInt(3, dispositionId);
			stmt.setInt(4, element);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertMaze(Maze maze) {
		String query = "INSERT INTO mazes (width, height, num_crocodiles, crocodile_damage, num_medkits, medkit_heal, question_time, question_damage, num_questions) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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
				int generatedId = rs.getInt(1);
				maze.setId(generatedId);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertGame(int userId, int mazeId, int dispositionId, boolean victory, int life, int correctQuestions,
			int wrongQuestions, int points, String time) {
		String query = "INSERT INTO matches (user_id, maze_id, disposition_id, victory, life, "
				+ "correct_questions, wrong_questions, points, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, userId);
			stmt.setInt(2, mazeId);
			stmt.setInt(3, dispositionId);
			stmt.setBoolean(4, victory);
			stmt.setInt(5, life);
			stmt.setInt(6, correctQuestions);
			stmt.setInt(7, wrongQuestions);
			stmt.setInt(8, points);
			stmt.setString(9, time);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertUser(String user, String password) {
		String query = "INSERT INTO users (name, password) VALUES (?, ?)";
		int generatedId = -1;

		try {
			PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user);
			stmt.setString(2, password);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedId;
	}

	public List<Question> getQuestions() {
		List<Question> questions = new ArrayList<>();
		String sql = "SELECT question, answer, hint FROM questions";
		try (Connection conn = DriverManager.getConnection(url, login, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				questions.add(new Question(rs.getString("question"), rs.getString("answer"), rs.getString("hint")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}

	public ResultSet getTop10(int mazeId, int dispositionId) {
		String sql = "SELECT u.name AS user, m.points, m.time, m.life, m.victory FROM matches m "
				+ "JOIN users u ON m.user_id = u.id "
				+ "WHERE m.maze_id = ? AND m.disposition_id = ? "
				+ "ORDER BY m.points DESC "
				+ "LIMIT 10";
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
