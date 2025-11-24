package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Controller;
import logic.DBController;
import model.User;

/**
 * ResultsMazeUI is the window shown after finishing a maze game. It displays a
 * full summary of the gameplay, including: - Result (victory or defeat) -
 * Player stats (points, life, time) - Correct/incorrect answers - Top 10
 * leaderboard for the selected maze and layout
 */
public class ResultsMazeUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private DefaultTableModel statsModel;
	private JTable statsTable;
	private DBController dbController;
	private int mazeId, layoutId;
	private Controller controller;

	/* Labels for dynamic data */
	private JLabel lblUserValue;
	private JLabel lblPointsValue;
	private JLabel lblLifeValue;
	private JLabel lblTimeValue;
	private JLabel lblResult;

	/* Text fields for correct/incorrect answers */
	private JTextField tfCorrect;
	private JTextField tfIncorrect;

	private ChooseMazeUI chooseMazeUI;
	private int[][] matrix;

	/**
	 * Constructs the UI window that displays the final results of a maze game.
	 *
	 * @param controller main logic controller
	 */
	public ResultsMazeUI(Controller controller) {

		dbController = controller.getDbController();

		setTitle("Game Summary");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 486);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ——— Game summary panel ———
		JPanel summaryPanel = new JPanel(null);
		summaryPanel.setBounds(10, 58, 300, 300);
		summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
		contentPane.add(summaryPanel);

		lblResult = new JLabel();
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblResult.setBounds(80, 20, 150, 25);
		summaryPanel.add(lblResult);

		// Static labels
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(10, 60, 80, 14);
		summaryPanel.add(lblUser);

		lblUserValue = new JLabel();
		lblUserValue.setBounds(100, 60, 180, 14);
		summaryPanel.add(lblUserValue);

		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setBounds(10, 90, 80, 14);
		summaryPanel.add(lblPoints);

		lblPointsValue = new JLabel();
		lblPointsValue.setBounds(100, 90, 180, 14);
		summaryPanel.add(lblPointsValue);

		JLabel lblLife = new JLabel("Life:");
		lblLife.setBounds(10, 120, 80, 14);
		summaryPanel.add(lblLife);

		lblLifeValue = new JLabel();
		lblLifeValue.setBounds(100, 120, 180, 14);
		summaryPanel.add(lblLifeValue);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(10, 150, 80, 14);
		summaryPanel.add(lblTime);

		lblTimeValue = new JLabel();
		lblTimeValue.setBounds(100, 150, 180, 14);
		summaryPanel.add(lblTimeValue);

		// Correct / Incorrect
		JLabel lblCorrect = new JLabel("Correct answers:");
		lblCorrect.setBounds(10, 180, 150, 14);
		summaryPanel.add(lblCorrect);

		tfCorrect = new JTextField();
		tfCorrect.setBounds(160, 180, 50, 20);
		tfCorrect.setEditable(false);
		summaryPanel.add(tfCorrect);

		JLabel lblIncorrect = new JLabel("Incorrect answers:");
		lblIncorrect.setBounds(10, 210, 150, 14);
		summaryPanel.add(lblIncorrect);

		tfIncorrect = new JTextField();
		tfIncorrect.setBounds(160, 210, 50, 20);
		tfIncorrect.setEditable(false);
		summaryPanel.add(tfIncorrect);

		// ——— Top 10 panel ———
		JPanel statsPanel = new JPanel(new BorderLayout());
		statsPanel.setBounds(330, 10, 350, 370);
		statsPanel.setBorder(BorderFactory.createTitledBorder("Top 10 Users"));
		contentPane.add(statsPanel);

		statsModel = new DefaultTableModel(new Object[] { "User", "Points", "Time", "Life", "Victory" }, 0);
		statsTable = new JTable(statsModel);
		statsPanel.add(new JScrollPane(statsTable), BorderLayout.CENTER);

		// Buttons
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(10, 402, 99, 34);
		contentPane.add(btnBack);

		JButton btnShowLayout = new JButton("Show layout");
		btnShowLayout.setBounds(119, 402, 147, 34);
		contentPane.add(btnShowLayout);

		// Back action
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				chooseMazeUI.resetWindow();
				chooseMazeUI.setVisible(true);
			}
		});

		// Print matrix action
		btnShowLayout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printMatrix();
			}
		});
	}

	/**
	 * Loads the game data into the UI after the maze has been completed.
	 *
	 * @param correctAnswers   number of correct answers
	 * @param incorrectAnswers number of incorrect answers
	 * @param user             the user who played the maze
	 * @param time             time spent in the maze
	 * @param victory          true if the player won, false otherwise
	 * @param mazeId           id of the maze played
	 * @param layoutId         id of the layout used
	 * @param matrix           matrix representing the maze layout
	 */
	public void loadData(int correctAnswers, int incorrectAnswers, User user, String time, boolean victory, int mazeId,
			int layoutId, int[][] matrix) {

		this.mazeId = mazeId;
		this.layoutId = layoutId;
		this.matrix = matrix;

		lblResult.setText(victory ? "You won!" : "You lost");

		lblUserValue.setText(controller.getUser().getName());
		lblPointsValue.setText(String.valueOf(user.getPoints()));
		lblLifeValue.setText(String.valueOf(user.getHealth()));
		lblTimeValue.setText(time);

		tfCorrect.setText(String.valueOf(correctAnswers));
		tfIncorrect.setText(String.valueOf(incorrectAnswers));

		loadStats();
	}

	/**
	 * Initializes this UI with the main controller and retrieves the ChooseMazeUI
	 * reference.
	 *
	 * @param controller main controller
	 */
	public void intialize(Controller controller) {
		this.controller = controller;
		chooseMazeUI = controller.getUiController().getChooseMazeUI();
	}

	/**
	 * Loads the Top-10 statistics for the current maze and layout from the
	 * database.
	 */
	private void loadStats() {
		statsModel.setRowCount(0);
		try (ResultSet rs = dbController.getTop10(mazeId, layoutId)) {
			while (rs != null && rs.next()) {
				statsModel.addRow(new Object[] { rs.getString("username"), rs.getInt("points"), rs.getString("time"),
						rs.getInt("health"), rs.getBoolean("victory") ? "Yes" : "No" });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading statistics", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Prints the maze layout to the console using ASCII symbols: - Borders: # -
	 * Start: O - Exit: X - Empty: space - Medkit: M - Crocodile: C - Wall: #
	 */
	public void printMatrix() {
		int rows = matrix.length;
		int columns = matrix[0].length;

		for (int y = 0; y < rows + 2; y++) {
			for (int x = 0; x < columns + 2; x++) {

				if (y == 0 || y == rows + 1 || x == 0 || x == columns + 1) {
					System.out.print("#");
				} else {
					int mapY = y - 1;
					int mapX = x - 1;

					if (mapY == 0 && mapX == 0) {
						System.out.print("O"); // Start
					} else if (mapY == rows - 1 && mapX == columns - 1) {
						System.out.print("X"); // Exit
					} else {
						switch (matrix[mapY][mapX]) {
						case 0:
							System.out.print(" ");
							break;
						case 1:
							System.out.print("M");
							break;
						case 2:
							System.out.print("C");
							break;
						case 3:
							System.out.print("#");
							break;
						default:
							System.out.print("?");
							break;
						}
					}
				}
			}
			System.out.println();
		}
	}
}
