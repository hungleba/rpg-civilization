import characters.CivCharacter;

public class CivPlayer {
	private int MAX_UNITS = 10;
	private int unitCount;
	private CivCharacter[] units; 
	private CivCountry country; 
	
	
	public CivPlayer() {
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
