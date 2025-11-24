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

import logic.Controller;
import logic.DBController;
import logic.UIController;
import model.User;

/**
 * The LoginUI class represents the login window of the application.
 * It allows users to log in with their credentials or create a new account.
 */
public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCreateUser;

    private User user;
    private DBController dbController;
    private UIController uiController;
    private Controller controller;

    /**
     * Constructs the LoginUI window and initializes UI components.
     * Sets up event listeners and configures the layout.
     */
    public LoginUI() {

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

        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Initializes the UI with the given controller.
     *
     * @param controller The main controller of the application.
     */
    public void initialize(Controller controller) {
        this.controller = controller;
        dbController = controller.getDbController();
        uiController = controller.getUiController();
    }

    /**
     * Attempts to log in the user using the provided username and password.
     * Validates fields, checks for admin login, queries the database,
     * and performs UI navigation based on the result.
     */
    public void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (!validateUsername(username) || !validatePassword(password))
            return;

        // Admin login
        if (username.equals("admin") && password.equals("admin")) {
            MazeManagementUI mazeManagement = uiController.getMazeManagementUI();
            mazeManagement.updateMazes();
            uiController.changeView(LoginUI.this, mazeManagement);

        } else {
            try {
                ResultSet rs = dbController.queryUser(username, password);

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    username = rs.getString("name");

                    user = new User(username, userId);
                    controller.setUser(user);

                    JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + "!");

                    ChooseMazeUI chooseMaze = uiController.getChooseMazeUI();
                    chooseMaze.loadWindow(dbController);
                    uiController.changeView(LoginUI.this, chooseMaze);

                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect username or password.");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error while logging in.");
                e.printStackTrace();
            }
        }

        clearFields();
    }

    /**
     * Allows the user to create a new account by entering a username
     * and password through input dialogs.
     * Validates the input and registers the new user in the database.
     */
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

        User user = dbController.insertUser(newUser, newPassword);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "User " + user.getName() + " created with ID: " + user.getId());
        } else {
            JOptionPane.showMessageDialog(this, "Error creating user.");
        }
    }

    /**
     * Validates that the username is not null or empty.
     *
     * @param username The username to validate.
     * @return true if valid, false otherwise.
     */
    private boolean validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.");
            return false;
        }
        return true;
    }

    /**
     * Validates that the password is not null or empty.
     *
     * @param password The password to validate.
     * @return true if valid, false otherwise.
     */
    private boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return false;
        }
        return true;
    }

    /**
     * Returns the current value inside the username input field.
     *
     * @return The username typed by the user.
     */
    public String getUsername() {
        return txtUsername.getText();
    }

    /**
     * Clears the username and password input fields.
     */
    public void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }
}
