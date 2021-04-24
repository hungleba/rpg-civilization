import characters.CivCharacter;

public class CivSelf {
	private String player;
	private CivCharacter role;
	
	public CivSelf() {
		this.player = null;
	}
	
	public void setChar(CivCharacter role) {
		this.role = role;
	}
	
	public CivCharacter getChar() {
		return role;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public String setPlayer() {
		return player;
	}
	
	
}
