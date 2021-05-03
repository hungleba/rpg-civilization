package characters;

public class CivWarrior extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	public static final int FIXED_COST = 2;
	
	public CivWarrior() {
		attack = 2;
		range = 1;
		movement = 1;
		cost = FIXED_COST;
		health = 7;
		name = "Warrior";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 1;
			attack += 1;
		}
	}

}
