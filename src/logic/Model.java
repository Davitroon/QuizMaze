package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private static Connection connection;
    private String database = "laberinto25"; 
    private String login = "root";
    private String pwd = "Coco2006";
    private String url = "jdbc:mysql://localhost/" + database;
    
    public Model() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, login, pwd);
        System.out.println(" - Database connection established -");
    }
    
    public void deleteMaze(int mazeId) {
        String sql = "DELETE FROM laberintos WHERE id = ?";
        
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
        String query = "SELECT cord_x, cord_y, elemento FROM disposiciones_matriz WHERE id_disposicion = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, dispositionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int x = rs.getInt("cord_x");
                int y = rs.getInt("cord_y");
                int element = rs.getInt("elemento");
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
        String query = "SELECT * FROM disposiciones WHERE id_laberinto = ?";
        
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
        String query = "SELECT id, nombre, contraseña FROM usuarios WHERE nombre = ? AND contraseña = ?";
        
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
        String query = "INSERT INTO disposiciones (id_laberinto) VALUES (?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, disposition.getIdMaze());
            stmt.executeUpdate();
            
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                disposition.setId(generatedId);
            }
            stmt.close();
            
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    
    public void insertDispositionMatrix(int cordX, int cordY, int dispositionId, int element) {
        String query = "INSERT INTO disposiciones_matriz (cord_x, cord_y, id_disposicion, elemento) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, cordX);
            stmt.setInt(2, cordY);
            stmt.setInt(3, dispositionId);
            stmt.setInt(4, element);
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertMaze(Maze maze) {
        String query = "INSERT INTO laberintos (ancho, alto, num_cocodrilos, daño_cocodrilos, num_botiquines, vida_botiquines, tiempo_pregunta, daño_pregunta, num_preguntas) "
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
            
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                maze.setId(generatedId);
            }
            stmt.close();
            
        } catch (SQLException e) { 
            e.printStackTrace();
        }        
    }
    
    public void insertMatch(int userId, int mazeId, int dispositionId, boolean victory,
                            int hp, int correctQuestions, int wrongQuestions, int points, String time) {
        String query = "INSERT INTO partidas (id_usuario, id_laberinto, id_disposicion, victoria, vida, " +
                       "preguntas_correctas, preguntas_incorrectas, puntos, tiempo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, mazeId);
            stmt.setInt(3, dispositionId);
            stmt.setBoolean(4, victory);
            stmt.setInt(5, hp);
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
        String query = "INSERT INTO usuarios (nombre, contraseña) VALUES (?, ?)";
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
        String sql = "SELECT pregunta, respuesta, pista FROM preguntas";
        try (
            Connection conn = DriverManager.getConnection(url, login, pwd);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                questions.add(new Question(
                    rs.getString("pregunta"),
                    rs.getString("respuesta"),
                    rs.getString("pista")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    
    public ResultSet getTop10(int mazeId, int dispositionId) {
        String sql =
            "SELECT u.nombre AS usuario, p.puntos, p.tiempo, p.vida, p.victoria " +
            "FROM partidas p " +
            "JOIN usuarios u ON p.id_usuario = u.id " +
            "WHERE p.id_laberinto = ? AND p.id_disposicion = ? " +
            "ORDER BY p.puntos DESC " +
            "LIMIT 10";
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
