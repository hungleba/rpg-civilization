
public class CivModel {
	
	private String[][] boardArr; 
	private final int DIMENSION = 10; // the dimensions of the the map
	private int curUnits; // the current number of units on the map
	private CivPlayer human;
	private CivPlayer computer;
	
	
	public CivModel() {
		human = new CivPlayer();
		computer = new CivPlayer();
		
		boardArr = new String[DIMENSION][DIMENSION];
		
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				boardArr[i][j] = "o";
			}
		}
		curUnits = 0;
	}
	
	public int getCurUnits() {
		return curUnits;
	}
	
	public CivPlayer getPlayer(String type) {
		return type.equals("human") ? human : computer;
	}
	
	public int getPlayerCurUnits(CivPlayer player) {
		return player.getUnitCount();
	}
	
	public CivCountry getPlayerCountry(CivPlayer player) {
		return player.getCountry();
	}
	
	public void setCountry(CivPlayer player, String name) {
		CivCountry country = new CivCountry(name);
		player.setCountry(country);
	}
	
	
}
