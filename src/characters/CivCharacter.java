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
	/** Attach power of the character */
	protected int attack;
	/** Attack range that can be reached by the character */
	protected int range;
	/** Range of movement of the character */
	protected int movement;
	/** Cost of the character */
	protected int cost;
	/** Health of the character (Zero health means the character is removed) */
	protected int health;
	/** Current level of the character */
	protected int lvl;
	/** Name (type) of the character */
	protected String name;
	/** Status of the character in a player's turn (moved/not yet moved) */
	protected boolean isMoved;
	/** Maximum level that can be attained by the character */
	protected static final int MAX_LEVEL = 5;
	/** Maximum health that can be attained by the character */
	
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
	 * Update a character's health
	 * @param health the health to be set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/** 
	 * Update stats when leveling up a character
	 */
	public void levelUp() {
	}

	/** 
	 * Update a character status of being moved or not
	 * @param value the status to be set (T/F)
	 */
	public void setIsMoved(boolean value) {
		isMoved = value;
	}
	
}
