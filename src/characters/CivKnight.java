package characters;
/**
 * This class serves as the character Knight for the game and contains all necessary data
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivKnight extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	/** Cost of this character type */
	public static final int FIXED_COST = 6;
	
	/**
	 * Constructor. Creates an instance of CivKnight
	 */
	public CivKnight() {
		attack = 4;
		range = 2;
		movement = 3;
		cost = FIXED_COST;
		health = 12;
		name = "Knight";
	}
	
	/**
	 * Update the character stats when leveling up
	 */
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 1;
			movement += 1;
			max_health += 1;
		}
	}
}
