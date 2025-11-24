package model;

import java.util.List;

public class Question {
    private String statement;
    private List<String> options; // 4 options: a, b, c, d
    private int correctIndex;     // 0=a, 1=b, 2=c, 3=d

    public Question(String statement, List<String> options, int correctIndex) {
        this.statement = statement;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getStatement() {
        return statement;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getCorrectAnswer() {
        return options.get(correctIndex);
    }
}
