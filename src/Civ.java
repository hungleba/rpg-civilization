import javafx.application.Application;
/**
 * This class serves as the main class for the Civilization game. The program will
 * launch the GUI view or Text view based on command line argument. Default option is GUI view.
 * 
 * The GUI view will also include a save/load feature that loads the most recent game every
 * time the application is launched. User can also choose to start a new game by clicking "New game" 
 * on the menu bar.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class Civ {
	
	/**
	 * Main method for lauching the application
	 * @param args cmd line argument to launch appropriate view 
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].toLowerCase().equals("-text")) {
				//Application.launch(CivTextView.class, args);
			}else if ((args[0].toLowerCase().equals("-window"))) {
				Application.launch(CivGUIView.class, args);
			}else {
				System.out.println("Invalid Command. Must choose between -text or -window. No argument means default mode -window.");
			}
		}else {
			Application.launch(CivGUIView.class, args);
		}
		
	}
}