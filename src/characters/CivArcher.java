package characters;

public class CivArcher extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	public static final int FIXED_COST = 3;
	
	public CivArcher() {
		attack = 2;
		range = 2;
		movement = 1;
		cost = FIXED_COST;
		health = 5;
		name = "Archer";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			attack += 1;
			health += 1;
		}
	}
}
