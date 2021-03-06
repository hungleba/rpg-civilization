import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import characters.CivArcher;
import characters.CivCatapult;
import characters.CivCharacter;
import characters.CivGuard;
import characters.CivKnight;
import characters.CivWarrior;

/**
 * This class serves as the controller for the MVC (Mode/Control/View) architecture and 
 * contains method which perform different tasks that facilitates the logic and functionality
 * of the game. This class can check if their player'computer moves are valid, it handles
 * the actions like moving, attacking, and spawning; it can also update the stats of the
 * the player, the computer and both of their units.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivController {
	/** the max number of unit a player(human ad computer) can have on the board at all times */
	private static final int MAX_UNITS = 10;
	/** the dimension of the Civilization game's board */
	private static final int DIMENSION = 10;
	/** the CivModel object */
	private CivModel model;
	/** the row that the player is currently on - used for handling attacking and moving */
	private int prevRow;
	/** the row that the player is currently on - used for handling attacking and moving */
	private int prevCol;
	/** the CivCharacter at the cell that the player is currently at - used for handling attacking and moving */
	private CivCharacter civChar;
	/** true if a valid cell has been clicked before, false if otherwise */
	private boolean isMove;
	/** true if spawning can be perform, and false if otherwise */
	private boolean isSpawned;
	/** true if both sides has not finished their first turn, and false if otherwise */
	private boolean isBeginOfGame;

	/**
	 * Constructor
	 * 
	 * @param model the CivModel object
	 */
	public CivController(CivModel model) {
		this.model = model;
		prevRow = -1;
		prevCol = -1;
		civChar = null;
		isMove = false;
		isSpawned = false;
		isBeginOfGame = true;
	}

	/**
	 * Set the isSpwaned field to true and inform the program that spawning is possible
	 */
	public void setSpawned() {
		isSpawned = true;
	}

	/**
	 * Ends the turn and reset all the field for the next player's turn
	 * 
	 * @param player type of player "Human" or "Computer"
	 */
	public void endTurn(String player) {
		isBeginOfGame = false;
		model.getPlayer(player).addGold(2); // at the end of each turn the player receives two additional gold
		Map<String, List<CivCharacter>> unitMap = model.getPlayer(player).getUnitMap();
		for (String name: unitMap.keySet()) {
			List<CivCharacter> list = unitMap.get(name);
			for (CivCharacter character: list) {
				character.setIsMoved(false);
			}
		}
		CivCell cell = model.getCell(0, 0);
		model.updateCell(0, 0, cell.getCharacter(), cell.getPlayer());
	}

	/**
	 * Check if a player can spawn the given character
	 * 
	 * @param character the character type to be spawned
	 * 
	 * @param playerType type of player "Human" or "Computer"
	 * 
	 * @return true if the player can spawn the character, and false if
	 * the character is invalid, the player exceeds max unit count, or is
	 * out of gold to buy the given character
	 */
	private boolean isAbleToSpawn(String character, String playerType) {
		CivCharacter curChar = null;
		if (character.equals("Archer")) {
			curChar = new CivArcher();
		} else if (character.equals("Catapult")) {
			curChar = new CivCatapult();
		} else if (character.equals("Guard")) {
			curChar = new CivGuard();
		} else if (character.equals("Knight")) {
			curChar = new CivKnight();
		} else if (character.equals("Warrior")) {
			curChar = new CivWarrior();
		} else {
			return false;
		}
		CivPlayer player = model.getPlayer(playerType);
		if (player.getUnitCount() >= MAX_UNITS || player.getGold() < curChar.getCost()) {
			return false;
		}
		return true;
	}

	/**
	 * Check a position can be spawned by a given player. Computer spawns
	 * the first two rows, and Human spawns the last two rows
	 * 
	 * @param row the row position to be spawned
	 * 
	 * @param col the column position to be spawned
	 * 
	 * @param playerType type of player "Human" or "Computer"
	 * 
	 * @return true if the position can be spawned by the given player, and false 
	 * otherwise
	 */
	private boolean isValidSpawnPosition(int row, int col, String playerType) {
		if (playerType.equals("Computer")) {
			if (row >= 2) {
				return false;
			} else {
				return model.getCell(row, col).getPlayer() == null && model.getCell(row, col).getObstacle() == null;
			}
		} else {
			if (row < DIMENSION - 2) {
				return false;
			} else {
				return model.getCell(row, col).getPlayer() == null && model.getCell(row, col).getObstacle() == null;
			}
		}
	}

	/**
	 * Check if the game is over (one side has no unit left)
	 * 
	 * @return true if the game is over and false otherwise
	 */
	public boolean isGameOver() {
		int humanUnits = model.getHumanCurUnits();
		int compUnits = model.getComputerCurUnits();
		if (!isBeginOfGame) {
			if (humanUnits == 0 || compUnits == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the game has started already (one side has completed their turn)
	 * 
	 * @return true if the game is started and false otherwise
	 */
	public boolean isGameBegin() {
		return isBeginOfGame;
	}
	
	/**
	 * Check for the one with more unit count
	 * 
	 * @return "Human" if human side has more unit count, and "Computer" if
	 * computer side has more unit count. If tied then return null.
	 */
	public String determineWinner() {
		int humanUnits = model.getHumanCurUnits();
		int compUnits = model.getComputerCurUnits();
		if (humanUnits > compUnits) {
			return "Human";
		} else if (humanUnits < compUnits) {
			return "Computer";
		} else {
			return null;
		}
	}

	/**
	 * Represents a move from the computer
	 * 
	 * @return a set of positions (represented as index of the 2D board array) 
	 * that has been attacked in this turn
	 */
	public Set<Integer> computerMove() {
		Set<Integer> attacked = new HashSet<>();
		CivPlayer computer = model.getPlayer("Computer");
		Map<CivCharacter, Integer> positionMap = computer.getPositionMap();
		Random rand = new Random();
		for (CivCharacter character: positionMap.keySet()) {
			int coord = positionMap.get(character);
			int row = coord / DIMENSION;
			int col = coord % DIMENSION;
			Map<String, List<Integer>> moves = allPossibleMoves(row, col, "Computer");
			List<Integer> attack = moves.get("Attack");
			List<Integer> movement = moves.get("Move");
			if (attack.size() != 0) {
				int newCoord = optimizeAttack(attack);
				int nextRow = newCoord / DIMENSION;
				int nextCol = newCoord % DIMENSION;
				handleAttack(row, col, nextRow, nextCol, computer, character);
				attacked.add(newCoord);
			} else if (movement.size() != 0) {
				int newCoord = movement.get(rand.nextInt(movement.size()));
				int nextRow = newCoord / DIMENSION;
				int nextCol = newCoord % DIMENSION;
				handleMove(row, col, nextRow, nextCol, computer, character);			
			}
		}
		int isSpawn = rand.nextInt(3);
		int numSpawn = rand.nextInt(MAX_UNITS-computer.getUnitCount()+1);
		if (computer.getUnitCount() == 0) {
			isSpawn = 0;
			numSpawn = MAX_UNITS;
		}
		if (isSpawn > 0) {
			endTurn("Computer");
			return attacked;
		}
		int countSpawn = 0;
		while (countSpawn < numSpawn && computer.getGold() >= CivWarrior.FIXED_COST) {
			int r = rand.nextInt(2);
			int c = rand.nextInt(DIMENSION);
			while (model.getCell(r, c).getPlayer() != null || model.getCell(r, c).getObstacle() != null) {
				if (c == DIMENSION-1) {
					if (r == 0) {
						r = 1;
						c = 0;
					} else {
						r = 0;
						c = 0;
					}
				} else {
					c += 1;
				}
			}
			if (isAbleToSpawn("Catapult", "Computer")) {
				handleAddUnit("Catapult", computer, r, c);
			} else if (isAbleToSpawn("Knight", "Computer")) {
				handleAddUnit("Knight", computer, r, c);
			} else if (isAbleToSpawn("Guard", "Computer")) {
				handleAddUnit("Guard", computer, r, c);
			} else if (isAbleToSpawn("Archer", "Computer")) {
				handleAddUnit("Archer", computer, r, c);
			} else {
				handleAddUnit("Warrior", computer, r, c);
			}
			countSpawn++;
		}
		Map<String, List<CivCharacter>> unitMap = model.getPlayer("Computer").getUnitMap();
		for (String name: unitMap.keySet()) {
			List<CivCharacter> list = unitMap.get(name);
			for (CivCharacter character: list) {
				character.setIsMoved(false);
			}
		}
		endTurn("Computer");
		return attacked;
	}

	/**
	 * Choose the optimal unit to attack. In this implementation, it is
	 * the unit in range that has the lowest health score
	 * 
	 * @param attack available attack positions
	 * 
	 * @return position of the unit in range that has the lowest health
	 */
	private int optimizeAttack(List<Integer> attack) {
		TreeMap<Integer, Integer> healthMap = new TreeMap<>();
		for (int coord: attack) {
			int row = coord / DIMENSION;
			int col = coord % DIMENSION;
			CivCharacter opponent = model.getCell(row, col).getCharacter();
			healthMap.put(opponent.getHealth(), coord);
		}
		return healthMap.get(healthMap.firstKey());
	}
	
	/**
	 * All possible moves (attack or move unit) from the position (row, col)
	 * If the position is empty or the given player does not occupy that position, 
	 * then return null
	 * 
	 * @param row the position to make a move from
	 * 
	 * @param col the column position to make a move from
	 * 
	 * @param player current player to make a move
	 * 
	 * @return a map containing appropriate positions as values for two keys: "Move" and "Attack"
	 */
	public Map<String, List<Integer>> allPossibleMoves(int row, int col, String player) {
		CivCell cell = model.getCell(row, col);
		if (cell.getPlayer() == null) {
			return null;
		}
		if (!cell.getPlayer().equals(player)) {
			return null;
		}
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("Attack", new ArrayList<Integer>());
		map.put("Move", new ArrayList<Integer>());
		CivCharacter character = cell.getCharacter();
		int range = character.getRange();
		Set<Integer> moves = validMoves(row, col, character.getMovement());
		for (int move: moves) {
			map.get("Move").add(move);
		}
		for (int i = Math.max(0, row - range); i <= Math.min(DIMENSION - 1, row + range); i++) {
			for (int j = Math.max(0, col - range); j <= Math.min(DIMENSION - 1, col + range); j++) {
				String otherPlayer = model.getCell(i, j).getPlayer();
				if (otherPlayer != null && !otherPlayer.equals(player)) {
					map.get("Attack").add(i * DIMENSION + j);
				}
			}
		}
		return map;
	}
	
	/**
	 * Get the set of all valid moves given the position and the movement range
	 * @param r the row position
	 * @param c the column position
	 * @param range the movement range
	 * @return the set of all valid moves
	 */
	private Set<Integer> validMoves(int r, int c, int range) {
		Set<Integer> moves = new HashSet<>();
		validMovesHelper(r, c, 1, range, moves);
		return moves;
	}
	
	/**
	 * A helper method for all valid moves method
	 * @param r the row position
	 * @param c the column position
	 * @param count the count variable to keep track
	 * @param range the movement range
	 * @param moves the set of valid moves
	 */
	private void validMovesHelper(int r, int c, int count, int range, Set<Integer> moves) {
		if (count > range) {
			return;
		}
		if (r-1 >= 0 && c-1 >= 0 && model.getCell(r-1, c-1).getObstacle() == null 
				&& model.getCell(r-1, c-1).getCharacter() == null) {
			moves.add((r-1)*DIMENSION+c-1);
			validMovesHelper(r-1, c-1, count+1, range, moves);
		}
		if (r-1 >= 0 && model.getCell(r-1, c).getObstacle() == null 
				&& model.getCell(r-1, c).getCharacter() == null) {
			moves.add((r-1)*DIMENSION+c);
			validMovesHelper(r-1, c, count+1, range, moves);
		}
		if (r-1 >= 0 && c+1 < DIMENSION && model.getCell(r-1, c+1).getObstacle() == null 
				&& model.getCell(r-1, c+1).getCharacter() == null) {
			moves.add((r-1)*DIMENSION+c+1);
			validMovesHelper(r-1, c+1, count+1, range, moves);
		}
		if (c+1 < DIMENSION && model.getCell(r, c+1).getObstacle() == null 
				&& model.getCell(r, c+1).getCharacter() == null) {
			moves.add(r*DIMENSION+c+1);
			validMovesHelper(r, c+1, count+1, range, moves);
		}
		if (r+1 < DIMENSION && c+1 < DIMENSION && model.getCell(r+1, c+1).getObstacle() == null 
				&& model.getCell(r+1, c+1).getCharacter() == null) {
			moves.add((r+1)*DIMENSION+c+1);
			validMovesHelper(r+1, c+1, count+1, range, moves);
		}
		if (r+1 < DIMENSION && model.getCell(r+1, c).getObstacle() == null 
				&& model.getCell(r+1, c).getCharacter() == null) {
			moves.add((r+1)*DIMENSION+c);
			validMovesHelper(r+1, c, count+1, range, moves);
		}
		if (r+1 < DIMENSION && c-1 >= 0 && model.getCell(r+1, c-1).getObstacle() == null 
				&& model.getCell(r+1, c-1).getCharacter() == null) {
			moves.add((r+1)*DIMENSION+c-1);
			validMovesHelper(r+1, c-1, count+1, range, moves);
		}
		if (c-1 >= 0 && model.getCell(r, c-1).getObstacle() == null 
				&& model.getCell(r, c-1).getCharacter() == null) {
			moves.add(r*DIMENSION+c-1);
			validMovesHelper(r, c-1, count+1, range, moves);
		}
	}
	
	/**
	 * Display stats of a cell at (row, col)
	 * 
	 * @param row the row position to get stats from
	 * 
	 * @param col the column position to get stats from

	 * @return the CivCharacter that is in the cell
	 */
	public CivCharacter displayStats(int row, int col) {
		CivCell cell = model.getCell(row, col);
		if (cell.getCharacter() == null) {
			return null;
		}
		return cell.getCharacter();
	}

	/**
	 * Handle interactions to a cell when it is clicked. Can be spawning a new character, 
	 * moving the current character out of the cell, moving a pre-selected character into the
	 * cell, making the current character in the cell attack another unit, or attacking 
	 * the current unit in the cell
	 * 
	 * @param row the row of the clicked cell
	 * 
	 * @param col the column of the clicked cell
	 * 
	 * @param character the character that is given when clicked, in case
	 * of spawning
	 * 
	 * @return true if the cell is attacked and false otherwise
	 */
	public boolean handleClick(int row, int col, String character) {
		CivPlayer human = model.getPlayer("Human");
		CivCell cell = model.getCell(row, col);
		if (cell.getCharacter() == null) {
			if (cell.getObstacle() != null) {
			} else if (isSpawned) {
				handleAddUnit(character, human, row, col);
			} else if (isMove) {
				handleMove(prevRow, prevCol, row, col, human, civChar);
			}
		} else {
			if (!isMove && cell.getPlayer().equals("Human")) {
				prevRow = row;
				prevCol = col;
				civChar = cell.getCharacter();
				isMove = true;
			} else if (isMove && cell.getPlayer().equals("Computer")) {
				int prevHealth = -1;
				int currHealth = -1;
				CivCharacter currChar = model.getCell(row, col).getCharacter();
				if (currChar != null)
					prevHealth = currChar.getHealth();
				handleAttack(prevRow, prevCol, row, col, human, civChar);
				if (currChar != null)
					currHealth = currChar.getHealth();
				if (prevHealth != currHealth && prevHealth != -1 && currHealth != -1)
					return true;
			}
		}
		return false;
	}

	/**
	 * Handle interactions to a cell when it is attacked
	 * 
	 * @param prevRow row of the cell clicked prior to the current cell
	 * 
	 * @param prevCol column of the cell clicked prior to the current cell
	 * 
	 * @param row the row of the clicked cell
	 * 
	 * @param col the column of the clicked cell
	 * 
	 * @param player the player of the previously clicked cell (the attacker)
	 * 
	 * @param civChar the character of the previously clicked cell (the attacker)
	 * 
	 */
	private void handleAttack(int prevRow, int prevCol, int row, int col, CivPlayer player, CivCharacter civChar) {
		CivCharacter curChar = model.getCell(row, col).getCharacter();
		CivPlayer otherPlayer = model.getPlayer(model.getCell(row, col).getPlayer());
		if (Math.max(Math.abs(row - prevRow), Math.abs(col - prevCol)) <= civChar.getRange()
				&& !civChar.getIsMoved()) {
			int health = curChar.getHealth() - civChar.getAttack();
			if (health <= 0) {
				model.updateCell(row, col, null, null);
				otherPlayer.removeUnit(curChar);
				player.addGold(curChar.getLevel());
				civChar.levelUp();
			} else {
				curChar.setHealth(health);
				model.updateCell(row, col, curChar, otherPlayer.getName());
			}
			civChar.setIsMoved(true);
		}
		isMove = false;
		prevRow = -1;
		prevCol = -1;
		civChar = null;

	}

	/**
	 * Handle interactions to a cell when a unit is being moved to it
	 * 
	 * @param prevRow row of the cell clicked prior to the current cell
	 * 
	 * @param prevCol column of the cell clicked prior to the current cell
	 * 
	 * @param row the row of the clicked cell
	 * 
	 * @param col the column of the clicked cell
	 * 
	 * @param player the player of the previously clicked cell 
	 * 
	 * @param civChar the character of the previously clicked cell 
	 * 
	 */
	private void handleMove(int prevRow, int prevCol, int row, int col, CivPlayer player, CivCharacter civChar) {
		Set<Integer> moves = validMoves(prevRow, prevCol, civChar.getMovement());
		if (moves.contains(row*DIMENSION+col) && !civChar.getIsMoved()) {
			model.updateCell(row, col, civChar, player.getName());
			model.updateCell(prevRow, prevCol, null, null);
			civChar.setIsMoved(true);
		}
		player.updateUnit(civChar, row, col);
		isMove = false;
		prevRow = -1;
		prevCol = -1;
		civChar = null;

	}

	/**
	 * Add unit to an empty cell and the player's team when they spawned
	 * 
	 * @param player the current player (human or computer) 
	 * 
	 * @param character the character that has been spawned
	 * 
	 * @param row the row of the cell to spawn on
	 * 
	 * @param col the column of cell to spawn on
	 * 
	 */
	private void handleAddUnit(String character, CivPlayer player, int row, int col) {
		if (!isValidSpawnPosition(row, col, player.getName()) || !isAbleToSpawn(character, player.getName())) {
			isSpawned = false;
			return;
		}
		CivCharacter curChar = null;
		if (character.equals("Archer")) {
			curChar = new CivArcher();
		} else if (character.equals("Catapult")) {
			curChar = new CivCatapult();
		} else if (character.equals("Guard")) {
			curChar = new CivGuard();
		} else if (character.equals("Knight")) {
			curChar = new CivKnight();
		} else if (character.equals("Warrior")) {
			curChar = new CivWarrior();
		}
		player.addUnit(curChar, row, col);
		isSpawned = false;
		curChar.setIsMoved(true);
		model.updateCell(row, col, curChar, player.getName());
	}
	
	/**
	 * Return status of the current turn
	 * 
	 * @return true if a valid cell has been clicked before, false if otherwise 
	 *
	 */
	public boolean getIsMoved() {
		return isMove;
	}
	
	/**
	 * Get the cell at (r,c)
	 * 
	 * @param r the row position of the cell
	 * 
	 * @param c the column position of the cell
	 * 
	 * @return CivCell at (r,c)
	 *
	 */
	public CivCell getCell(int r, int c) {
		return model.getCell(r, c);
	}
	
	/**
	 * Get the player based on name 
	 * 
	 * @param player name of the player ("Human" or "Computer")
	 * 
	 * @return the CivPlayer of that name
	 *
	 */
	public CivPlayer getPlayer(String player) {
		return model.getPlayer(player);
	}
	
	/**
	 * Set the color theme
	 * @param color the new color theme to be set
	 */
	public void setColor(String color) {
		model.setColor(color);
	}
	
	public String getColor() {
		return model.getColor();
	}

}
