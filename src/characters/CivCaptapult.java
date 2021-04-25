package characters;

public class CivCaptapult extends CivCharacter{
	
	public CivCaptapult() {
		attack = 4;
		range = 4;
		movement = 2;
		cost = 7;
		health = 10;
		name = "Catapult";
	}
	
	@Override
	public void levelUp() {
		if (lvl < MAX_LEVEL) {
			lvl += 1;
			attack += 1;
		}
	}
}
