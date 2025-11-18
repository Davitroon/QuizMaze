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

import dao.DBConnector;

public class MazeManagementUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable mazeTable;
	private DefaultTableModel mazeTableModel;

	private DBConnector model;
	private CreateMazeUI createMaze;
	private JButton btnDelete;

	@SuppressWarnings("serial")
	public MazeManagementUI(LoginUI login, DBConnector model) {
		this.model = model;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 404);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExit.setBackground(new Color(128, 128, 128));
		btnExit.setBounds(31, 321, 94, 33);
		contentPane.add(btnExit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 102, 806, 208);
		contentPane.add(scrollPane);

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

		JLabel lblMazeManagement = new JLabel("Maze Management");
		lblMazeManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblMazeManagement.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMazeManagement.setBounds(31, 11, 806, 36);
		contentPane.add(lblMazeManagement);

		JButton btnCreateMaze = new JButton("Create Maze");
		btnCreateMaze.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCreateMaze.setBackground(new Color(128, 255, 255));
		btnCreateMaze.setBounds(569, 58, 129, 33);
		contentPane.add(btnCreateMaze);

		btnDelete = new JButton("Delete Maze");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255, 128, 128));
		btnDelete.setEnabled(false);
		btnDelete.setBounds(708, 58, 129, 33);
		contentPane.add(btnDelete);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetWindow();
				dispose();
				login.login();
			}
		});

		btnCreateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createMaze == null) {
					createMaze = new CreateMazeUI(MazeManagementUI.this, model);
				}
				createMaze.getFrame().setVisible(true);
				btnDelete.setEnabled(false);
				setVisible(false);
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = mazeTable.getSelectedRow();
				int mazeId = (int) mazeTable.getValueAt(selectedRow, 0);
				model.deleteMaze(mazeId);
				btnDelete.setEnabled(false);
				updateMazes();
			}
		});

		mazeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = mazeTable.getSelectedRow();
				if (selectedRow != -1) {
					btnDelete.setEnabled(true);
				}
			}
		});
	}

	public void updateMazes() {
		ResultSet rset = model.queryAll("mazes");

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

	public void resetWindow() {
		btnDelete.setEnabled(false);
		mazeTable.clearSelection();

		if (mazeTableModel.getRowCount() > 0) {
			mazeTableModel.setRowCount(0);
		}
	}
}
