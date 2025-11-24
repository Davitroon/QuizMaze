package model;

public class User {

	private int id;
	private String name;
	private int x, y = 0;
	private int health  = 100;
	private int points = 0;

	public User(String userName, int userId) {
		this.name = userName;
		this.id = userId;
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
		
		if (health < 0) {
			health = 0;
		}
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
