import java.util.HashMap;
import java.util.List;

import characters.CivCharacter;

public class CivPlayer {
	private int MAX_UNITS = 10;
	private int unitCount;
	private CivCharacter[] units; 
	private CivCountry country; 
	private HashMap<String, List<Integer>> unitMap;
	
	
	public CivPlayer() {
		unitMap = new HashMap<String, List<Integer>>();
		units = new CivCharacter[MAX_UNITS];
		unitCount = 0;
	}
	
	public void setCountry(CivCountry country) {
		this.country = country;
	}
	
	public CivCountry getCountry() {
		return country;
	}
	
	public int getUnitCount() {
		return unitCount;
	}
	
}
