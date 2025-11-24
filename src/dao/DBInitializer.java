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
						    question VARCHAR(255) NOT NULL,
						    answer_a VARCHAR(100) NOT NULL,
						    answer_b VARCHAR(100) NOT NULL,
						    answer_c VARCHAR(100) NOT NULL,
						    answer_d VARCHAR(100) NOT NULL,
						    correct_answer INTEGER NOT NULL
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
						""",

				"""
						INSERT INTO questions (question, answer_a, answer_b, answer_c, answer_d, correct_answer) VALUES
						    ('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 3),
						    ('What programming language is primarily used for styling web pages?', 'JavaScript', 'Python', 'CSS', 'HTML', 3),
						    ('How many planets are in our solar system (excluding dwarf planets)?', '7', '8', '9', '10', 2),
						    ('What is the chemical symbol for water?', 'O2', 'H2O', 'CO2', 'NaCl', 2),
						    ('Who wrote the play "Romeo and Juliet"?', 'Charles Dickens', 'William Shakespeare', 'Jane Austen', 'F. Scott Fitzgerald', 2),
						    ('In what year did the first manned mission land on the Moon?', '1969', '1971', '1965', '1975', 1),
						    ('What type of database management system is MySQL?', 'NoSQL', 'Relational', 'Document', 'Graph', 2),
						    ('What is the largest ocean on Earth?', 'Atlantic', 'Indian', 'Pacific', 'Arctic', 3),
						    ('What does the acronym WWW stand for?', 'World Wide Web', 'Windows Web World', 'Wide World Web', 'Web World Wide', 1),
						    ('Which primary color is missing: Red, Blue, ...?', 'Green', 'Yellow', 'Violet', 'Black', 2),
						    ('What is the powerhouse of the cell?', 'Nucleus', 'Mitochondria', 'Ribosome', 'Cytoplasm', 2),
						    ('What is the fastest animal on land?', 'Cheetah', 'Lion', 'Gazelle', 'Leopard', 1),
						    ('What is the currency of Japan?', 'Yuan', 'Won', 'Yen', 'Rupee', 3),
						    ('Which planet is known as the "Red Planet"?', 'Jupiter', 'Venus', 'Mars', 'Saturn', 3),
						    ('What is the main purpose of the WHERE clause in SQL?', 'Grouping results', 'Ordering results', 'Filtering records', 'Joining tables', 3),
						    ('Which data structure uses LIFO (Last-In, First-Out) principle?', 'Queue', 'Array', 'Stack', 'Linked List', 3),
						    ('What temperature does water boil at in Celsius?', '90°C', '100°C', '110°C', '95°C', 2),
						    ('Which famous scientist developed the theory of relativity?', 'Isaac Newton', 'Galileo Galilei', 'Albert Einstein', 'Nikola Tesla', 3),
						    ('What is the process of turning liquid into gas called?', 'Condensation', 'Melting', 'Evaporation', 'Sublimation', 3),
						    ('What command is used to retrieve data from a database?', 'UPDATE', 'INSERT', 'DELETE', 'SELECT', 4),
						    ('How many bits are in a byte?', '4', '8', '16', '32', 2),
						    ('Which country is known as the Land of the Rising Sun?', 'China', 'South Korea', 'Japan', 'Thailand', 3),
						    ('What is the largest land animal?', 'Elephant', 'Rhinoceros', 'Hippopotamus', 'Giraffe', 1),
						    ('What element has the chemical symbol O?', 'Gold', 'Oxygen', 'Osmium', 'Iron', 2),
						    ('Which of these is a popular version control system?', 'FTP', 'SSH', 'Git', 'HTTP', 3),
						    ('What is the capital of Australia?', 'Sydney', 'Melbourne', 'Canberra', 'Perth', 3),
						    ('What is the smallest prime number?', '0', '1', '2', '3', 3),
						    ('Which famous historical figure was the first Emperor of Rome?', 'Julius Caesar', 'Augustus', 'Nero', 'Caligula', 2),
						    ('In object-oriented programming, what is a blueprint for creating objects?', 'Method', 'Attribute', 'Class', 'Interface', 3),
						    ('Which mountain is the tallest in the world?', 'K2', 'Mount Everest', 'Kangchenjunga', 'Lhotse', 2),
						    ('What is the main component of Earth''s atmosphere?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Argon', 2),
						    ('Which company developed the Java programming language?', 'Microsoft', 'Google', 'Sun Microsystems', 'Apple', 3),
						    ('What is the chemical symbol for gold?', 'Ag', 'Au', 'Fe', 'Cu', 2),
						    ('Which planet is closest to the Sun?', 'Venus', 'Mars', 'Mercury', 'Earth', 3),
						    ('What is the longest river in the world?', 'Amazon River', 'Nile River', 'Yangtze River', 'Mississippi River', 2),
						    ('In computing, what does CPU stand for?', 'Central Process Unit', 'Computer Personal Unit', 'Central Processing Unit', 'Control Program Utility', 3),
						    ('What is the default port for HTTPS traffic?', '21', '80', '443', '8080', 3),
						    ('Which famous war involved the battles of Marathon and Thermopylae?', 'Punic Wars', 'Peloponnesian War', 'Persian Wars', 'Crusades', 3),
						    ('What is the smallest continent by land area?', 'Europe', 'Antarctica', 'Australia', 'South America', 3),
						    ('Which element is represented by the letter K on the periodic table?', 'Iron', 'Potassium', 'Krypton', 'Calcium', 2),
						    ('What is the capital city of Canada?', 'Toronto', 'Vancouver', 'Montreal', 'Ottawa', 4),
						    ('What SQL keyword is used to sort the result set?', 'GROUP BY', 'FILTER', 'SORT BY', 'ORDER BY', 4),
						    ('Which Renaissance artist painted the "Mona Lisa"?', 'Donatello', 'Michelangelo', 'Leonardo da Vinci', 'Raphael', 3),
						    ('What is the currency of Switzerland?', 'Euro', 'Pound', 'Swiss Franc', 'Krone', 3),
						    ('How many sides does a hexagon have?', '5', '6', '7', '8', 2),
						    ('Which physical component of a computer is often referred to as the "brain"?', 'RAM', 'Hard Drive', 'Motherboard', 'CPU', 4),
						    ('What is the largest internal organ in the human body?', 'Heart', 'Lungs', 'Liver', 'Kidneys', 3),
						    ('Which gas do plants primarily absorb from the atmosphere?', 'Oxygen', 'Nitrogen', 'Hydrogen', 'Carbon Dioxide', 4),
						    ('What year did the Berlin Wall fall?', '1985', '1991', '1989', '1990', 3),
						    ('In relational databases, what key uniquely identifies a row in a table?', 'Foreign Key', 'Compound Key', 'Primary Key', 'Index Key', 3);
						""" };

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
}