package models;

import model.UserBase;

public class Player extends UserBase{
	private int rounds;
	private int points;
	
	public Player(int id, String name, String pass, String role, int rounds, int points) {
		super(id, name, pass, role);
		this.rounds = rounds;
		this.points = points;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	
	
}
