package model;

/**
 * Represents a Maze created by the user. 
 * Stores maze dimensions, game elements, and question-related settings.
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

    /**
     * Constructs a Maze with specified parameters.
     * 
     * @param width           Width of the maze.
     * @param height          Height of the maze.
     * @param num_crocodiles  Number of crocodiles in the maze.
     * @param crocodile_damage Damage each crocodile causes.
     * @param num_medkits     Number of medkits in the maze.
     * @param medkit_health   Amount of health each medkit restores.
     * @param question_time   Time allowed for each question.
     * @param question_damage Damage taken for wrong answers.
     * @param num_questions   Number of questions in the maze.
     * @param map             Initial maze matrix.
     */
    public Maze(int width, int height, int num_crocodiles, int crocodile_damage, int num_medkits, int medkit_health,
                int question_time, int question_damage, int num_questions, int[][] map) {
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
     * Generates a new random disposition within the maze.
     * The disposition matrix will place medkits and crocodiles randomly.
     */
    public void generateDisposition() {
        Disposition disposition = new Disposition(map, this.getId());
        disposition.generateMatrix(numMedkits, numCrocodiles);
    }

    /** Returns the width of the maze. */
    public int getWidth() {
        return width;
    }

    /** Sets the width of the maze. */
    public void setWidth(int width) {
        this.width = width;
    }

    /** Returns the height of the maze. */
    public int getHeight() {
        return height;
    }

    /** Sets the height of the maze. */
    public void setHeight(int height) {
        this.height = height;
    }

    /** Returns the number of crocodiles in the maze. */
    public int getNumCrocodiles() {
        return numCrocodiles;
    }

    /** Sets the number of crocodiles in the maze. */
    public void setNumCrocodiles(int numCrocodiles) {
        this.numCrocodiles = numCrocodiles;
    }

    /** Returns the damage each crocodile inflicts. */
    public int getCrocodileDamage() {
        return crocodileDamage;
    }

    /** Sets the damage each crocodile inflicts. */
    public void setCrocodileDamage(int crocodileDamage) {
        this.crocodileDamage = crocodileDamage;
    }

    /** Returns the number of medkits in the maze. */
    public int getNumMedkits() {
        return numMedkits;
    }

    /** Sets the number of medkits in the maze. */
    public void setNumMedkits(int numMedkits) {
        this.numMedkits = numMedkits;
    }

    /** Returns the health restored by each medkit. */
    public int getMedkitHealth() {
        return medkitHealth;
    }

    /** Sets the health restored by each medkit. */
    public void setMedkitHealth(int medkitHealth) {
        this.medkitHealth = medkitHealth;
    }

    /** Returns the time allowed for each question. */
    public int getQuestionTime() {
        return questionTime;
    }

    /** Sets the time allowed for each question. */
    public void setQuestionTime(int questionTime) {
        this.questionTime = questionTime;
    }

    /** Returns the damage taken for incorrect answers. */
    public int getQuestionDamage() {
        return questionDamage;
    }

    /** Sets the damage taken for incorrect answers. */
    public void setQuestionDamage(int questionDamage) {
        this.questionDamage = questionDamage;
    }

    /** Returns the number of questions in the maze. */
    public int getNumQuestions() {
        return numQuestions;
    }

    /** Sets the number of questions in the maze. */
    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    /** Returns the ID of the maze. */
    public int getId() {
        return id;
    }

    /** Sets the ID of the maze. */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the maze's map matrix. */
    public int[][] getMap() {
        return map;
    }
}
