package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dao.DBConnector;
import model.Disposition;
import model.Maze;

public class CreateMazeUI {

	private JFrame frame;
	private JButton btnCreate;
	private JTable mazeTable;
	private JSpinner spinnerHeight;
	private JSpinner spinnerWidth;
	private JSpinner spinnerNumCrocodiles;
	private JSpinner spinnerNumMedkits;
	private JSlider sliderCrocodileDamage;
	private JSlider sliderMedkitHeal;
	private JSpinner spinnerQuestionTime;
	private JSlider sliderQuestionDamage;
	private JScrollPane scrollPaneMazeTable;

	private MazeManagementUI mazeManagement;
	private Maze maze;
	private DBConnector model;
	private Disposition disposition;

	private int minQuestionTime = 5;
	private int maxQuestionTime = 45;

	private int maxMedkits = 10;
	private int maxCrocodiles = 10;

	private int minHeight = 4;
	private int maxHeight = 10;
	private int minWidth = 4;
	private int maxWidth = 10;

	public CreateMazeUI(MazeManagementUI mazeManagement, DBConnector model) {
		initialize();
		this.mazeManagement = mazeManagement;
		this.model = model;
	}

	public JFrame getFrame() {
		return frame;
	}

	@SuppressWarnings("serial")
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 680, 462);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblMazeTitle = new JLabel("Create Maze");
		lblMazeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblMazeTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMazeTitle.setBounds(10, 11, 636, 24);
		frame.getContentPane().add(lblMazeTitle);

		JLabel lblHeight = new JLabel("Height =");
		lblHeight.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHeight.setBounds(10, 64, 74, 24);
		frame.getContentPane().add(lblHeight);

		JLabel lblWidth = new JLabel("Width =");
		lblWidth.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWidth.setBounds(10, 98, 74, 24);
		frame.getContentPane().add(lblWidth);

		JLabel lblNumCrocodiles = new JLabel("Number of Crocodiles =");
		lblNumCrocodiles.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumCrocodiles.setBounds(10, 145, 182, 24);
		frame.getContentPane().add(lblNumCrocodiles);

		JLabel lblNumMedkits = new JLabel("Number of Medkits =");
		lblNumMedkits.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumMedkits.setBounds(10, 176, 182, 24);
		frame.getContentPane().add(lblNumMedkits);

		JLabel lblCrocodileDamage = new JLabel("Crocodile Damage = 25 HP");
		lblCrocodileDamage.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrocodileDamage.setBounds(10, 225, 182, 24);
		frame.getContentPane().add(lblCrocodileDamage);

		JLabel lblMedkitHeal = new JLabel("Heal =");
		lblMedkitHeal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMedkitHeal.setBounds(243, 225, 128, 24);
		frame.getContentPane().add(lblMedkitHeal);

		JLabel lblSeconds = new JLabel("seconds");
		lblSeconds.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSeconds.setBounds(275, 311, 182, 24);
		frame.getContentPane().add(lblSeconds);

		JLabel lblQuestionDamage = new JLabel("Question Damage =");
		lblQuestionDamage.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQuestionDamage.setBounds(446, 225, 182, 24);
		frame.getContentPane().add(lblQuestionDamage);

		JLabel lblQuestionTime = new JLabel("Time per Question =");
		lblQuestionTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQuestionTime.setBounds(10, 313, 182, 24);
		frame.getContentPane().add(lblQuestionTime);

		SpinnerNumberModel questionTimeModel = new SpinnerNumberModel(minQuestionTime, minQuestionTime, maxQuestionTime,
				5);
		spinnerQuestionTime = new JSpinner(questionTimeModel);
		spinnerQuestionTime.setBounds(202, 313, 63, 25);
		frame.getContentPane().add(spinnerQuestionTime);

		sliderCrocodileDamage = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderCrocodileDamage.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblCrocodileDamage.setText("Crocodile Damage = " + sliderCrocodileDamage.getValue() + " HP");
			}
		});
		sliderCrocodileDamage.setValue(25);
		sliderCrocodileDamage.setSnapToTicks(true);
		sliderCrocodileDamage.setMinorTickSpacing(5);
		sliderCrocodileDamage.setPaintTicks(true);
		sliderCrocodileDamage.setBounds(10, 266, 200, 26);
		frame.getContentPane().add(sliderCrocodileDamage);

		sliderMedkitHeal = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderMedkitHeal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblMedkitHeal.setText("Heal = " + sliderMedkitHeal.getValue() + " HP");
			}
		});
		sliderMedkitHeal.setValue(10);
		sliderMedkitHeal.setSnapToTicks(true);
		sliderMedkitHeal.setMinorTickSpacing(5);
		sliderMedkitHeal.setPaintTicks(true);
		sliderMedkitHeal.setBounds(236, 266, 200, 26);
		frame.getContentPane().add(sliderMedkitHeal);

		sliderQuestionDamage = new JSlider(JSlider.HORIZONTAL, 10, 90, 25);
		sliderQuestionDamage.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblQuestionDamage.setText("Question Damage = " + sliderQuestionDamage.getValue() + " HP");
			}
		});
		sliderQuestionDamage.setValue(10);
		sliderQuestionDamage.setSnapToTicks(true);
		sliderQuestionDamage.setMinorTickSpacing(5);
		sliderQuestionDamage.setPaintTicks(true);
		sliderQuestionDamage.setBounds(446, 266, 200, 26);
		frame.getContentPane().add(sliderQuestionDamage);

		SpinnerModel medkitValue = new SpinnerNumberModel(1, 0, maxMedkits, 1);
		SpinnerModel crocodileValue = new SpinnerNumberModel(2, 0, maxCrocodiles, 1);

		spinnerNumMedkits = new JSpinner(medkitValue);
		spinnerNumMedkits.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerNumMedkits.setBounds(202, 178, 40, 25);
		frame.getContentPane().add(spinnerNumMedkits);

		spinnerNumCrocodiles = new JSpinner(crocodileValue);
		spinnerNumCrocodiles.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerNumCrocodiles.setBounds(202, 147, 40, 25);
		frame.getContentPane().add(spinnerNumCrocodiles);

		SpinnerNumberModel heightModel = new SpinnerNumberModel(minHeight, minHeight, maxHeight, 1);
		spinnerHeight = new JSpinner(heightModel);
		spinnerHeight.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerHeight.setBounds(94, 64, 40, 25);
		frame.getContentPane().add(spinnerHeight);

		SpinnerNumberModel widthModel = new SpinnerNumberModel(minWidth, minWidth, maxWidth, 1);
		spinnerWidth = new JSpinner(widthModel);
		spinnerWidth.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinnerWidth.setBounds(94, 98, 40, 25);
		frame.getContentPane().add(spinnerWidth);

		scrollPaneMazeTable = new JScrollPane();
		scrollPaneMazeTable.setBounds(285, 46, 163, 163);
		frame.getContentPane().add(scrollPaneMazeTable);

		JButton btnUpdate = new JButton("Resize");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(153, 64, 122, 41);
		frame.getContentPane().add(btnUpdate);

		btnUpdate.addActionListener(e -> {
			btnCreate.setEnabled(true);

			int rows = (Integer) spinnerHeight.getValue();
			int cols = (Integer) spinnerWidth.getValue();

			String[] tableColumns = new String[cols];
			for (int i = 0; i < cols; i++)
				tableColumns[i] = "Col " + (i + 1);

			String[][] tableData = new String[rows][cols];
			for (int i = 0; i < rows; i++)
				for (int j = 0; j < cols; j++)
					tableData[i][j] = "";

			tableData[0][0] = "O";
			tableData[rows - 1][cols - 1] = "X";

			mazeTable = new JTable(tableData, tableColumns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			mazeTable.setTableHeader(null);
			scrollPaneMazeTable.setViewportView(mazeTable);

			mazeTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					int row = mazeTable.rowAtPoint(evt.getPoint());
					int col = mazeTable.columnAtPoint(evt.getPoint());
					int lastRow = mazeTable.getRowCount() - 1;
					int lastCol = mazeTable.getColumnCount() - 1;
					if ((row == 0 && col == 0) || (row == lastRow && col == lastCol))
						return;

					Object value = mazeTable.getValueAt(row, col);
					if (value == null || value.toString().trim().isEmpty())
						mazeTable.setValueAt("3", row, col);
					else if (value.toString().trim().equals("3"))
						mazeTable.setValueAt("", row, col);
				}
			});
		});

		btnCreate = new JButton("CREATE");
		btnCreate.setForeground(new Color(0, 0, 0));
		btnCreate.setBackground(new Color(128, 255, 255));
		btnCreate.setEnabled(false);
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCreate.setBounds(528, 371, 110, 41);
		frame.getContentPane().add(btnCreate);

		JButton btnBack = new JButton("BACK");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBounds(24, 371, 110, 41);
		frame.getContentPane().add(btnBack);

		JLabel lblNotice = new JLabel("* \"3\" represents a Wall ");
		lblNotice.setForeground(new Color(128, 128, 128));
		lblNotice.setFont(new Font("Sitka Text", Font.BOLD, 12));
		lblNotice.setBounds(464, 64, 182, 58);
		frame.getContentPane().add(lblNotice);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrame().dispose();
				resetWindow();
				mazeManagement.setVisible(true);
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int width = (int) spinnerWidth.getValue();
				int height = (int) spinnerHeight.getValue();
				int numCrocodiles = (int) spinnerNumCrocodiles.getValue();
				int crocodileDamage = sliderCrocodileDamage.getValue();
				int numMedkits = (int) spinnerNumMedkits.getValue();
				int medkitHeal = sliderMedkitHeal.getValue();
				int questionTime = (int) spinnerQuestionTime.getValue();
				int questionDamage = sliderQuestionDamage.getValue();
				int numQuestions = 20;

				int rows = mazeTable.getRowCount();
				int cols = mazeTable.getColumnCount();
				int[][] map = new int[rows][cols];
				for (int i = 0; i < rows; i++)
					for (int j = 0; j < cols; j++)
						if ("3".equals(mazeTable.getValueAt(i, j)))
							map[i][j] = 3;

				maze = new Maze(width, height, numCrocodiles, crocodileDamage, numMedkits, medkitHeal, questionTime,
						questionDamage, numQuestions, map);
				disposition = new Disposition(maze.getMap(), maze.getId(), model);

				try {
					disposition.generateMatrix(numMedkits, numCrocodiles);
				} catch (IllegalArgumentException e1) {
					javax.swing.JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
							javax.swing.JOptionPane.ERROR_MESSAGE);
					return;
				}

				model.insertMaze(maze);
				disposition.setMazeId(maze.getId());
				model.insertDisposition(disposition);
				disposition.saveMatrix();
				mazeManagement.updateMazes();
				getFrame().dispose();
				mazeManagement.setVisible(true);
			}
		});
	}

	public void resetWindow() {
		spinnerHeight.setModel(new SpinnerNumberModel(minHeight, minHeight, maxHeight, 1));
		spinnerHeight.setValue(minHeight);

		spinnerWidth.setModel(new SpinnerNumberModel(minWidth, minWidth, maxWidth, 1));
		spinnerWidth.setValue(minWidth);

		spinnerNumCrocodiles.setModel(new SpinnerNumberModel(2, 0, maxCrocodiles, 1));
		spinnerNumCrocodiles.setValue(2);

		spinnerNumMedkits.setModel(new SpinnerNumberModel(1, 0, maxMedkits, 1));
		spinnerNumMedkits.setValue(1);

		spinnerQuestionTime.setModel(new SpinnerNumberModel(minQuestionTime, minQuestionTime, maxQuestionTime, 5));
		spinnerQuestionTime.setValue(minQuestionTime);

		sliderCrocodileDamage.setValue(25);
		sliderMedkitHeal.setValue(10);
		sliderQuestionDamage.setValue(10);

		if (mazeTable != null) {
			scrollPaneMazeTable.setViewportView(null);
			mazeTable = null;
		}

		btnCreate.setEnabled(false);
	}
}
