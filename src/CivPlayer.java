import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import characters.CivCharacter;

public class CivPlayer {
	private static final int DIMENSION = 10;
	private static final int INITIAL_GOLD = 10;
	private int unitCount;
	private CivCountry country;
	private Map<String, List<CivCharacter>> unitMap;
	private Map<CivCharacter, Integer> positionMap;
	private String name;
	private int gold;

	public CivPlayer(String name) {
		unitMap = new HashMap<String, List<CivCharacter>>();
		unitMap.put("Archer", new ArrayList<CivCharacter>());
		unitMap.put("Catapult", new ArrayList<CivCharacter>());
		unitMap.put("Guard", new ArrayList<CivCharacter>());
		unitMap.put("Knight", new ArrayList<CivCharacter>());
		unitMap.put("Warrior", new ArrayList<CivCharacter>());
		positionMap = new HashMap<CivCharacter, Integer>();
		unitCount = 0;
		this.name = name;
		gold = INITIAL_GOLD;
	}

	public void setCountry(CivCountry country) {
		this.country = country;
	}

	public String getCountry() {
		return country.getName();
	}

	public int getUnitCount() {
		return unitCount;
	}

	public String getName() {
		return name;
	}

	public int getGold() {
		return gold;
	}
	
	public void addGold(int add) {
		gold += add;
	}
	
	public Map<CivCharacter, Integer> getPositionMap() {
		return positionMap;
	}
	
	public Map<String, List<CivCharacter>> getUnitMap() {
		return unitMap;
	}

	public void addUnit(CivCharacter character, int row, int col) {
		List<CivCharacter> characters = unitMap.get(character.getName());
		characters.add(character);
		positionMap.put(character, row * DIMENSION + col);
		unitCount++;
		gold -= character.getCost();

	}

	public void removeUnit(CivCharacter character) {
		List<CivCharacter> characters = unitMap.get(character.getName());
		characters.remove(character);
		positionMap.remove(character);
		unitCount--;
	}
	
	public void updateUnit(CivCharacter character, int row, int col) {
		positionMap.put(character, row * DIMENSION + col);
	}	

}
