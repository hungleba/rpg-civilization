package characters;

public class CivArcher extends CivCharacter{
	
	public CivArcher() {
		attack = 2;
		range = 2;
		movement = 1;
		cost = 3;
		health = 5;
		name = "Archer";
		fixedCost = 3;
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			attack += 1;
		}
	}
}
