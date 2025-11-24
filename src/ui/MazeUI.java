package ui;

import java.awt.Color; // Allows defining colors (e.g., Color.WHITE, Color.RED)
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory; // To create borders around components
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import logic.Controller;
import logic.DBController;
import logic.UIController;
import model.Player;
import model.Question;

public class MazeUI {
	private JFrame frame;
	private JPanel[] gridCells = new JPanel[9]; // Array of JPanels representing the player's view
	private int[][] matrix;
	private int width, height;
	private Player player;
	private int mazeId;
	private int layoutId;

	private JLabel lifeLabel;
	private JLabel timerLabel;
	private Timer timer;
	private int startTime;
	private int questionTime; // seconds, obtained from maze/layout

	private int medkitLife;
	private int crocodileDamage;
	private int questionDamage;

	private List<Question> questions; // List of Question objects
	private int questionIndex = 0;

	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	private JLabel scoreLabel;
	JPanel playerViewPanel;

	private DBController dbController;
	private UIController uiController;

	// Variables used in showTimedQuestion()
	private boolean questionResult = false;
	private int questionAttempts = 0;
	private JDialog questionDialog;

	// Constructor receives parameters to create a unique view
	public MazeUI(Controller controller) {
		dbController = controller.getDbController();
		uiController = controller.getUiController();
		questions = dbController.getQuestions();
		Collections.shuffle(this.questions); // Randomize questions
		initialize();
	}

