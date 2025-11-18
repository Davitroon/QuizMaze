package model;

public class Question {
	private String statement;
	private String correctAnswer;
	private String hint;

	public Question(String statement, String correctAnswer, String hint) {
		this.statement = statement;
		this.correctAnswer = correctAnswer;
		this.hint = hint;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
}
