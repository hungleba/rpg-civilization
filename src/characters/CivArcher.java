package characters;
/**
 * This class serves as the character Archer for the game and contains all necessary data
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivArcher extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	/** Cost of this character type */
	public static final int FIXED_COST = 3;
	
	/**
	 * Constructor. Creates an instance of CivArcher
	 */
	public CivArcher() {
		attack = 2;
		range = 2;
		movement = 1;
		cost = FIXED_COST;
		health = 5;
		name = "Archer";
	}
	
	/**
	 * Change the character stats when leveling up
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
