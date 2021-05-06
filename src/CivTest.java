import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import characters.CivArcher;
import characters.CivCatapult;
import characters.CivCharacter;
import characters.CivGuard;
import characters.CivKnight;
import characters.CivWarrior;
/**
 * This class serves as tests for CivController and CivModel
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivTest {
	/**
	 * Test method for CivController and CivModel. 
	 */
	@Test
	void test_controler_1() {
		System.out.println("TEST CONTROLLER 1:");
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		
		//test isGameOver()
		assertFalse(controller.isGameOver());
		
		//test isGameBegin()
		assertTrue(controller.isGameBegin());
		
		//test display stats
		assertEquals(controller.displayStats(0, 1), null);
		assertEquals(controller.displayStats(9, 1), null);
		assertEquals(controller.displayStats(0, 9), null);
		assertEquals(controller.displayStats(8, 0), null);
		
		// test spawning in invalid positions
		controller.handleClick(3,5, "Archer");
		assertEquals(controller.displayStats(3,5), null);
		controller.setSpawned();
		controller.handleClick(7,5, "Archer");
		assertEquals(controller.displayStats(7,5), null);
		controller.setSpawned();
		controller.handleClick(8,5, "Archerr");
		assertEquals(controller.displayStats(8,5), null);
		
		
		//test spawning
		controller.setSpawned();
		controller.handleClick(9,6, "Archer");
		CivCharacter tempChar = model.getCell(9,6).getCharacter();
		assertEquals(controller.displayStats(9,6), tempChar);
		controller.handleClick(8,7, "Warrior");
		assertEquals(controller.displayStats(8,7), null);	
		controller.handleClick(3,5, "Warrior");
		assertEquals(controller.displayStats(3,5), null);
		
		assertEquals(controller.getCell(8, 5), model.getCell(8, 5));
		for (int i = 0; i < 4; i++) {
			controller.endTurn("Human");	
		}
		controller.setSpawned();
		controller.handleClick(9,8, "Warrior");
		controller.handleClick(9,8, "Warrior");
		
		// test setter & getter for players
		CivPlayer human = model.getPlayer("Human");
		CivPlayer comp = model.getPlayer("Computer");
		assertEquals(controller.getPlayer("Human"), human);
		assertEquals(controller.getPlayer("Computer"), comp);
		assertFalse(controller.getPlayer("Human").equals(comp));
		assertFalse(controller.getPlayer("Computer").equals(human));

		
	}

	
	
	/**
	 * Test method for CivController and CivModel. Human will not attack.
	 */
	@Test
	void test_controller_2() {
		System.out.println("TEST CONTROLLER 2:");
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		model.setColor("Pink");
		assertEquals(model.getColor(), "Pink");
		
		// set countries
		assertEquals(controller.determineWinner(), null);

		// human turn
		controller.setSpawned();
		controller.handleClick(9,8, "Guard");
		controller.handleClick(9,8, "Guard");
		assertEquals(controller.getIsMoved(), true);
		controller.handleClick(9,9, "");
		controller.endTurn("Human");
		assertEquals(controller.determineWinner(), "Human");
		
		// computer turn
		controller.computerMove();
		assertFalse(controller.isGameOver());
		
		// human turn
		assertEquals(controller.allPossibleMoves(5, 5, "Human"),null);
		controller.handleClick(9, 8, "");
		assertEquals(controller.allPossibleMoves(9, 8, "Computer"),null);
		System.out.println("Possible moves for 9,8: " +controller.allPossibleMoves(9, 8, "Human"));
		controller.handleClick(7, 6, "");
		controller.endTurn("Human");
		
		// computer turn
		controller.computerMove();
		
		// human turn
		controller.handleClick(7, 6, "");
		System.out.println("Possible moves for 7,6: " +controller.allPossibleMoves(7, 6, "Human"));
		controller.handleClick(5, 4, "");
		controller.endTurn("Human");
		
		// computer turn
		controller.computerMove();
		
		// human turn
		controller.handleClick(5, 4, "");
		System.out.println("Possible moves for 5,4: " +controller.allPossibleMoves(5, 4, "Human"));			
		controller.handleClick(3, 3, "");
		controller.endTurn("Human");
				
		// computer turn
		controller.computerMove();
		
		// human turn
		controller.handleClick(3, 3, "");
		System.out.println("Possible moves for 3,3: " +controller.allPossibleMoves(3, 3, "Human"));			
		controller.handleClick(1, 5, "");
		controller.endTurn("Human");
						
		// computer turn
		controller.computerMove();
		assertEquals(controller.determineWinner(), "Computer");
		
		// human turn
		controller.endTurn("Human");
								
		// computer turn
		controller.computerMove();
		assertEquals(controller.determineWinner(), "Computer");
		while (!controller.isGameOver()) {
			controller.computerMove();
			controller.endTurn("Computer");
		}
		assertTrue(controller.isGameOver());
	}
	
	
	
	/**
<<<<<<< HEAD
	 * Test methods CivModel & CivCharacte, character classes
=======
	 * Test methods CivModel and CivCharacte, character classes
>>>>>>> 3e8597f17bb8c7f9005b9790f4a7c68d98e138f9
	 * 
	 * @throws IOException when IOEException is caught
	 * @throws FileNotFoundException when file can not be found
	 * @throws ClassNotFoundException when the class is not found
	 */
	@Test
	void test_model() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("TEST MODEL:");
		
		CivCharacter cha = new CivCharacter();
		CivCharacter arc = new CivArcher();
		CivCharacter warr = new CivWarrior();
		CivCharacter kni = new CivKnight();
		CivCharacter cat = new CivCatapult();
		CivCharacter gua = new CivGuard();
		cat.levelUp();
		arc.levelUp();
		warr.levelUp();
		gua.levelUp();
		kni.levelUp();
		cha.levelUp();
		assertEquals(cat.getLevel(), 2);
		assertEquals(arc.getLevel(), 2);
		assertEquals(kni.getLevel(), 2);
		assertEquals(warr.getLevel(), 2);
		assertEquals(gua.getLevel(), 2);

		CivModel model = new CivModel(0);
		CivController controller = new CivController(model);

		controller.setSpawned();
		controller.handleClick(8,9, "Knight");
		controller.endTurn("Human");
		
		// Test adding a model saved game with old model
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test_save_game.dat"));
		oos.writeObject(model);
		oos.close();
		
		CivModel model2 = new CivModel("test_save_game.dat");
		assertEquals(model2.getCell(8, 9).getPlayer(), "Human");
		
		// Test adding a model saved game with null game board
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("test_save_game2.dat"));
		oos2.writeObject(null);
		oos2.close();
		
	}
}