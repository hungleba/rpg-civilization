import java.io.Serializable;
/**
 * This class serves as a cell in the game's board and contains information about its
 * current character + player
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

import characters.CivCharacter;
/**
 * This class serves as a cell in the game board and contains data about its current
 * player + character
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivCell implements Serializable{

	private static final long serialVersionUID = 1L;
	private String player;
	private CivCharacter character;
	
	/**
	 * Constructor. Creates an instance of CivCell.
	 *
	 */
	public CivCell() {
		this.player = null;
		this.character = null;
	}
	
	/**
	 * Set current character of the cell (type of character that is in the cell)
	 * @param character char to be set
	 *
	 */
	public void setCharacter(CivCharacter character) {
		this.character = character;
	}
	
	/**
	 * Get current character (type) of the cell
	 * @return character (type) that is currently in the cell
	 *
	 */
	public CivCharacter getCharacter() {
		return character;
	}
	
	/**
	 * Set current player of the cell (human or computer)
	 * @param player player to be set
	 *
	 */
	public void setPlayer(String player) {
		this.player = player;
	}
	
	/**
	 * Get current player of the cell (human or computer)
	 * @return player that is currently occupying the cell
	 *
	 */
	public String getPlayer() {
		return player;
	}
}
