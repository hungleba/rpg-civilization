package characters;

import java.io.Serializable;

/**
 * 
 * This class represents a character in the game and contains all
 * their main attributes.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
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
	
	/**
	 * Constructor. Creates an instance of CivCharacter
	 */
	public CivCharacter() {
		// every character starts off at level 1
		isMoved = false;
		lvl = 1;
	}
	
	/** 
	 * Return how much damage a character can cause in one attack
	 * @return character's attack stat
	 */
	public int getAttack() {
		return attack;
	}
	
	/** 
	 * Return how far a character can move in one turn
	 * @return character's movement stat
	 */
	public int getMovement() {
		return movement;
	}
	
	/** 
	 * Return how far a character can reach in one attack
	 * @return character's range stat
	 */
	public int getRange() {
		return range;
	}
	
	/** 
	 * Return how much a character costs
	 * @return character's cost
	 */
	public int getCost() {
		return cost;
	}
	
	/** 
	 * Return how much health a character has left
	 * @return character's health 
	 */
	public int getHealth() {
		return health;
	}
	
	/** 
	 * Return the character's level
	 * @return character's level
	 */
	public int getLevel() {
		return lvl;
	}
	
	/** 
	 * Check if the character is moved in a player's turn
	 * @return true if it is moved and false otherwise
	 */
	public boolean getIsMoved() {
		return isMoved;
	}
	
	/** 
	 * Return name (type) of the character
	 * @return character's name
	 */
	public String getName() {
		return name;
	}
	
	/** 
	 * Return the highest level that a character can reach
	 * @return character's max lvl
	 */
	public static int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	/** 
	 * Change a character's health
	 * @param health the health to be set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/** 
	 * Change stats when leveling up a character
	 */
	public void levelUp() {
	}

	/** 
	 * Change a character status of being moved or not
	 * @param value the status to be set (T/F)
	 */
	public void setIsMoved(boolean value) {
		isMoved = value;
	}
	
}
