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

/**
 * Database connector and data access object (DAO) class.
 * Provides methods to query, insert, and delete records for Mazes, Dispositions, Users, Questions, and Games.
 */
public class DBConnector {

    private final Connection connection;

    /**
     * Constructs a DBConnector using an existing JDBC connection.
     *
     * @param connection the JDBC connection to the database
     */
    public DBConnector(Connection connection) {
        this.connection = connection;
    }

    // ----------------------------
    // Maze-related methods
    // ----------------------------

    /**
     * Deletes a maze from the database by its ID.
     *
     * @param mazeId the ID of the maze to delete
     */
    public void deleteMaze(int mazeId) {
        String sql = "DELETE FROM mazes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mazeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new maze into the database.
     *
     * @param maze the Maze object to insert; its ID will be set after insertion
     */
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

    // ----------------------------
    // Disposition-related methods
    // ----------------------------

    /**
     * Inserts a disposition record into the database.
     *
     * @param disposition the Disposition object to insert; its ID will be set after insertion
     */
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

    /**
     * Inserts a cell into a disposition matrix in the database.
     *
     * @param coordX        the x-coordinate of the cell
     * @param coordY        the y-coordinate of the cell
     * @param dispositionId the ID of the disposition
     * @param element       the element type (0=empty, 1=medkit, 2=crocodile, 3=wall)
     */
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

    /**
     * Loads the matrix of a disposition from the database.
     *
     * @param dispositionId the ID of the disposition
     * @param width         matrix width
     * @param height        matrix height
     * @return 2D integer matrix representing the disposition
     */
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

    /**
     * Queries all dispositions for a given maze.
     *
     * @param mazeId the maze ID
     * @return ResultSet containing all dispositions
     */
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

    // ----------------------------
    // User-related methods
    // ----------------------------

    /**
     * Inserts a new user into the database.
     *
     * @param userName the user's name
     * @param password the user's password
     * @return the created User object with its generated ID
     */
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

    /**
     * Queries a user by name and password.
     *
     * @param user     username
     * @param password password
     * @return ResultSet containing the matching user
     */
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

    // ----------------------------
    // Question-related methods
    // ----------------------------

    /**
     * Retrieves all questions from the database.
     *
     * @return list of Question objects
     */
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT question, answer_a, answer_b, answer_c, answer_d, correct_answer FROM questions";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                List<String> options = List.of(
                        rs.getString("answer_a"),
                        rs.getString("answer_b"),
                        rs.getString("answer_c"),
                        rs.getString("answer_d")
                );
                int correctIndex = rs.getInt("correct_answer") - 1; // database uses 1-4
                questions.add(new Question(rs.getString("question"), options, correctIndex));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // ----------------------------
    // Game-related methods
    // ----------------------------

    /**
     * Inserts a new game result into the database.
     *
     * @param userId          ID of the user
     * @param mazeId          ID of the maze
     * @param dispositionId   ID of the disposition
     * @param victory         whether the user won
     * @param health          user's remaining health
     * @param correctQuestions number of correct answers
     * @param wrongQuestions   number of wrong answers
     * @param points          total points earned
     * @param time            time taken as string (e.g., "02:30")
     */
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

    /**
     * Retrieves the top 10 players for a maze and disposition based on points.
     *
     * @param mazeId        ID of the maze
     * @param dispositionId ID of the disposition
     * @return ResultSet of top 10 game results
     */
    public ResultSet getTop10(int mazeId, int dispositionId) {
        String sql = "SELECT u.name AS username, m.points, m.time, m.health, m.victory FROM games m "
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

    // ----------------------------
    // Generic query methods
    // ----------------------------

    /**
     * Queries a table by ID.
     *
     * @param table table name
     * @param id    ID value
     * @return ResultSet of the query
     */
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

    /**
     * Queries all records from a table.
     *
     * @param table table name
     * @return ResultSet of all records
     */
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
}
