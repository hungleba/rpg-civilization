package characters;

public class CivGuard extends CivCharacter{
	
	public CivGuard() {
		attack = 0;
		range = 0;
		movement = 1;
		cost = 5;
		health = 14;
		name = "Guard";
		fixedCost = 5;
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 2;
		}
	}
}
