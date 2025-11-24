package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that initializes the SQLite database and executes the SQL installation
 * script directly in Java only if the database file does not exist.
 */
public class DBInitializer {

    private static final String DB_FILE = "quiz_maze.db";
    private static Connection connection;

    /**
     * Initializes the database and runs the installation script if the database
     * file does not exist.
     */
    public DBInitializer() {
        try {
            boolean dbExists = new File(DB_FILE).exists();
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
            System.out.println("SQLite database connection established.");

            if (!dbExists) {
                System.out.println("Database not found. Installing...");
                runInstallScript();
            } else {
                System.out.println("Database already exists. Skipping installation.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the SQL installation script directly in Java.
     */
    private void runInstallScript() {
        String[] sqlStatements = new String[] {
            // Create users table
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(25) UNIQUE NOT NULL,
                password VARCHAR(25) NOT NULL
            );
            """,

            // Create mazes table
            """
            CREATE TABLE mazes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                width INT NOT NULL,
                height INT NOT NULL,
                num_crocodiles INT NOT NULL,
                crocodile_damage INT NOT NULL,
                num_medkits INT NOT NULL,
                medkit_heal INT NOT NULL,
                question_time INT NOT NULL,
                question_damage INT NOT NULL,
                num_questions INT NOT NULL
            );
            """,

            // Create dispositions table
            """
            CREATE TABLE dispositions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                maze_id INT,
                FOREIGN KEY (maze_id) REFERENCES mazes(id) ON DELETE CASCADE
            );
            """,

            // Create questions table
            """
            CREATE TABLE questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question VARCHAR(100) NOT NULL,
                answer VARCHAR(40) NOT NULL,
                hint VARCHAR(150) NOT NULL
            );
            """,

            // Create games table
            """
            CREATE TABLE games (
                user_id INT,
                maze_id INT,
                disposition_id INT,
                victory BOOLEAN,
                health INT,
                correct_questions INT,
                wrong_questions INT,
                points INT,
                time VARCHAR(20),
                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY (maze_id) REFERENCES mazes(id) ON DELETE CASCADE,
                FOREIGN KEY (disposition_id) REFERENCES dispositions(id) ON DELETE CASCADE
            );
            """,

            // Create disposition_grid table
            """
            CREATE TABLE disposition_grid (
                disposition_id INT NOT NULL,
                x_coord INT NOT NULL,
                y_coord INT NOT NULL,
                element INT NOT NULL,
                PRIMARY KEY (x_coord, y_coord, disposition_id),
                FOREIGN KEY (disposition_id) REFERENCES dispositions(id) ON DELETE CASCADE
            );
            """,

            // Insert default user
            """
            INSERT INTO users (name, password) VALUES ('user', 'user');
            """
        };

        try (Statement stmt = connection.createStatement()) {
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
            System.out.println("SQLite database installed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("SQLite connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Test
    public static void main(String[] args) {
        new DBInitializer();
    }
}