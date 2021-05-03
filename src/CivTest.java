import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import characters.CivCharacter;


public class CivTest {
	/**
	 * Test method for CivController. Basic set up.
	 */
	@Test
	void testController() {
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		CivPlayer human = model.getPlayer("Human");
		CivPlayer computer = model.getPlayer("Computer");
		
		//test isGameOver()
		assertFalse(controller.isGameOver());
		
		//test spawning mechanism
		assertTrue(controller.isAbleToSpawn("Warrior","Human"));
		assertTrue(controller.isAbleToSpawn("Catapult","Human"));
		assertTrue(controller.isAbleToSpawn("Guard","Human"));
		assertTrue(controller.isAbleToSpawn("Knight","Human"));
		assertTrue(controller.isAbleToSpawn("Warrior","Human"));
		
		assertEquals(controller.displayStats(0, 1), null);
		assertEquals(controller.displayStats(9, 1), null);
		assertEquals(controller.displayStats(0, 9), null);
		assertEquals(controller.displayStats(8, 0), null);
		
		// invalid character
		assertEquals(controller.isAbleToSpawn("Tank", "Human"),false);
		
		// test spawning in invalid positions
		controller.handleClick(3,5, "Archer");
		controller.handleClick(7,5, "Archer");
		assertEquals(controller.displayStats(3,5), null);
		assertEquals(controller.displayStats(7,5), null);
		
		
		//test spawning
		controller.setSpawned();
		controller.handleClick(9,6, "Archer");
		CivCharacter tempChar = model.getCell(9,6).getCharacter();
		assertEquals(controller.displayStats(9,6), tempChar);
		controller.handleClick(8,7, "Warrior");
		tempChar = model.getCell(8,7).getCharacter();
		assertEquals(controller.displayStats(8,7), null);	
	}
	
	
	/**
	 * Test method for CivController. A random game.
	 */
	@Test
	void testController2() {
		CivModel model = new CivModel();
		CivController controller = new CivController(model);
		CivPlayer human = model.getPlayer("Human");
		CivPlayer computer = model.getPlayer("Computer");
		
		controller.setSpawned();
		controller.handleClick(9,8, "Catapult");
		controller.handleClick(9,8, "Catapult");
		assertEquals(controller.getIsMoved(), true);
		
	}
}
