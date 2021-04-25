package characters;

public class CivKnight extends CivCharacter{
	
	public CivKnight() {
		attack = 4;
		range = 2;
		movement = 3;
		cost = 6;
		health = 12;
		name = "Knight";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			movement += 1;
		}
	}
}
