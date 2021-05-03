package characters;

import java.io.Serializable;

/**
 * 
 * This class represents a character in the game and contains all
 * their main attributes.
 *
 */
public class CivCharacter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected int attack;
	protected int range;
	protected int movement;
	protected int cost;
	protected int health;
	protected int lvl;
	protected String name;
	protected boolean isMoved;
	protected static final int MAX_LEVEL = 5;
	
	public CivCharacter() {
		// every character starts off at level 1
		isMoved = false;
		lvl = 1;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getLevel() {
		return lvl;
	}
	
	public boolean getIsMoved() {
		return isMoved;
	}
	
	public String getName() {
		return name;
	}
	
	public static int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void levelUp() {
	}
	
	public void setIsMoved(boolean value) {
		isMoved = value;
	}
	
}
