import java.io.Serializable;

import javafx.scene.paint.Color;
/**
 * This class serves as a country in the game. Each country will have its own
 * flag color and name.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivCountry implements Serializable{

	private static final long serialVersionUID = 1L;
	private Color[] colors;
	private String name = "";
	
	/**
	 * Constructor. Creates an instance of CivCountry with the given name.
	 * @param country a string representing the country's name
	 *
	 */
	public CivCountry(String country) {
		colors = new Color[3];
		if (country.toUpperCase().equals("FRANCE")) {
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
			name = "FRANCE";
		} else if (country.toUpperCase().equals("GERMANY")) {
			colors[0] = Color.BLUE;
			colors[1] = Color.WHITE;
			colors[2] = Color.RED;
			name = "GERMANY";
		} else if(country.toUpperCase().equals("ITALY")) {
			colors[0] = Color.BLACK;
			colors[1] = Color.RED;
			colors[2] = Color.YELLOW;
			name = "ITALY";
		} 
	}
	
	/**
	 * Get three flag colors of the current country for use in the GUI view
	 * @return the country's flag colors
	 */
	public Color[] getFlag() {
		return colors;
	}

	/**
	 * Get name of the current country 
	 * @return the country's name
	 */
	public String getName() {
		return name;
	}
}
