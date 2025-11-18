package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Model;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResultsMazeUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Top 10
    private DefaultTableModel statsModel;
    private JTable statsTable;

    private Model logicModel;
    private int mazeId, layoutId;

    // Dynamic labels
    private JLabel lblUserValue;
    private JLabel lblPointsValue;
    private JLabel lblLifeValue;
    private JLabel lblTimeValue;
    
    private int[][] matrix;

    public ResultsMazeUI(String username, int correctAnswers, int incorrectAnswers,
                               int finalLife, int points, String time,
                               boolean victory, Model model, int mazeId,
                               int layoutId, ChooseMazeUI chooseMazeUI, int[][] matrix) {
        this.logicModel   = model;
        this.mazeId       = mazeId;
        this.layoutId     = layoutId;
        this.matrix       = matrix;

        setTitle("Game Summary");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 486);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ——— Game summary panel (left) ———
        JPanel summaryPanel = new JPanel(null);
        summaryPanel.setBounds(10, 58, 300, 300);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        contentPane.add(summaryPanel);

        JLabel lblResult = new JLabel(victory ? "You won!" : "You lost");
        lblResult.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblResult.setBounds(80, 20, 150, 25);
        summaryPanel.add(lblResult);
        
        // Fixed labels
        JLabel lblUser = new JLabel("User:");
        lblUser.setBounds(10, 60, 80, 14);
        summaryPanel.add(lblUser);
        lblUserValue = new JLabel(username);
        lblUserValue.setBounds(100, 60, 180, 14);
        summaryPanel.add(lblUserValue);

        JLabel lblPoints = new JLabel("Points:");
        lblPoints.setBounds(10, 90, 80, 14);
        summaryPanel.add(lblPoints);
        lblPointsValue = new JLabel(String.valueOf(points));
        lblPointsValue.setBounds(100, 90, 180, 14);
        summaryPanel.add(lblPointsValue);

        JLabel lblLife = new JLabel("Life:");
        lblLife.setBounds(10, 120, 80, 14);
        summaryPanel.add(lblLife);
        lblLifeValue = new JLabel(String.valueOf(finalLife));
        lblLifeValue.setBounds(100, 120, 180, 14);
        summaryPanel.add(lblLifeValue);

        JLabel lblTime = new JLabel("Time:");
        lblTime.setBounds(10, 150, 80, 14);
        summaryPanel.add(lblTime);
        lblTimeValue = new JLabel(time);
        lblTimeValue.setBounds(100, 150, 180, 14);
        summaryPanel.add(lblTimeValue);

        // Question details
        JLabel lblCorrect = new JLabel("Correct answers:");
        lblCorrect.setBounds(10, 180, 150, 14);
        summaryPanel.add(lblCorrect);
        JTextField tfCorrect = new JTextField(String.valueOf(correctAnswers));
        tfCorrect.setBounds(160, 180, 50, 20);
        tfCorrect.setEditable(false);
        summaryPanel.add(tfCorrect);

        JLabel lblIncorrect = new JLabel("Incorrect answers:");
        lblIncorrect.setBounds(10, 210, 150, 14);
        summaryPanel.add(lblIncorrect);
        JTextField tfIncorrect = new JTextField(String.valueOf(incorrectAnswers));
        tfIncorrect.setBounds(160, 210, 50, 20);
        tfIncorrect.setEditable(false);
        summaryPanel.add(tfIncorrect);

        // ——— Top 10 stats panel (right) ———
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBounds(330, 10, 350, 370);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Top 10 Players"));
        contentPane.add(statsPanel);

        statsModel = new DefaultTableModel(
            new Object[] { "User", "Points", "Time", "Life", "Victory" }, 0
        );
        statsTable = new JTable(statsModel);
        statsPanel.add(new JScrollPane(statsTable), BorderLayout.CENTER);
        
        JButton btnBack = new JButton("Back");
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnBack.setBackground(Color.GRAY);
        btnBack.setBounds(10, 402, 99, 34);
        contentPane.add(btnBack);
        
        JButton btnShowLayout = new JButton("Show layout");
        btnShowLayout.setBounds(119, 402, 147, 34);
        contentPane.add(btnShowLayout);

        loadStats();
        
        // Back button click
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                chooseMazeUI.resetWindow();
                chooseMazeUI.setVisible(true);
            }
        });
        
        // Show matrix button
        btnShowLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printMatrix();
            }
        });
    }

    private void loadStats() {
        statsModel.setRowCount(0);
        try (ResultSet rs = logicModel.getTop10(mazeId, layoutId)) {
            while (rs != null && rs.next()) {
                statsModel.addRow(new Object[] {
                    rs.getString("usuario"),
                    rs.getInt("puntos"),
                    rs.getString("tiempo"),
                    rs.getInt("vida"),
                    rs.getBoolean("victoria") ? "Yes" : "No"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading statistics",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void printMatrix() {
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        for (int y = 0; y < rows + 2; y++) {
            for (int x = 0; x < columns + 2; x++) {
                // Outer borders
                if (y == 0 || y == rows + 1 || x == 0 || x == columns + 1) {
                    System.out.print("#");  // border wall
                } else {
                    int mapY = y - 1;
                    int mapX = x - 1;
                    
                    // Top-left corner (start)
                    if (mapY == 0 && mapX == 0) {
                        System.out.print("O");
                    } 
                    // Bottom-right corner (exit)
                    else if (mapY == rows - 1 && mapX == columns - 1) {
                        System.out.print("X");
                    } 
                    else {
                        // Other cells according to map
                        switch (matrix[mapY][mapX]) {
                            case 0: System.out.print(" "); break;    // empty
                            case 1: System.out.print("M"); break;    // medkit
                            case 2: System.out.print("C"); break;    // crocodile
                            case 3: System.out.print("#"); break;    // wall
                            default: System.out.print("?"); break;   // other
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
