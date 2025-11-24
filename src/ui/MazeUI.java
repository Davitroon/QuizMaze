package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Controller;
import logic.DBController;
import logic.UIController;
import model.Question;
import model.User;

/**
 * The {@code MazeUI} class represents the main interface for playing a maze
 * game. The player navigates through a matrix-based maze, encounters medkits,
 * crocodiles, and must answer timed questions to progress.
 */
public class MazeUI {

	private JFrame frame;
	private JPanel[] gridCells = new JPanel[9];
	private int[][] matrix;

	private int width, height;
	private User user;
	private int mazeId;
	private int layoutId;
	
	private JLabel lifeLabel;
	private JLabel timerLabel;
	private Timer timer;
	private int startTime;
	private int questionTime;
	private int medkitLife;
	private int crocodileDamage;
	private int questionDamage;
	private List<Question> questions;
	private int questionIndex = 0;
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	private JLabel scoreLabel;
	JPanel userViewPanel;

	private DBController dbController;
	private UIController uiController;

	/**
	 * Constructs the MazeUI window and initializes all UI components, but does not
	 * start the game yet.
	 */
	public MazeUI() {
		frame = new JFrame("Maze");
		frame.setResizable(false);
		frame.setBounds(100, 100, 452, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lifeLabel = new JLabel();
		lifeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lifeLabel.setBounds(50, 10, 200, 30);
		frame.getContentPane().add(lifeLabel);

		timerLabel = new JLabel("Time: 00:00");
		timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		timerLabel.setBounds(250, 10, 150, 30);
		frame.getContentPane().add(timerLabel);

		userViewPanel = new JPanel();
		userViewPanel.setBounds(50, 50, 350, 350);
		frame.getContentPane().add(userViewPanel);
		userViewPanel.setLayout(new GridLayout(3, 3, 5, 5));

		// Movement buttons
		JButton btnUP = new JButton("↑");
		btnUP.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnUP.setBounds(180, 410, 75, 65);
		frame.getContentPane().add(btnUP);

		JButton btnDOWN = new JButton("↓");
		btnDOWN.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnDOWN.setBounds(180, 494, 75, 65);
		frame.getContentPane().add(btnDOWN);

		JButton btnLEFT = new JButton("←");
		btnLEFT.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnLEFT.setBounds(90, 494, 75, 65);
		frame.getContentPane().add(btnLEFT);

		JButton btnRIGHT = new JButton("→");
		btnRIGHT.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnRIGHT.setBounds(265, 494, 75, 65);
		frame.getContentPane().add(btnRIGHT);

		scoreLabel = new JLabel("Score = ");
		scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		scoreLabel.setBounds(180, 590, 200, 30);
		frame.getContentPane().add(scoreLabel);

		// Movement actions
		btnUP.addActionListener(e -> tryMove(0, -1));
		btnDOWN.addActionListener(e -> tryMove(0, 1));
		btnLEFT.addActionListener(e -> tryMove(-1, 0));
		btnRIGHT.addActionListener(e -> tryMove(1, 0));
	}

	/**
	 * Initializes controllers and loads questions from the database.
	 *
	 * @param controller main controller used to access database and UI logic
	 */
	public void initialize(Controller controller) {
		dbController = controller.getDbController();
		uiController = controller.getUiController();
		questions = dbController.getQuestions();
		Collections.shuffle(this.questions);
	}

	/**
	 * Attempts to move the user to a new position in the maze. Applies wall checks,
	 * boundary checks, medkit and crocodile effects, and triggers a timed question
	 * before allowing movement.
	 *
	 * @param offsetX horizontal movement (-1, 0, 1)
	 * @param offsetY vertical movement (-1, 0, 1)
	 */
	private void tryMove(int offsetX, int offsetY) {
		int targetX = user.getX() + offsetX;
		int targetY = user.getY() + offsetY;

		// Boundary checks
		if (targetX < 0 || targetX >= width || targetY < 0 || targetY >= height) {
			JOptionPane.showMessageDialog(frame, "You cannot leave the maze.");
			return;
		}

		// Wall detection
		if (matrix[targetY][targetX] == 3) {
			JOptionPane.showMessageDialog(frame, "There is a wall!");
			return;
		}

		// Question gate
		if (showTimedQuestion()) {
			user.moveTo(targetX, targetY);

			// Cell interactions
			if (matrix[targetY][targetX] == 1) { // Medkit
				int leftover = user.heal(medkitLife);
				if (leftover > 0) {
					user.addPoints(leftover);
					JOptionPane.showMessageDialog(frame,
							"You found a medkit! +" + (medkitLife - leftover) + " life, +" + leftover + " points");
				} else {
					JOptionPane.showMessageDialog(frame, "You found a medkit! +" + medkitLife + " life");
				}
				scoreLabel.setText("Score = 0");
				matrix[targetY][targetX] = 0;
			} else if (matrix[targetY][targetX] == 2) { // Crocodile
				user.reduceHealth(crocodileDamage);
				JOptionPane.showMessageDialog(frame, "A crocodile bit you! -" + crocodileDamage + " life");
				matrix[targetY][targetX] = 0;
			}

			updateView();

			// Goal detection
			if (targetX == width - 1 && targetY == height - 1) {
				timer.stop();
				JOptionPane.showMessageDialog(frame,
						"You reached the goal!\nTime: " + timerLabel.getText().replace("Time: ", ""));
				showSummary();
				frame.dispose();
			}
		} else {
			// Player failed question
			if (user.getHealth() <= 0) {
				timer.stop();
				JOptionPane.showMessageDialog(frame, "You lost! You ran out of life.");
				showSummary();
				frame.dispose();
			}
		}
	}

	/**
	 * Updates the 3x3 interface showing the cells around the player's current
	 * position. Colors represent:
	 * <ul>
	 * <li>White – empty</li>
	 * <li>Green – medkit</li>
	 * <li>Red – crocodile</li>
	 * <li>Gray – wall</li>
	 * <li>Yellow – player</li>
	 * <li>Gold – goal</li>
	 * <li>Black – out of bounds</li>
	 * </ul>
	 */
	private void updateView() {
		int userX = user.getX();
		int userY = user.getY();

		for (int offsetY = -1; offsetY <= 1; offsetY++) {
			for (int offsetX = -1; offsetX <= 1; offsetX++) {
				int x = userX + offsetX;
				int y = userY + offsetY;
				int index = (offsetY + 1) * 3 + (offsetX + 1);
				JPanel cell = gridCells[index];

				if (x >= 0 && x < width && y >= 0 && y < height) {
					int element = matrix[y][x];

					if (x == width - 1 && y == height - 1) {
						cell.setBackground(new Color(255, 215, 0));
					} else {
						switch (element) {
						case 0:
							cell.setBackground(Color.WHITE);
							break;
						case 1:
							cell.setBackground(Color.GREEN);
							break;
						case 2:
							cell.setBackground(Color.RED);
							break;
						case 3:
							cell.setBackground(Color.GRAY);
							break;
						default:
							cell.setBackground(Color.LIGHT_GRAY);
							break;
						}
					}
				} else {
					cell.setBackground(Color.BLACK);
				}
			}
		}

		gridCells[4].setBackground(Color.YELLOW);
		lifeLabel.setText("Life: " + user.getHealth());
	}

	/** Starts the game timer which updates the displayed time every second. */
	private void startTimer() {
		startTime = (int) System.currentTimeMillis();
		timer = new Timer(1000, e -> updateTimer());
		timer.start();
	}

	/** Updates the timer label based on elapsed time since game start. */
	private void updateTimer() {
		int currentTime = (int) System.currentTimeMillis();
		int seconds = (currentTime - startTime) / 1000;

		int min = seconds / 60;
		int sec = seconds % 60;

		timerLabel.setText(String.format("Time: %02d:%02d", min, sec));
	}

	/**
	 * Shows a timed question to the player. The player must answer correctly or
	 * lose health.
	 *
	 * @return {@code true} if the answer was correct, {@code false} otherwise
	 */
	private boolean showTimedQuestion() {
		if (questions == null || questions.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No questions available.");
			return false;
		}

		if (questionIndex >= questions.size()) {
			questionIndex = 0;
		}

		Question question = questions.get(questionIndex++);

		QuestionUI questionDialog = new QuestionUI(frame, question, questionTime);
		boolean result = questionDialog.isCorrect();

		if (result) {
			correctAnswers++;
			user.addPoints(10);
			scoreLabel.setText("Score = " + user.getPoints());
			JOptionPane.showMessageDialog(frame, "Correct!");
		} else {
			incorrectAnswers++;
			user.reduceHealth(questionDamage);
			JOptionPane.showMessageDialog(frame,
					"Too many wrong answers or time ran out! You lose " + questionDamage + " life.");
			updateView();
		}

		return result;
	}

	/**
	 * Shows a summary of the game, saves the data to the database, and displays the
	 * results window.
	 */
	private void showSummary() {
		String time = timerLabel.getText().replace("Time: ", "");
		boolean victory = user.getHealth() > 0;

		dbController.insertGame(user.getId(), mazeId, layoutId, victory, user.getHealth(), correctAnswers,
				incorrectAnswers, user.getPoints(), time);

		ResultsMazeUI resultsWindow = uiController.getResultsMazeUI();
		resultsWindow.loadData(correctAnswers, incorrectAnswers, user, time, victory, mazeId, layoutId, matrix);
		resultsWindow.setLocationRelativeTo(frame);
		resultsWindow.setVisible(true);

		frame.dispose();
	}

	/**
	 * Starts a new game by setting the maze, user, and configuration values.
	 *
	 * @param matrix          maze matrix
	 * @param width           maze width
	 * @param height          maze height
	 * @param user            player object
	 * @param questionTime    time allowed for each question
	 * @param medkitLife      life restored by medkits
	 * @param crocodileDamage damage dealt by crocodiles
	 * @param questionDamage  damage for incorrect trivia answers
	 * @param mazeId          maze identifier
	 * @param layoutId        layout identifier
	 */
	public void startGame(int[][] matrix, int width, int height, User user, int questionTime, int medkitLife,
			int crocodileDamage, int questionDamage, int mazeId, int layoutId) {

		this.matrix = matrix;
		this.width = width;
		this.height = height;
		this.user = user;
		this.questionTime = questionTime;
		this.medkitLife = medkitLife;
		this.crocodileDamage = crocodileDamage;
		this.questionDamage = questionDamage;
		this.mazeId = mazeId;
		this.layoutId = layoutId;

		lifeLabel.setText("Life: " + user.getHealth());

		// Generate 3×3 cells
		for (int i = 0; i < 9; i++) {
			JPanel cell = new JPanel();
			cell.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
			userViewPanel.add(cell);
			gridCells[i] = cell;
		}

		updateView();
		startTimer();
	}

	/**
	 * @return the game window frame
	 */
	public JFrame getFrame() {
		return frame;
	}
}