	/**
	 * Initialize the application.
	 */
	private void initialize() {
		frame = new JFrame("Maze");
		frame.setResizable(false);
		frame.setBounds(100, 100, 452, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Life label
		lifeLabel = new JLabel();
		lifeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lifeLabel.setBounds(50, 10, 200, 30);
		frame.getContentPane().add(lifeLabel);

		// Timer label
		timerLabel = new JLabel("Time: 00:00");
		timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		timerLabel.setBounds(250, 10, 150, 30);
		frame.getContentPane().add(timerLabel);

		playerViewPanel = new JPanel();
		playerViewPanel.setBounds(50, 50, 350, 350);
		frame.getContentPane().add(playerViewPanel);
		playerViewPanel.setLayout(new GridLayout(3, 3, 5, 5));



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

		// Movement listeners
		btnUP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tryMove(0, -1);
			}
		});
		btnDOWN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tryMove(0, 1);
			}
		});
		btnLEFT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tryMove(-1, 0);
			}
		});
		btnRIGHT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tryMove(1, 0);
			}
		});
	}

	private void tryMove(int offsetX, int offsetY) {
		int targetX = player.getX() + offsetX;
		int targetY = player.getY() + offsetY;
		if (targetX < 0 || targetX >= width || targetY < 0 || targetY >= height) {
			JOptionPane.showMessageDialog(frame, "You cannot leave the maze.");
			return;
		}
		if (matrix[targetY][targetX] == 3) {
			JOptionPane.showMessageDialog(frame, "There is a wall!");
			return;
		}

		// Timed question
		if (showTimedQuestion()) {
			player.moveTo(targetX, targetY);

			// Cell effects
			if (matrix[targetY][targetX] == 1) { // Medkit
				int leftover = player.heal(medkitLife); // Returns leftover life as points
				if (leftover > 0) {
					player.addPoints(leftover);
					JOptionPane.showMessageDialog(frame,
							"You found a medkit! +" + (medkitLife - leftover) + " life, +" + leftover + " points");
				} else {
					JOptionPane.showMessageDialog(frame, "You found a medkit! +" + medkitLife + " life");
				}
				scoreLabel.setText("Score = " + player.getPoints());
				matrix[targetY][targetX] = 0; // Remove medkit
			} else if (matrix[targetY][targetX] == 2) { // Crocodile
				player.reduceHealth(crocodileDamage);
				JOptionPane.showMessageDialog(frame, "A crocodile bit you! -" + crocodileDamage + " life");
				matrix[targetY][targetX] = 0; // Remove crocodile
			}

			updateView();

			// Check if reached goal (bottom-right corner)
			if (targetX == width - 1 && targetY == height - 1) {
				timer.stop();
				JOptionPane.showMessageDialog(frame,
						"You reached the goal!\nTime: " + timerLabel.getText().replace("Time: ", ""));
				showSummary();
				frame.dispose();
			}
		} else { // If player loses life after failing the question
			if (player.getHealth() <= 0) {
				timer.stop();
				JOptionPane.showMessageDialog(frame, "You lost! You ran out of life.");
				showSummary();
				frame.dispose();
			}
			// Otherwise, player does not move
		}
	}

	/*
	 * This method is essential. Renders a 3x3 window centered on the player with
	 * colors based on maze content.
	 */
	private void updateView() {
		int playerX = player.getX();
		int playerY = player.getY();

		for (int offsetY = -1; offsetY <= 1; offsetY++) {
			for (int offsetX = -1; offsetX <= 1; offsetX++) {
				int x = playerX + offsetX;
				int y = playerY + offsetY;
				int index = (offsetY + 1) * 3 + (offsetX + 1); // Map 2D (-1 to 1) to 1D 0-8
				JPanel cell = gridCells[index];
				if (x >= 0 && x < width && y >= 0 && y < height) {
					int element = matrix[y][x];
					if (x == width - 1 && y == height - 1) {
						cell.setBackground(new Color(255, 215, 0)); // Gold color for goal
					} else {
						switch (element) {
						case 0:
							cell.setBackground(Color.WHITE);
							break; // Empty
						case 1:
							cell.setBackground(Color.GREEN);
							break; // Medkit
						case 2:
							cell.setBackground(Color.RED);
							break; // Crocodile
						case 3:
							cell.setBackground(Color.GRAY);
							break; // Wall
						default:
							cell.setBackground(Color.LIGHT_GRAY);
							break;
						}
					}
				} else {
					cell.setBackground(Color.BLACK); // Out of bounds
				}
			}
		}

		// Central cell is the player
		gridCells[4].setBackground(Color.YELLOW);
		lifeLabel.setText("Life: " + player.getHealth());
	}

	/** Starts the timer */
	private void startTimer() {
		startTime = (int) System.currentTimeMillis();
		timer = new Timer(1000, new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				updateTimer();
			}
		});
		timer.start();
	}

	/* Updates the timer label every second */
	private void updateTimer() {
		int currentTime = (int) System.currentTimeMillis();
		int seconds = (int) ((currentTime - startTime) / 1000);
		int min = seconds / 60;
		int sec = seconds % 60;

		StringBuilder minStr = new StringBuilder();
		if (min < 10)
			minStr.append('0');
		minStr.append(min);

		StringBuilder secStr = new StringBuilder();
		if (sec < 10)
			secStr.append('0');
		secStr.append(sec);

		timerLabel.setText("Time: " + minStr + ":" + secStr);
	}

	private boolean showTimedQuestion() {
		if (questions == null || questions.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No questions available.");
			return false;
		}
		if (questionIndex >= questions.size()) {
			questionIndex = 0;
		}
		Question question = questions.get(questionIndex++);

		JTextField answerField = new JTextField();
		JButton answerButton = new JButton("Answer");
		questionResult = false;
		questionAttempts = 0;
		questionDialog = new JDialog(frame, "Question", true);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel(question.getStatement()));
		JLabel timeLabel = new JLabel("Time: " + questionTime + " seconds");
		panel.add(timeLabel);
		panel.add(new JLabel("Hint: " + question.getHint()));
		panel.add(new JLabel("Answer:"));
		panel.add(answerField);
		panel.add(answerButton);

		questionDialog.getContentPane().add(panel);
		questionDialog.pack();
		questionDialog.setLocationRelativeTo(frame);

		Timer countdown = new Timer(1000, null);
		final int[] remainingTime = { questionTime };
		countdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remainingTime[0]--;
				timeLabel.setText("Time: " + remainingTime[0] + " seconds");
				if (remainingTime[0] <= 0) {
					countdown.stop();
					questionDialog.dispose();
				}
			}
		});
		countdown.start();

		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userAnswer = answerField.getText().trim();
				if (userAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
					questionResult = true;
					countdown.stop();
					questionDialog.dispose();
				} else {
					questionAttempts++;
					if (questionAttempts >= 3) {
						JOptionPane.showMessageDialog(questionDialog, "You failed 3 times.");
						countdown.stop();
						questionDialog.dispose();
					} else {
						JOptionPane.showMessageDialog(questionDialog,
								"Incorrect answer. Attempt " + questionAttempts + " of 3.");
					}
				}
			}
		});

		// Enter key triggers the button
		answerField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerButton.doClick();
			}
		});

		questionDialog.setVisible(true);
		countdown.stop();

		if (questionResult) {
			correctAnswers++;
			player.addPoints(10);
			scoreLabel.setText("Score = " + player.getPoints());
			JOptionPane.showMessageDialog(frame, "Correct!");
		} else {
			incorrectAnswers++;
			player.reduceHealth(questionDamage);
			JOptionPane.showMessageDialog(frame,
					"Too many wrong answers or time ran out! You lose " + questionDamage + " life.");
			updateView();
		}

		return questionResult;
	}

	private void showSummary() {
		String time = timerLabel.getText().replace("Time: ", "");
		boolean victory = player.getHealth() > 0;

		// Insert game record into database
		dbController.insertGame(player.getId(), this.mazeId, this.layoutId, victory, player.getHealth(), correctAnswers,
				incorrectAnswers, player.getPoints(), time);

		// Open results window
		ResultsMazeUI resultsWindow = uiController.getResultsMazeUI();
		resultsWindow.initialize(correctAnswers, incorrectAnswers, player, time, victory, mazeId, layoutId, matrix);
		resultsWindow.setLocationRelativeTo(frame); // Center over current window
		resultsWindow.setVisible(true);

		// Close the maze window
		frame.dispose();
	}
	
	public void startGame(int[][] matrix, int width, int height, Player player, int questionTime, int medkitLife,
			int crocodileDamage, int questionDamage, int mazeId, int layoutId) {
		this.matrix = matrix;
		this.width = width;
		this.height = height;
		this.player = player;
		this.questionTime = questionTime;
		this.medkitLife = medkitLife;
		this.crocodileDamage = crocodileDamage;
		this.questionDamage = questionDamage;
		this.mazeId = mazeId;
		this.layoutId = layoutId;
		
		lifeLabel.setText("Life: " + player.getHealth());
		for (int i = 0; i < 9; i++) { // Create 9 cells and store them in the array
			JPanel cell = new JPanel();
			cell.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
			playerViewPanel.add(cell);
			gridCells[i] = cell;
		}
		
		updateView();
		startTimer();
	}

	public JFrame getFrame() {
		return frame;
	}
}