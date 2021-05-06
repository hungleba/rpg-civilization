import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
	private CivCell[][] boardArr; 
	/** the dimension of the Civilization's map */
	private transient final static int DIMENSION = 10; 
	/** current unit count on the board */
	private int curUnits; 
	/** represent the human's side of the game */
	private CivPlayer human;
	private CivPlayer computer;
	private String color;
	private Map<Integer, CivCell[][]> map = buildMap();


	/**
	 * Constructor. Creates an instance of CivModel with previously saved data
	 * @param fileName name of the file where data is saved
	 *
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
	 * Get default model (set up for the start of a new game)
	 */
	public CivModel(int num) {
		human = new CivPlayer("Human");
		computer = new CivPlayer("Computer");
		curUnits = 0;
		boardArr = map.get(num);
		color = "Green";
	}

	public static Map<Integer, CivCell[][]> buildMap() {
		Map<Integer, CivCell[][]> map = new HashMap<>();
		map.put(0, buildMap0());
		map.put(1, buildMap1());
		map.put(2, buildMap2());
		return map;
	}

	/**
	 * Get player and their data from the model based on the given name
	 * @param type the player's name ("Human" or "Computer")
	 * @return the player that is asked for
	 */
	public CivPlayer getPlayer(String type) {
		return type.equals("Human") ? human : computer;
	}

	/**
	 * Get human's current number of units
	 * @return humans's unit count
	 */
	public int getHumanCurUnits() {
		return human.getUnitCount();
	}

	/**
	 * Get computer's current number of units
	 * @return computer's unit count
	 */
	public int getComputerCurUnits() {
		return computer.getUnitCount();
	}

	/**
	 * Get the color theme
	 * @return the color theme
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Set the color theme
	 * @param color the new color theme to be set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Get a cell data from the board
	 * @param row row position of the cell to be returned 
	 * @param col column position of the cell to be returned 
	 * @return the cell at (row, col)
	 */
	public CivCell getCell(int row, int col) {
		return boardArr[row][col];
	}

	/**
	 * Update a cell on the board
	 * @param row row position of the cell to be updated
	 * @param col column position of the cell to be updated 
	 * @param character character of the newly updated cell
	 * @param player player of the newly updated cell
	 */
	public void updateCell(int row, int col, CivCharacter character, String player) {
		boardArr[row][col].setCharacter(character);
		boardArr[row][col].setPlayer(player);
		setChanged();
		notifyObservers(null);
	}

	/**
	 * build map number 0
	 * 
	 * @return map number 0
	 */
	private static CivCell[][] buildMap0() {
		CivCell[][] arr = new CivCell[DIMENSION][DIMENSION]; 
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				arr[i][j] = new CivCell();
			}
		}
		arr[4][0].setObstacle("Water");
		arr[4][1].setObstacle("Water");
		arr[4][2].setObstacle("Water");
		arr[4][7].setObstacle("Water");
		arr[4][8].setObstacle("Water");
		arr[4][9].setObstacle("Water");
		arr[5][0].setObstacle("Water");
		arr[5][1].setObstacle("Water");
		arr[5][2].setObstacle("Water");
		arr[5][7].setObstacle("Water");
		arr[5][8].setObstacle("Water");
		arr[5][9].setObstacle("Water");
		arr[2][0].setObstacle("Tree");
		arr[2][9].setObstacle("Tree");
		arr[7][0].setObstacle("Tree");
		arr[7][9].setObstacle("Tree");
		arr[3][3].setObstacle("Rock");
		arr[3][6].setObstacle("Rock");
		arr[6][3].setObstacle("Rock");
		arr[6][6].setObstacle("Rock");
		arr[1][2].setObstacle("Tree");
		arr[1][7].setObstacle("Tree");
		arr[8][2].setObstacle("Tree");
		arr[8][7].setObstacle("Tree");
		return arr;
	}

	
	/**
	 * build map number 1
	 * 
	 * @return map number 1
	 */
	private static CivCell[][] buildMap1() {
		CivCell[][] arr = new CivCell[DIMENSION][DIMENSION]; 
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				arr[i][j] = new CivCell();
			}
		}
		arr[4][2].setObstacle("Lava");
		arr[4][3].setObstacle("Lava");
		arr[4][4].setObstacle("Lava");
		arr[4][5].setObstacle("Lava");
		arr[4][6].setObstacle("Lava");
		arr[4][7].setObstacle("Lava");
		arr[5][2].setObstacle("Lava");
		arr[5][3].setObstacle("Lava");
		arr[5][4].setObstacle("Lava");
		arr[5][5].setObstacle("Lava");
		arr[5][6].setObstacle("Lava");
		arr[5][7].setObstacle("Lava");
		arr[2][1].setObstacle("Tree");
		arr[2][8].setObstacle("Tree");
		arr[7][1].setObstacle("Tree");
		arr[7][8].setObstacle("Tree");
		arr[1][0].setObstacle("Rock");
		arr[1][2].setObstacle("Rock");
		arr[1][7].setObstacle("Rock");
		arr[1][9].setObstacle("Rock");
		arr[8][0].setObstacle("Rock");
		arr[8][2].setObstacle("Rock");
		arr[8][7].setObstacle("Rock");
		arr[8][9].setObstacle("Rock");
		arr[3][4].setObstacle("Rock");
		arr[3][5].setObstacle("Rock");
		arr[6][4].setObstacle("Rock");
		arr[6][5].setObstacle("Rock");
		return arr;
	}

	/**
	 * build map number 2
	 * 
	 * @return map number 2
	 */
	private static CivCell[][] buildMap2() {
		CivCell[][] arr = new CivCell[DIMENSION][DIMENSION]; 
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				arr[i][j] = new CivCell();
			}
		}
		arr[0][0].setObstacle("Rock");
		arr[1][1].setObstacle("Water");
		arr[2][2].setObstacle("Water");
		arr[3][3].setObstacle("Rock");
		arr[6][6].setObstacle("Rock");
		arr[7][7].setObstacle("Water");
		arr[8][8].setObstacle("Water");
		arr[9][9].setObstacle("Rock");
		arr[0][9].setObstacle("Rock");
		arr[1][8].setObstacle("Lava");
		arr[2][7].setObstacle("Lava");
		arr[3][6].setObstacle("Rock");
		arr[6][3].setObstacle("Rock");
		arr[7][2].setObstacle("Lava");
		arr[8][1].setObstacle("Lava");
		arr[9][0].setObstacle("Rock");
		arr[1][4].setObstacle("Tree");
		arr[1][5].setObstacle("Tree");
		arr[8][4].setObstacle("Tree");
		arr[8][5].setObstacle("Tree");
		arr[4][1].setObstacle("Tree");
		arr[5][1].setObstacle("Tree");
		arr[4][8].setObstacle("Tree");
		arr[5][8].setObstacle("Tree");
		return arr;
	}
	
}
