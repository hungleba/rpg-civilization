package characters;

public class CivCatapult extends CivCharacter{
	
	public CivCatapult() {
		attack = 4;
		range = 4;
		movement = 2;
		cost = 7;
		health = 10;
		name = "Catapult";
		fixedCost = 7;
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			attack += 1;
		}
	}
}
