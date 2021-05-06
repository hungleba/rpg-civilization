package characters;
/**
 * This class serves as the character Catapult for the game and contains all necessary data
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivCatapult extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	/** Cost of this character type */
	public static final int FIXED_COST = 7;
	
	/**
	 * Constructor. Creates an instance of CivCatapult
	 */
	public CivCatapult() {
		attack = 4;
		range = 4;
		movement = 2;
		cost = FIXED_COST;
		health = 10;
		name = "Catapult";
	}
	
	/**
	 * Update the character stats when leveling up
	 */
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			attack += 1;
			health += 1;
		}
	}

}
