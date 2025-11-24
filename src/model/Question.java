package model;

import java.util.List;

/**
 * Represents a multiple-choice question with four options.
 */
public class Question {

    private String statement;
    private List<String> options; // 4 options: a, b, c, d
    private int correctIndex;     // 0=a, 1=b, 2=c, 3=d

    /**
     * Constructs a Question with the given statement, options, and correct answer index.
     * 
     * @param statement     The text of the question.
     * @param options       List of four possible answers.
     * @param correctIndex  Index of the correct answer in the options list (0-3).
     */
    public Question(String statement, List<String> options, int correctIndex) {
        this.statement = statement;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    /**
     * Returns the question statement.
     * 
     * @return The question text.
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Returns the list of answer options.
     * 
     * @return A list of four possible answers.
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Returns the index of the correct answer in the options list.
     * 
     * @return Index of the correct answer (0-3).
     */
    public int getCorrectIndex() {
        return correctIndex;
    }

    /**
     * Returns the correct answer as a string.
     * 
     * @return The correct option text.
     */
    public String getCorrectAnswer() {
        return options.get(correctIndex);
    }
}
