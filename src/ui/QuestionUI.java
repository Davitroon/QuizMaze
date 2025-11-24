package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Question;

/**
 * A dialog window used to present a timed multiple-choice question to the user.
 */
@SuppressWarnings("serial")
public class QuestionUI extends JDialog {

    private boolean correct;
    private int attempts = 0;

    /**
     * Creates and displays a modal question dialog with a countdown timer.
     *
     * @param parent            the parent frame to center the dialog over
     * @param question          the {@link Question} object containing the text,
     *                          options, and correct answer
     * @param timeLimitSeconds  the time limit in seconds before the question
     *                          automatically closes
     */
    public QuestionUI(JFrame parent, Question question, int timeLimitSeconds) {
        super(parent, "Question", true);
        correct = false;

        JButton[] optionButtons = new JButton[4];

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel questionLabel =
                new JLabel("<html><body style='width:300px'>" +
                           question.getStatement() +
                           "</body></html>");
        panel.add(questionLabel);

        JLabel timeLabel = new JLabel("Time: " + timeLimitSeconds + " seconds");
        panel.add(timeLabel);

        List<String> options = new ArrayList<>(question.getOptions());
        Collections.shuffle(options);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton(options.get(i));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(optionButtons[i]);
            optionButtons[i].addActionListener(e -> checkAnswer(e, question));
        }

        this.getContentPane().add(panel);
        this.pack();
        this.setLocationRelativeTo(parent);

        Timer countdown = new Timer(1000, null);
        final int[] remainingTime = { timeLimitSeconds };

        countdown.addActionListener(e -> {
            remainingTime[0]--;
            timeLabel.setText("Time: " + remainingTime[0] + " seconds");

            if (remainingTime[0] <= 0) {
                countdown.stop();
                dispose();
            }
        });

        countdown.start();
        this.setVisible(true);
    }

    /**
     * Handles the logic of verifying whether the clicked option is correct.
     *
     * @param e         the button click event
     * @param question  the current {@link Question} being answered
     */
    private void checkAnswer(ActionEvent e, Question question) {
        JButton clicked = (JButton) e.getSource();
        attempts++;

        if (clicked.getText().equalsIgnoreCase(question.getCorrectAnswer())) {
            correct = true;
            dispose();
        } else if (attempts >= 3) {
            JOptionPane.showMessageDialog(this, "You failed 3 times!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect. Attempt " + attempts + " of 3.");
        }
    }

    /**
     * Returns whether the question was answered correctly.
     *
     * @return {@code true} if the user selected the correct answer,
     *         {@code false} otherwise
     */
    public boolean isCorrect() {
        return correct;
    }
}
