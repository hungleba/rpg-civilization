package characters;
/**
 * 
 * This class represents a character in the game and contains all
 * their main attributes.
 *
 */
public class CivCharacter {
	protected int attack;
	protected int range;
	protected int movement;
	protected int cost;
	protected int health;
	protected int lvl;
	protected String name;
	protected static final int MAX_LEVEL = 5;
	
	public CivCharacter() {
		// every character starts off at level 1
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
	
	public String getName() {
		return name;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void levelUp() {
	}
	
}
