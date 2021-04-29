package characters;

public class CivKnight extends CivCharacter{
	
	public static final int FIXED_COST = 6;
	
	public CivKnight() {
		attack = 4;
		range = 2;
		movement = 3;
		cost = FIXED_COST;
		health = 12;
		name = "Knight";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 1;
			movement += 1;
		}
	}
}
