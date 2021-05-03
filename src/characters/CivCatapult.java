package characters;

public class CivCatapult extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	public static final int FIXED_COST = 7;
	
	public CivCatapult() {
		attack = 4;
		range = 4;
		movement = 2;
		cost = FIXED_COST;
		health = 10;
		name = "Catapult";
		
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
