
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import characters.CivArcher;
import characters.CivCaptapult;
import characters.CivCharacter;
import characters.CivGuard;
import characters.CivKnight;
import characters.CivWarrior;

public class CivController {
	private static final int MAX_UNITS = 10;
	private CivModel model;
	private int prevRow;
	private int prevCol;
	private CivCharacter civChar;
	private boolean isMove;
	private boolean isSpawned;

	public CivController(CivModel model) {
		this.model = model;
		prevRow = -1;
		prevCol = -1;
		civChar = null;
		isMove = false;
		isSpawned = false;
	}
	
	public void setSpawned() {
		isSpawned = true;
	}
	
	public boolean isAbleToSpawn(String character, String playerType) {
		CivCharacter curChar = null;
		if (character.equals("Archer")) {
			curChar = new CivArcher();
		} else if (character.equals("Catapult")) {
			curChar = new CivCaptapult();
		} else if (character.equals("Guard")) {
			curChar = new CivGuard();
		} else if (character.equals("Kinight")) {
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
	
	// TODO
	public boolean isValidSpawnPosition(int row, int col, String playerType) {
		CivPlayer player = model.getPlayer(playerType);
		return true;
	}

	public boolean isGameOver() {
		int humanUnits = model.getHumanCurUnits();
		int compUnits = model.getComputerCurUnits();
		if (humanUnits == 0 || compUnits == 0) {
			return true;
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
		
		
	}
	
	public Map<String, List<Integer>> allPossibleMoves(int row, int col, String playerType) {
		CivPlayer player = model.getPlayer(playerType);
		CivCell cell = model.getCell(row, col);
		if (cell.getPlayer() == null) {
			return null;
		}
		if (!cell.getPlayer().equals(playerType)) {
			return null;
		}
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("Attack", new ArrayList<Integer>());
		map.put("Move", new ArrayList<Integer>());
		
		return null;
	}
	
	public CivCharacter displayStats(int row, int col) {
		CivCell cell = model.getCell(row, col);
		if (cell.getCharacter() == null) {
			return null;
		}
		return cell.getCharacter();
	}

	public void handleClick(int row, int col, String character) {
		CivPlayer human = model.getPlayer("human");
		CivCell cell = model.getCell(row, col);
		if (cell.getCharacter() == null) {
			if (isSpawned) {
				handleAddUnits(character, human, row, col);
			} else if (isMove) {
				handleMove(row, col, human, civChar);
			}
		} else {
			if (!isMove && cell.getPlayer().equals("Human")) {
				prevRow = row;
				prevCol = col;
				civChar = cell.getCharacter();
			} else if (isMove && cell.getPlayer().equals("Computer")) {
				handleAttack(row, col, human, civChar);
			}	
		}
	}

	private void handleAttack(int row, int col, CivPlayer player, CivCharacter civchar) {
		CivCharacter curChar = model.getCell(row, col).getCharacter();
		if (Math.max(Math.abs(row-prevRow), Math.abs(col-prevCol)) <= civChar.getRange()) {
			int health = curChar.getHealth()-civChar.getAttack();
			if (health <= 0) {
				model.updateCell(row, col, null, null);
				player.removeUnit(curChar);
				player.addGold(curChar.getLevel());
				civChar.levelUp();
			} else {
				curChar.setHealth(health);
			}
		}
		isMove = false;
	}

	private void handleMove(int row, int col, CivPlayer player, CivCharacter civchar) {
		if (Math.max(Math.abs(row-prevRow), Math.abs(col-prevCol)) <= civChar.getMovement()) {
			model.updateCell(row, col, civChar, player.getName());
			model.updateCell(prevRow, prevCol, null, null);
		}
		isMove = false;
	}

	private void handleAddUnits(String character, CivPlayer player, int row, int col) {
		CivCharacter curChar = null;
		if (character.equals("Archer")) {
			curChar = new CivArcher();
		} else if (character.equals("Catapult")) {
			curChar = new CivCaptapult();
		} else if (character.equals("Guard")) {
			curChar = new CivGuard();
		} else if (character.equals("Kinight")) {
			curChar = new CivKnight();
		} else if (character.equals("Warrior")) {
			curChar = new CivWarrior();
		}
		player.addUnit(curChar, row, col);
		model.updateCell(row, col, curChar, player.getName());
		isSpawned = false;
	}
	
	
}
