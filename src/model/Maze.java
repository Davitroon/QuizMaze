package model;

import dao.DBConnector;

/**
 * Maze class that the user will create.
 */
public class Maze {
	
	private int id;
	private int width;
	private int height;
	private int numCrocodiles;
	private int crocodileDamage;
	private int numMedkits;
	private int medkitHealth;
	private int questionTime;
	private int questionDamage;
	private int numQuestions;
	
	private int[][] map;
	
	private DBConnector model;

	public Maze(int width, int height, int num_crocodiles, int crocodile_damage, int num_medkits,
			int medkit_health, int question_time, int question_damage, int num_questions, int[][] map) {
		this.width = width;
		this.height = height;
		this.numCrocodiles = num_crocodiles;
		this.crocodileDamage = crocodile_damage;
		this.numMedkits = num_medkits;
		this.medkitHealth = medkit_health;
		this.questionTime = question_time;
		this.questionDamage = question_damage;
		this.numQuestions = num_questions;
		this.map = map;
	}
	
	
	/**
	 * Generate a new random disposition.
	 */
	public void generateDisposition() {
	
		Disposition disposition = new Disposition(map, this.getId(), model);
		disposition.generateMatrix(numMedkits, numCrocodiles);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNumCrocodiles() {
		return numCrocodiles;
	}

	public void setNumCrocodiles(int numCrocodiles) {
		this.numCrocodiles = numCrocodiles;
	}

	public int getCrocodileDamage() {
		return crocodileDamage;
	}

	public void setCrocodileDamage(int crocodileDamage) {
		this.crocodileDamage = crocodileDamage;
	}

	public int getNumMedkits() {
		return numMedkits;
	}

	public void setNumMedkits(int numMedkits) {
		this.numMedkits = numMedkits;
	}

	public int getMedkitHealth() {
		return medkitHealth;
	}

	public void setMedkitHealth(int medkitHealth) {
		this.medkitHealth = medkitHealth;
	}

	public int getQuestionTime() {
		return questionTime;
	}

	public void setQuestionTime(int questionTime) {
		this.questionTime = questionTime;
	}

	public int getQuestionDamage() {
		return questionDamage;
	}

	public void setQuestionDamage(int questionDamage) {
		this.questionDamage = questionDamage;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[][] getMap() {
		return map;
	}
}
