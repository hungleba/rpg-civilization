
import java.io.Serializable;

import characters.CivCharacter;

public class CivCell implements Serializable{

	private static final long serialVersionUID = 1L;
	private String player;
	private CivCharacter character;
	
	public CivCell() {
		this.player = null;
		this.character = null;
	}
	
	public void setCharacter(CivCharacter character) {
		this.character = character;
	}
	
	public CivCharacter getCharacter() {
		return character;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public String getPlayer() {
		return player;
	}
}
