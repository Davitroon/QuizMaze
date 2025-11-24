package model;

/**
 * Represents a player in the maze game.
 */
public class User {

    private int id;
    private String name;
    private int x = 0, y = 0;
    private int health = 100;
    private int points = 0;

    /**
     * Constructs a User with the given name and ID.
     *
     * @param name The username.
     * @param id   The user ID.
     */
    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** Returns the user's name. */
    public String getName() {
        return name;
    }

    /** Sets the user's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the X coordinate of the user in the maze. */
    public int getX() {
        return x;
    }

    /** Sets the X coordinate of the user in the maze. */
    public void setX(int x) {
        this.x = x;
    }

    /** Returns the Y coordinate of the user in the maze. */
    public int getY() {
        return y;
    }

    /** Sets the Y coordinate of the user in the maze. */
    public void setY(int y) {
        this.y = y;
    }

    /** Returns the current health of the user. */
    public int getHealth() {
        return health;
    }

    /** Sets the user's health. */
    public void setHealth(int health) {
        this.health = health;
    }

    /** Returns the user's current points. */
    public int getPoints() {
        return points;
    }

    /** Sets the user's points. */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Moves the user to a new position in the maze.
     *
     * @param x The new X coordinate.
     * @param y The new Y coordinate.
     */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Reduces the user's health by a specified amount.
     * Health will not drop below zero.
     *
     * @param amount Amount of health to reduce.
     */
    public void reduceHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Heals the user by a specified amount.
     * Health cannot exceed 100; any excess is returned.
     *
     * @param amount Amount to heal.
     * @return The excess health that could not be applied.
     */
    public int heal(int amount) {
        this.health += amount;
        if (this.health > 100) {
            int excess = this.health - 100;
            this.health = 100;
            return excess;
        }
        return 0;
    }

    /**
     * Adds points to the user's total.
     *
     * @param amount Points to add.
     */
    public void addPoints(int amount) {
        this.points += amount;
    }

    /** Returns the user's ID. */
    public int getId() {
        return id;
    }

    /** Sets the user's ID. */
    public void setId(int id) {
        this.id = id;
    }
}
