import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import characters.CivArcher;
import characters.CivCatapult;
import characters.CivCharacter;
import characters.CivGuard;
import characters.CivKnight;
import characters.CivWarrior;
import javafx.scene.paint.Color;

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

	private boolean isValidSpawnPosition(int row, int col, String playerType) {
		if (playerType.equals("Computer")) {
			if (row >= 2) {
				return false;
			} else {
				return model.getCell(row, col).getPlayer() == null;
			}
		} else {
			if (row < DIMENSION - 2) {
				return false;
			} else {
				return model.getCell(row, col).getPlayer() == null;
			}
		}
	}

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

	public boolean isGameBegin() {
		return isBeginOfGame;
	}
	
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

	public void computerMove() {
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
				int newCoord = optimizeAttack(attack, character);
				int nextRow = newCoord / DIMENSION;
				int nextCol = newCoord % DIMENSION;
				handleAttack(row, col, nextRow, nextCol, computer, character);			
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
			return;
		}
		int countSpawn = 0;
		while (countSpawn < numSpawn && computer.getGold() >= CivWarrior.FIXED_COST) {
			int r = rand.nextInt(2);
			int c = rand.nextInt(DIMENSION);
			while (model.getCell(r, c).getPlayer() != null) {
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
	}

	private int optimizeAttack(List<Integer> attack, CivCharacter character) {
		TreeMap<Integer, Integer> healthMap = new TreeMap<>();
		for (int coord: attack) {
			int row = coord / DIMENSION;
			int col = coord % DIMENSION;
			CivCharacter opponent = model.getCell(row, col).getCharacter();
			healthMap.put(opponent.getHealth(), coord);
		}
		return healthMap.get(healthMap.firstKey());
	}
	
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
		int movement = character.getMovement();
		for (int i = Math.max(0, row - movement); i <= Math.min(DIMENSION - 1, row + movement); i++) {
			for (int j = Math.max(0, col - movement); j <= Math.min(DIMENSION - 1, col + movement); j++) {
				if (model.getCell(i, j).getPlayer() == null) {
					map.get("Move").add(i * DIMENSION + j);
				}
			}
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

	public CivCharacter displayStats(int row, int col) {
		CivCell cell = model.getCell(row, col);
		if (cell.getCharacter() == null) {
			return null;
		}
		return cell.getCharacter();
	}

	public void handleClick(int row, int col, String character) {
		CivPlayer human = model.getPlayer("Human");
		CivCell cell = model.getCell(row, col);

		if (cell.getCharacter() == null) {
			if (isSpawned) {
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
				handleAttack(prevRow, prevCol, row, col, human, civChar);
			}
		}
	}

	private void handleAttack(int prevRow, int prevCol, int row, int col, CivPlayer player, CivCharacter civChar) {
		CivCharacter curChar = model.getCell(row, col).getCharacter();
		CivPlayer otherPlayer = model.getPlayer(model.getCell(row, col).getPlayer());
		if (Math.max(Math.abs(row - prevRow), Math.abs(col - prevCol)) <= civChar.getRange()
				&& !civChar.getIsMoved()) {
			System.out.println(player.getName()+" attack from "+prevRow+prevCol+" to "+row+col+" using "+civChar.getName()+"\n");
			int health = curChar.getHealth() - civChar.getAttack();
			if (health <= 0) {
				model.updateCell(row, col, null, null);
				otherPlayer.removeUnit(curChar);
				player.addGold(curChar.getLevel());
				civChar.levelUp();
			} else {
				curChar.setHealth(health);
			}
			civChar.setIsMoved(true);
		}
		isMove = false;
		prevRow = -1;
		prevCol = -1;
		civChar = null;

	}

	private void handleMove(int prevRow, int prevCol, int row, int col, CivPlayer player, CivCharacter civChar) {
		if (Math.max(Math.abs(row - prevRow), Math.abs(col - prevCol)) <= civChar.getMovement()
				&& !civChar.getIsMoved()) {
			System.out.println(player.getName()+" move from "+prevRow+prevCol+" to "+row+col+" using "+civChar.getName()+"\n");
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

	private void handleAddUnit(String character, CivPlayer player, int row, int col) {
		if (!isValidSpawnPosition(row, col, player.getName()) || !isAbleToSpawn(character, player.getName())) {
			isSpawned = false;
			return;
		}
		System.out.println(player.getName()+" add "+row+col+" using "+character+"\n");
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
	
	public boolean getIsMoved() {
		return isMove;
	}
	
	public void setColor(String color) {
		model.setColor(color);
	}
	
	public String getColor() {
		return model.getColor();
	}
	
	public CivCell getCell(int r, int c) {
		return model.getCell(r, c);
	}
	
	public CivPlayer getPlayer(String player) {
		return model.getPlayer(player);
	}

}
