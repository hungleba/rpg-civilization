package characters;

public class CivGuard extends CivCharacter{
	
	private static final long serialVersionUID = 1L;
	public static final int FIXED_COST = 5;
	
	public CivGuard() {
		attack = 0;
		range = 0;
		movement = 1;
		cost = FIXED_COST;
		health = 14;
		name = "Guard";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			health += 2;
		}
	}

}
