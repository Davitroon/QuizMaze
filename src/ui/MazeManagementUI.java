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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Controller;
import logic.DBController;
import logic.UIController;

/**
 * The MazeManagementUI class provides an administrative interface for managing
 * mazes stored in the database. Administrators can view, create, and delete
 * mazes. All available mazes are displayed in a table.
 */
public class MazeManagementUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable mazeTable;
	private DefaultTableModel mazeTableModel;
	private JButton btnDelete;
	private DBController dbController;

	/**
	 * Constructs the MazeManagementUI window and initializes all UI components.
	 *
	 * @param controller The application controller used to access the database and
	 *                   UI navigation utilities.
	 */
	@SuppressWarnings("serial")
	public MazeManagementUI(Controller controller) {
		dbController = controller.getDbController();
		UIController uiController = controller.getUiController();

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 404);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Exit button
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExit.setBackground(new Color(128, 128, 128));
		btnExit.setBounds(31, 321, 94, 33);
		contentPane.add(btnExit);

		// Table scroll panel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 102, 806, 208);
		contentPane.add(scrollPane);

		// Table model with non-editable cells
		mazeTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "id", "width", "height", "crocodiles",
				"crocodile_damage", "medkits", "medkit_heal", "question_time", "question_damage", "questions" }) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mazeTable = new JTable();
		mazeTable.setModel(mazeTableModel);
		scrollPane.setViewportView(mazeTable);

		// Title label
		JLabel lblMazeManagement = new JLabel("Maze Management");
		lblMazeManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblMazeManagement.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMazeManagement.setBounds(31, 11, 806, 36);
		contentPane.add(lblMazeManagement);

		// Create Maze button
		JButton btnCreateMaze = new JButton("Create Maze");
		btnCreateMaze.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCreateMaze.setBackground(new Color(128, 255, 255));
		btnCreateMaze.setBounds(569, 58, 129, 33);
		contentPane.add(btnCreateMaze);

		// Delete Maze button
		btnDelete = new JButton("Delete Maze");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255, 128, 128));
		btnDelete.setEnabled(false);
		btnDelete.setBounds(708, 58, 129, 33);
		contentPane.add(btnDelete);

		// Exit button logic
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiController.changeView(MazeManagementUI.this, uiController.getLoginUI());
				resetWindow();
			}
		});

		// Create Maze logic
		btnCreateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelete.setEnabled(false);
				uiController.changeView(MazeManagementUI.this, uiController.getCreateMazeUI().getFrame());
			}
		});

		// Delete Maze logic
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = mazeTable.getSelectedRow();
				int mazeId = (int) mazeTable.getValueAt(selectedRow, 0);
				dbController.deleteMaze(mazeId);
				btnDelete.setEnabled(false);
				updateMazes();
			}
		});

		// Enable delete button when a row is selected
		mazeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mazeTable.getSelectedRow() != -1) {
					btnDelete.setEnabled(true);
				}
			}
		});
	}

	/**
	 * Loads all mazes from the database and inserts them into the table model.
	 * Called when the UI is opened or after a maze is deleted.
	 */
	public void updateMazes() {
		ResultSet rset = dbController.queryAll("mazes");

		// Clear existing rows
		if (mazeTableModel.getRowCount() > 0) {
			mazeTableModel.setRowCount(0);
		}

		try {
			while (rset.next()) {
				Object[] row = new Object[] { rset.getInt("id"), rset.getInt("width"), rset.getInt("height"),
						rset.getInt("num_crocodiles"), rset.getInt("crocodile_damage"), rset.getInt("num_medkits"),
						rset.getInt("medkit_heal"), rset.getInt("question_time"), rset.getInt("question_damage"),
						rset.getInt("num_questions") };
				mazeTableModel.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resets the UI to its default state.
	 * <ul>
	 * <li>Disables the delete button.</li>
	 * <li>Clears the selection from the table.</li>
	 * <li>Clears all maze rows from the table model.</li>
	 * </ul>
	 * Typically called when returning to the login screen.
	 */
	public void resetWindow() {
		btnDelete.setEnabled(false);
		mazeTable.clearSelection();

		if (mazeTableModel.getRowCount() > 0) {
			mazeTableModel.setRowCount(0);
		}
	}
}
