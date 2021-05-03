import java.util.Observable;

import characters.CivCharacter;

@SuppressWarnings("deprecation")
public class CivModel extends Observable{
	
	private CivCell[][] boardArr; 
	private final int DIMENSION = 10; // the dimensions of the the map
	private CivPlayer human;
	private CivPlayer computer;
	
	
	public CivModel() {
		human = new CivPlayer("Human");
		computer = new CivPlayer("Computer");
		
		boardArr = new CivCell[DIMENSION][DIMENSION];
		
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				boardArr[i][j] = new CivCell();
			}
		}
	}
	
	public CivPlayer getPlayer(String type) {
		return type.equals("Human") ? human : computer;
	}
	
	public int getHumanCurUnits() {
		return human.getUnitCount();
	}
	
	public int getComputerCurUnits() {
		return computer.getUnitCount();
	}
	
	public String getPlayerCountry(CivPlayer player) {
		return player.getCountry();
	}
	
	public void setCountry(CivPlayer player, String name) {
		CivCountry country = new CivCountry(name);
		player.setCountry(country);
	}
	
	public CivCell getCell(int row, int col) {
		return boardArr[row][col];
	}
	
	public void updateCell(int row, int col, CivCharacter character, String player) {
		boardArr[row][col].setCharacter(character);
		boardArr[row][col].setPlayer(player);
		setChanged();
		notifyObservers(null);
	}
	
	
}
