package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import logic.Model;
import logic.Player;

public class LoginUI extends JFrame {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnCreateUser;

	private Model model;
	private Player player;
	private ChooseMazeUI chooseMaze;
	private MazeManagementUI mazeManagement;

	public LoginUI(Model model) {
		this.model = model;
		setTitle("Login");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		initComponents();
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblUsername = new JLabel("Username:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lblUsername, gbc);

		txtUsername = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(txtUsername, gbc);

		JLabel lblPassword = new JLabel("Password:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(lblPassword, gbc);

		txtPassword = new JPasswordField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(txtPassword, gbc);

		btnLogin = new JButton("Log In");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(btnLogin, gbc);

		btnCreateUser = new JButton("Create User");
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(btnCreateUser, gbc);

		add(panel);

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		btnCreateUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createUser();
			}
		});
	}

	public void login() {
		String username = txtUsername.getText().trim();
		String password = new String(txtPassword.getPassword());

		if (!validateUsername(username) || !validatePassword(password))
			return;

		try {
			ResultSet rs = model.queryUser(username, password);
			if (rs.next()) {
				int userId = rs.getInt("id");
				String user = rs.getString("name");

				player = new Player(user);
				player.setId(userId);

				JOptionPane.showMessageDialog(this, "Welcome " + user + "!");

				if (user.equals("admin")) {
					if (mazeManagement == null) {
						mazeManagement = new MazeManagementUI(null, model);
					}
					mazeManagement.updateMazes();
					mazeManagement.setVisible(true);
				} else {
					if (chooseMaze == null) {
						chooseMaze = new ChooseMazeUI(null, model, player);
					}
					chooseMaze.loadMazes();
					chooseMaze.setVisible(true);
				}

				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Incorrect username or password.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error while logging in.");
			e.printStackTrace();
		}
	}

	private void createUser() {
		String newUser = JOptionPane.showInputDialog(this, "New username:");
		if (newUser == null || !validateUsername(newUser))
			return;

		String newPassword = JOptionPane.showInputDialog(this, "Password:");
		if (newPassword == null || !validatePassword(newPassword))
			return;

		String repeatedPassword = JOptionPane.showInputDialog(this, "Repeat password:");
		if (repeatedPassword == null || !validatePassword(repeatedPassword))
			return;

		if (!newPassword.equals(repeatedPassword)) {
			JOptionPane.showMessageDialog(this, "Passwords do not match.");
			return;
		}

		int playerId = model.insertUser(newUser, newPassword);
		if (playerId != -1) {
			player = new Player(newUser);
			player.setId(playerId);
			JOptionPane.showMessageDialog(this, "User " + newUser + " created with ID: " + playerId);
		} else {
			JOptionPane.showMessageDialog(this, "Error creating user.");
		}
	}

	private boolean validateUsername(String username) {
		if (username == null || username.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Username cannot be empty.");
			return false;
		}
		return true;
	}

	private boolean validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Password cannot be empty.");
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		Model model = new Model();
		SwingUtilities.invokeLater(() -> {
			new LoginUI(model).setVisible(true);
		});
	}

	public String getUsername() {
		return txtUsername.getText();
	}
}
