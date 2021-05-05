import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Observable;

import characters.CivCharacter;
/**
 * This class serves as the Model in MVC architecture and stores necessary information to
 * run the game.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

@SuppressWarnings("deprecation")
public class CivModel extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 2D-array that represents the game board. Each element is a cell on the board */
	private CivCell[][] boardArr; 
	/** the dimension of the Civilization's map */
	private transient final int DIMENSION = 10; 
	/** current unit count on the board */
	private int curUnits; 
	/** represent the human's side of the game */
	private CivPlayer human;
	/** represent the computer's side of the game */
	private CivPlayer computer;
	/** represent the current theme color of the game */
	private String color;
	
	/**
	 * Constructor. Creates an instance of CivModel with previously saved data
	 * 
	 * @param fileName name of the file where data is saved
	 * 
	 * @throws FileNotFoundException cannot find the file
	 * 
	 * @throws IOException problems with input file
	 * 
	 * @throws ClassNotFoundException cannot find required class (CivModel)
	 */
	public CivModel(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
		CivModel model = (CivModel) ois.readObject();
		if (model != null) {
			this.boardArr = model.boardArr;
			this.curUnits = model.curUnits;
			this.human = model.human;
			this.computer = model.computer;
			this.color = model.color;
			this.curUnits = model.curUnits;
		} else {
			getDefaultModel();
		}
		ois.close();
	}

	/**
	 * Constructor. Creates an instance of CivModel without saved data
	 */
	public CivModel() {
		getDefaultModel();
	}
	
	/**
	 * Get default model (set up for the start of a new game)
	 */
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
		color = "Green";
	}
	
	/**
	 * Get player and their data from the model based on the given name
	 * 
	 * @param type the player's name ("Human" or "Computer")
	 * 
	 * @return the player that is asked for
	 */
	public CivPlayer getPlayer(String type) {
		return type.equals("Human") ? human : computer;
	}
	
	/**
	 * Get human's current number of units
	 * 
	 * @return humans's unit count
	 */
	public int getHumanCurUnits() {
		return human.getUnitCount();
	}
	
	/**
	 * Get computer's current number of units
	 * 
	 * @return computer's unit count
	 */
	public int getComputerCurUnits() {
		return computer.getUnitCount();
	}
	
	/**
	 * Get the color theme
	 * 
	 * @return the color theme
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Set the color theme
	 * 
	 * @param color the new color theme to be set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Get a cell data from the board
	 * 
	 * @param row row position of the cell to be returned 
	 * 
	 * @param col column position of the cell to be returned 
	 * 
	 * @return the cell at (row, col)
	 */
	public CivCell getCell(int row, int col) {
		return boardArr[row][col];
	}
	
	/**
	 * Update a cell on the board
	 * 
	 * @param row row position of the cell to be updated
	 * 
	 * @param col column position of the cell to be updated 
	 * 
	 * @param character character of the newly updated cell
	 * 
	 * @param player player of the newly updated cell
	 */
	public void updateCell(int row, int col, CivCharacter character, String player) {
		boardArr[row][col].setCharacter(character);
		boardArr[row][col].setPlayer(player);
		setChanged();
		notifyObservers(null);
	}
	
	
}
