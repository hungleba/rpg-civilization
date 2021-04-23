import javafx.scene.paint.Color;

public class CivCountry {
	private String country;
	private String fra = "FRANCE";
	private String ger = "GERMANY";
	private String ita = "ITALY";
	private Color[] colors;
	
	public CivCountry(String country) {
		colors = new Color[3];
		if (country.equals(fra)) {
			this.country = fra;
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
		} else if (country.equals(ger)) {
			this.country = ger;
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
		} else if(country.equals(ita)) {
			this.country = ita;
			colors[0] = Color.BLACK;
			colors[1] = Color.RED;
			colors[2] = Color.YELLOW;
		} 
	}
	
	public Color[] getFlag() {
		return colors;
	}
	
}
