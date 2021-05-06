package characters;
/**
 * This class serves as the character Warrior for the game and contains all necessary data
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivWarrior extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	/** Cost of this character type */
	public static final int FIXED_COST = 2;
	
	/**
	 * Constructor. Creates an instance of CivWarrior
	 */
	public CivWarrior() {
		attack = 2;
		range = 1;
		movement = 1;
		cost = FIXED_COST;
		health = 7;
		name = "Warrior";
	}
	
	/**
	 * Update the character stats when leveling up
	 */
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 1;
			attack += 1;
		}
	}

}
