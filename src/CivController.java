
public class CivController {
	CivModel model;
	
	public CivController(CivModel model) {
		this.model = model;
	}
	
	public boolean isGameOver() {
		int humanUnits = model.getPlayerCurUnits(model.getPlayer("human"));
		int compUnits = model.getPlayerCurUnits(model.getPlayer("computer"));
		if (humanUnits == 0 || compUnits == 0) {
			return true;
		}
		return false;
	}
	
	public String determineWinner() {
		int humanUnits = model.getPlayerCurUnits(model.getPlayer("human"));
		int compUnits = model.getPlayerCurUnits(model.getPlayer("computer"));
		if (humanUnits > compUnits) {
			return "Human";
		} else if (humanUnits < compUnits) {
			return "Computer";
		} else {
			return null;
		}
	}
	
	public void humanMove(int row, int col) {
		
	}
	
	public void computerMove() {
		
	}
}
