import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Observable;

import characters.CivCharacter;
import javafx.application.Platform;

@SuppressWarnings("deprecation")
public class CivModel extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private CivCell[][] boardArr; 
	private transient final int DIMENSION = 10; // the dimensions of the the map
	private int curUnits; // the current number of units on the map
	private CivPlayer human;
	private CivPlayer computer;
	private String country;
	
	public CivModel(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
		CivModel model = (CivModel) ois.readObject();
		if (model != null) {
			this.boardArr = model.boardArr;
			this.curUnits = model.curUnits;
			this.human = model.human;
			this.computer = model.computer;
			this.country = model.country;
			this.curUnits = model.curUnits;
		} else {
			getDefaultModel();
		}
		ois.close();
	}

	
	public CivModel() {
		getDefaultModel();
	}
	
	private void getDefaultModel() {
		human = new CivPlayer("Human");
		computer = new CivPlayer("Computer");
		curUnits = 0;
		boardArr = new CivCell[DIMENSION][DIMENSION];
		
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				boardArr[i][j] = new CivCell();
			}
		}
		
		country = null;
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
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
		System.out.println(this.getCountry());
		
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
