package characters;
/**
 * This class serves as the character Guard for the game and contains all necessary data
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivGuard extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	/** Cost of this character type */
	public static final int FIXED_COST = 5;
	
	/**
	 * Constructor. Creates an instance of CivGuard
	 */
	public CivGuard() {
		attack = 0;
		range = 0;
		movement = 1;
		cost = FIXED_COST;
		health = 14;
		name = "Guard";
	}
	
	/**
	 * Update the character stats when leveling up
	 */
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 2;
		}
	}

}
