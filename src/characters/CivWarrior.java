package characters;

public class CivWarrior extends CivCharacter{
	
	public CivWarrior() {
		attack = 2;
		range = 1;
		movement = 1;
		cost = 2;
		health = 7;
		name = "Warrior";
		fixedCost = 2;
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 1;
		}
	}
}
