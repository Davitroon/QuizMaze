package model;

public class Player {

	private int id;
	private String name;
	private int x, y;
	private int health;
	private int points;

	public Player(String name) {
		this.name = name;
		this.health = 100;
		this.points = 0;
		this.x = 0;
		this.y = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void reduceHealth(int amount) {
		health -= amount;
	}

	public int heal(int amount) {
		this.health += amount;
		if (this.health > 100) {
			int excess = this.health - 100;
			this.health = 100;
			return excess;
		}
		return 0;
	}

	public void addPoints(int amount) {
		this.points += amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
