
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import characters.CivArcher;
import characters.CivCatapult;
import characters.CivCharacter;
import characters.CivGuard;
import characters.CivKnight;
import characters.CivWarrior;

public class CivController {
	private static final int MAX_UNITS = 10;
	private static final int DIMENSION = 10;
	private CivModel model;
	private int prevRow;
	private int prevCol;
	private CivCharacter civChar;
	private boolean isMove;
	private boolean isSpawned;
	private List<CivCharacter> visited;
	private int countSpawned;
	private boolean isBeginOfGame;

	public CivController(CivModel model) {
		this.model = model;
		prevRow = -1;
		prevCol = -1;
		civChar = null;
		isMove = false;
		isSpawned = false;
		visited = new ArrayList<CivCharacter>();
		countSpawned = 0;
		isBeginOfGame = true;
	}

	public void setSpawned() {
		isSpawned = true;

	}

	public void endTurn() {
		isBeginOfGame = false;
		visited = new ArrayList<CivCharacter>();
		countSpawned = 0;
		model.getPlayer("Human").addGold(2);
		Map<String, List<CivCharacter>> unitMap = model.getPlayer("Human").getUnitMap();
		for (String name: unitMap.keySet()) {
			List<CivCharacter> list = unitMap.get(name);
			for (CivCharacter character: list) {
				character.setIsMoved(false);
			}
		}
	}

	public boolean isAbleToSpawn(String character, String playerType) {
		if (countSpawned > 0 && playerType.equals("Human")) {
			return false;
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
		for (CivCharacter character : positionMap.keySet()) {
			int coord = positionMap.get(character);
			int row = coord / DIMENSION;
			int col = coord % DIMENSION;
			Map<String, List<Integer>> moves = allPossibleMoves(row, col, "Computer");
			List<Integer> attack = moves.get("Attack");
			List<Integer> movement = moves.get("Move");
			if (attack.size() != 0) {
				int newCoord = attack.get(rand.nextInt(attack.size()));
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
		if (computer.getUnitCount() == 0) {
			isSpawn = 1;
		}
		if (isSpawn == 0 || computer.getGold() < CivWarrior.FIXED_COST) {
			model.getPlayer("Computer").addGold(2);
			return;
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if (isValidSpawnPosition(i, j, "Computer")) {
					if (computer.getGold() >= CivCatapult.FIXED_COST) {
						handleAddUnit("Catapult", computer, i, j);
					} else if (computer.getGold() >= CivKnight.FIXED_COST) {
						handleAddUnit("Knight", computer, i, j);
					} else if (computer.getGold() >= CivGuard.FIXED_COST) {
						handleAddUnit("Guard", computer, i, j);
					} else if (computer.getGold() >= CivArcher.FIXED_COST) {
						handleAddUnit("Archer", computer, i, j);
					} else {
						handleAddUnit("Warrior", computer, i, j);
					}
					model.getPlayer("Computer").addGold(2);
					return;
				}
			}
		}
		model.getPlayer("Computer").addGold(2);
		Map<String, List<CivCharacter>> unitMap = model.getPlayer("Computer").getUnitMap();
		for (String name: unitMap.keySet()) {
			List<CivCharacter> list = unitMap.get(name);
			for (CivCharacter character: list) {
				character.setIsMoved(false);
			}
		}
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
				&& !visited.contains(civChar)) {
			int health = curChar.getHealth() - civChar.getAttack();
			if (health <= 0) {
				model.updateCell(row, col, null, null);
				otherPlayer.removeUnit(curChar);
				player.addGold(curChar.getLevel());
				civChar.levelUp();
				System.out.println(civChar.getLevel());
			} else {
				curChar.setHealth(health);
			}
			visited.add(civChar);
			civChar.setIsMoved(true);
		}
		isMove = false;
		prevRow = -1;
		prevCol = -1;
		civChar = null;

	}

	private void handleMove(int prevRow, int prevCol, int row, int col, CivPlayer player, CivCharacter civChar) {
		if (Math.max(Math.abs(row - prevRow), Math.abs(col - prevCol)) <= civChar.getMovement()
				&& !visited.contains(civChar)) {
			model.updateCell(row, col, civChar, player.getName());
			model.updateCell(prevRow, prevCol, null, null);
			player.updateUnit(civChar, row, col);
			visited.add(civChar);
			civChar.setIsMoved(true);
		}
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
		countSpawned++;
		visited.add(curChar);
		model.updateCell(row, col, curChar, player.getName());
	}

}
