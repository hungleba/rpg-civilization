import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import characters.CivCharacter;
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
	 * Test method for CivController and CivModel. Basic set up.
	 */
	@Test
	void test1() {
		System.out.println("TEST 1:");
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		
		//test isGameOver()
		assertFalse(controller.isGameOver());
		
		//test isGameBegin()
		assertTrue(controller.isGameBegin());
		
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
		
		
		//test spawning
		controller.setSpawned();
		controller.handleClick(9,6, "Archer");
		CivCharacter tempChar = model.getCell(9,6).getCharacter();
		assertEquals(controller.displayStats(9,6), tempChar);
		controller.handleClick(8,7, "Warrior");
		assertEquals(controller.displayStats(8,7), null);	
		controller.handleClick(3,5, "Warrior");
		assertEquals(controller.displayStats(3,5), null);
		
		System.out.println("\n");
	}
	
	
	/**
	 * Test method for CivController and CivModel. Human will not attack.
	 */
	@Test
	void test2() {
		System.out.println("TEST 2:");
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		CivPlayer human = model.getPlayer("Human");
		CivPlayer computer = model.getPlayer("Computer");
		
		// set countries
		assertEquals(controller.determineWinner(), null);

		// human turn
		controller.setSpawned();
		controller.handleClick(9,8, "Catapult");
		controller.handleClick(9,8, "Catapult");
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
				
		
		assertTrue(controller.isGameOver());

	}
	
	
	
	/**
	 * Test method for CivController and CivModel. Load previous game.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	void test3() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("TEST 2:");
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		CivPlayer human = model.getPlayer("Human");
		CivPlayer computer = model.getPlayer("Computer");

		controller.setSpawned();
		controller.handleClick(8,9, "Knight");
		controller.endTurn("Human");
		
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test_save_game.dat"));
//		oos.writeObject(model);
//		oos.close();
//		
//		CivModel model2 = new CivModel("test_save_game.dat");
//		model2.getCell(8, 9);
	}
}
