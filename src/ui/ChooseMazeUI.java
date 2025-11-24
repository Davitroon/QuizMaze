package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Controller;
import logic.DBController;
import logic.UIController;
import model.Disposition;
import model.Player;

public class ChooseMazeUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMazes;
	private JTable tableDisposition;
	private DefaultTableModel mazeModel;
	private DefaultTableModel dispositionModel;

	private JButton btnPlay;
	private JButton btnPlayRandomDisposition;

	/**
	 * Create the frame.
	 * 
	 * @param userMenu
	 */
	@SuppressWarnings("serial")
	public ChooseMazeUI(Controller controller) {
		UIController uiController = controller.getUiController();
		DBController dbController = controller.getDbController();
		Player player = controller.getPlayer();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 396);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPlay = new JLabel("Welcome " + player.getName() + ", choose a maze and a disposition to play.");
		lblPlay.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlay.setBounds(10, 21, 803, 27);
		lblPlay.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPlay);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 80, 557, 188);
		contentPane.add(scrollPane);

		mazeModel = new DefaultTableModel(new Object[][] {},
				new String[] { "id", "xCoord", "yCoord", "num_crocodiles", "crocodile_damage", "num_medkits",
						"medkit_life", "question_time", "question_damage", "num_questions" });

		dispositionModel = new DefaultTableModel(new Object[][] {

		}, new String[] { "id_disposition" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableMazes = new JTable();
		tableMazes.setModel(mazeModel);
		scrollPane.setViewportView(tableMazes);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(600, 80, 171, 188);
		contentPane.add(scrollPane_1);

		tableDisposition = new JTable();
		tableDisposition.setModel(dispositionModel);
		scrollPane_1.setViewportView(tableDisposition);

		JButton btnBack = new JButton("Go back");
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(25, 298, 99, 34);
		contentPane.add(btnBack);

		btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPlay.setBackground(new Color(128, 255, 255));
		btnPlay.setEnabled(false);
		btnPlay.setBounds(672, 298, 99, 34);
		contentPane.add(btnPlay);

		btnPlayRandomDisposition = new JButton("Play with a random disposition");
		btnPlayRandomDisposition.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPlayRandomDisposition.setEnabled(false);
		btnPlayRandomDisposition.setBackground(new Color(128, 255, 255));
		btnPlayRandomDisposition.setBounds(457, 298, 205, 34);
		contentPane.add(btnPlayRandomDisposition);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseMazeUI.this.setVisible(false);
				resetWindow();
				uiController.getLoginUI().login();
			}
		});

		tableMazes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPlay.setEnabled(false);
				int row = tableMazes.rowAtPoint(e.getPoint());
				int column = tableMazes.columnAtPoint(e.getPoint());

				if (row >= 0 && column >= 0) {
					btnPlayRandomDisposition.setEnabled(true);
					if (dispositionModel.getRowCount() > 0) {
						dispositionModel.setRowCount(0);
					}

					ResultSet rset = dbController.queryDispositions((int) mazeModel.getValueAt(row, 0));

					try {
						while (rset.next()) {
							Object[] dispositionRow = new Object[] { rset.getInt("id"), };
							dispositionModel.addRow(dispositionRow);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		tableDisposition.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableDisposition.rowAtPoint(e.getPoint());
				int column = tableDisposition.columnAtPoint(e.getPoint());

				if (row >= 0 && column >= 0) {
					btnPlay.setEnabled(true);
				}
			}
		});

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowMaze = tableMazes.getSelectedRow();
				int rowDisposition = tableDisposition.getSelectedRow();
				if (rowMaze == -1 || rowDisposition == -1) {
					JOptionPane.showMessageDialog(null, "Select a maze and a disposition.");
					return;
				}

				int idDisposition = (int) dispositionModel.getValueAt(rowDisposition, 0);
				int xCoord = (int) mazeModel.getValueAt(rowMaze, 1);
				int yCoord = (int) mazeModel.getValueAt(rowMaze, 2);

				int crocodileDamage = (int) mazeModel.getValueAt(rowMaze, 4);
				int medkitLife = (int) mazeModel.getValueAt(rowMaze, 6);
				int timeQuestion = (int) mazeModel.getValueAt(rowMaze, 7);
				int questionDamage = (int) mazeModel.getValueAt(rowMaze, 8);

				int[][] matrix = dbController.loadDispositionMatrix(idDisposition, xCoord, yCoord);
				int row = tableMazes.getSelectedRow();
				int mazeId = (int) mazeModel.getValueAt(row, 0);
				row = tableDisposition.getSelectedRow();
				int dispId = (int) dispositionModel.getValueAt(row, 0);

				uiController.getMazeUI().startGame(matrix, yCoord, row, player, timeQuestion, medkitLife, crocodileDamage, questionDamage, mazeId, dispId);
				uiController.changeView(ChooseMazeUI.this, uiController.getMazeUI().getFrame());
			}
		});

		btnPlayRandomDisposition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowMaze = tableMazes.getSelectedRow();
				if (rowMaze == -1) {
					JOptionPane.showMessageDialog(null, "Select a maze first.", "Attention",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int mazeId = (int) mazeModel.getValueAt(rowMaze, 0);
				int xCoord = (int) mazeModel.getValueAt(rowMaze, 1);
				int yCoord = (int) mazeModel.getValueAt(rowMaze, 2);
				int numCrocodiles = (int) mazeModel.getValueAt(rowMaze, 3);
				int crocodileDamage = (int) mazeModel.getValueAt(rowMaze, 4);
				int numMedkits = (int) mazeModel.getValueAt(rowMaze, 5);
				int medkitLife = (int) mazeModel.getValueAt(rowMaze, 6);
				int timeQuestion = (int) mazeModel.getValueAt(rowMaze, 7);
				int questionDamage = (int) mazeModel.getValueAt(rowMaze, 8);

				int[][] base = dbController.loadDispositionMatrix(mazeId, xCoord, yCoord);
				Disposition newDisp = new Disposition(base, mazeId);

				try {
					newDisp.generateMatrix(numMedkits, numCrocodiles);
					dbController.insertDisposition(newDisp);
					newDisp.saveMatrix(dbController);
					
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error generating matrix",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				uiController.getMazeUI().startGame(newDisp.getMap(), xCoord, yCoord, player, timeQuestion, medkitLife, crocodileDamage, questionDamage, mazeId, mazeId);
				uiController.changeView(ChooseMazeUI.this, uiController.getMazeUI().getFrame());

			}
		});
	}

	/**
	 * Load mazes from DB and insert into table.
	 */
	public void loadMazes(DBController dbController) {
		ResultSet rset;
		try {
			rset = dbController.queryAll("mazes");
			while (rset.next()) {
				Object[] row = new Object[] { rset.getInt("id"), rset.getInt("x_coord"), rset.getInt("y_coord"),
						rset.getInt("num_crocodiles"), rset.getInt("crocodile_damage"), rset.getInt("num_medkits"),
						rset.getInt("medkit_life"), rset.getInt("question_time"), rset.getInt("question_damage"),
						rset.getInt("num_questions") };
				mazeModel.addRow(row);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reset visual state.
	 */
	public void resetWindow() {
		btnPlay.setEnabled(false);
		btnPlayRandomDisposition.setEnabled(false);
		tableMazes.clearSelection();

		tableMazes.clearSelection();

		if (dispositionModel.getRowCount() > 0) {
			dispositionModel.setRowCount(0);
		}

		if (mazeModel.getRowCount() > 0) {
			mazeModel.setRowCount(0);
		}
	}
}
