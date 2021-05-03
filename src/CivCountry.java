import java.io.Serializable;

import javafx.scene.paint.Color;

public class CivCountry implements Serializable{

	private static final long serialVersionUID = 1L;
	private String country;
	private String fra = "FRANCE";
	private String ger = "GERMANY";
	private String ita = "ITALY";
	private Color[] colors;
	private String name = "";
	
	public CivCountry(String country) {
		colors = new Color[3];
		if (country.equals(fra)) {
			this.country = fra;
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
			name = "FRANCE";
		} else if (country.equals(ger)) {
			this.country = ger;
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
			name = "GERMANY";
		} else if(country.equals(ita)) {
			this.country = ita;
			colors[0] = Color.BLACK;
			colors[1] = Color.RED;
			colors[2] = Color.YELLOW;
			name = "ITALY";
		} 
		this.country = country;
	}
	
	public Color[] getFlag() {
		return colors;
	}

	public String getName() {
		return name;
	}
}
