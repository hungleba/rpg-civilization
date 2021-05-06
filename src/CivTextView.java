import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import characters.CivCharacter;

/**
 * This class serves as the view for the MVC (Mode/Control/View) architecture and let the 
 * player interact with the game through text forms. The player can interact with the game
 * and play against the AI by entering a series of commands the the necessary information
 * prompted by the program.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivTextView{
	
	/** CivModel object that serves as the model for this view */
	private static CivModel model;
	/** CivController object that serves as the controller for this view */
	private static CivController controller;

	/**
	 * Main method to launch the text view
	 * @param args command-line argument
	 *
	 */
	public static void main(String[] args) {
		model = new CivModel();
		controller = new CivController(model);
		Scanner sc = new Scanner(System.in);
		System.out.println("Valid commands for action: \n");
		System.out.println("end - To end  turn");
		System.out.println("click - To click on a cell");
		System.out.println("attack - To attack an opponents unit");
		System.out.println("move - To move your unit to a cell");
		System.out.println("spawn - To spawn a new unit");
		System.out.println("stats - To displayer the stats of a cell\n");

		// while the neither sides has lost all their units
		while(!controller.isGameOver()) {

			//PLAYER'S TURN	
			System.out.println("Enter command: ");
			String action =  sc.nextLine();
			boolean isEndTurn = false; // check whether the turn has ended
			boolean hasClicked = false; // check whether the player has clicked on a cell
			int row = 0; // the row the player is currently on
			int col = 0; // the column the player is currently on
			while (!isEndTurn) {
				String curRow; // the newest row coordinate being clicked on
				String curCol; // the newest column coordinate being clicked on
				switch (action) {
				// if the user click a cell
				case "click":
					//prompting the user the coordination
					System.out.println("\nEnter row: ");
					curRow = sc.nextLine();
					System.out.println("Enter column: ");
					curCol = sc.nextLine();
					
					while (Integer.valueOf(curRow) >= 10 || Integer.valueOf(curCol) >= 10) {
						System.out.println("\nCoordinates out of bounds! Please try again");
						System.out.println("Enter row: ");
						curRow = sc.nextLine();
						System.out.println("Enter column: ");
						curCol = sc.nextLine();
					}

					boolean isValidCell = false;
					while (!isValidCell) {
						if (model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol)).getPlayer() == null) {
							System.out.println("Empty cell");
							isValidCell = true;
						} else if (model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol)).getPlayer().equals("Computer")) {
							System.out.println("\nCoordinates occupied by opponent");
							System.out.println("Enter row: ");
							curRow = sc.nextLine();
							System.out.println("Enter column: ");
							curCol = sc.nextLine();
						} else {
							isValidCell = true;
						}

					}
					int[] playerCoor = new int[2];
					playerCoor[0] = Integer.valueOf(curRow);
					playerCoor[1] = Integer.valueOf(curCol);
					controller.handleClick(Integer.valueOf(curRow), Integer.valueOf(curCol), null);
					hasClicked = true;
					col = Integer.valueOf(curCol);
					row = Integer.valueOf(curRow);
					System.out.println("\nHere is the lastest board: ");
					printBoard();
					
					// prompt the user to enter new command
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					break;
				// if the user end their turn
				case "end": 
					controller.endTurn("Human");
					isEndTurn = true;
					break;
				// if the user attack a cell
				case "attack": 
					// user can only attack if the cell they has clicked on a cell and that cell is occupied by their units
					if (hasClicked && model.getCell(Integer.valueOf(row), Integer.valueOf(col)).getPlayer() != null) {
						Map<String, List<Integer>> tempMap = controller.allPossibleMoves(Integer.valueOf(row), Integer.valueOf(col), "Human");
						System.out.println("\nPossible moves for ATTACK" + ": ");
						for(Integer j : tempMap.get("Attack")) {
							System.out.print(j + " ");
						}
						System.out.println();

						//prompting the user the coordination
						System.out.println("Enter row: ");
						curRow = sc.nextLine();
						System.out.println("Enter column: ");
						curCol = sc.nextLine();
						controller.handleClick(Integer.valueOf(curRow), Integer.valueOf(curCol), null);
					} else if (!hasClicked) {
						System.out.println("You have not clicked on anything so you can't ATTACK");
					} else if (hasClicked && model.getCell(Integer.valueOf(row), Integer.valueOf(col)).getPlayer() == null) {
						System.out.println("You have clicked on empty cell => CAN'T ATTACK");
					}
					
					System.out.println("\nHere is the lastest board: ");
					printBoard();
					
					// prompt the user to enter new command
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					hasClicked = false;
					break;
				// if the user want to move a unit to a new cell
				case "move":
					// user can only move if the cell they has clicked on a cell and that cell is occupied by their units
					if (hasClicked && model.getCell(Integer.valueOf(row), Integer.valueOf(col)).getPlayer() != null) {
						Map<String, List<Integer>> tempMap = controller.allPossibleMoves(Integer.valueOf(row), Integer.valueOf(col), "Human");
						System.out.println("\nPossible moves for MOVE" + ": ");
						for(Integer j : tempMap.get("Move")) {
							System.out.print(j + " ");
						}
						System.out.println();
						//prompting the user the coordination
						System.out.println("Enter row: ");
						curRow = sc.nextLine();
						System.out.println("Enter column: ");
						curCol = sc.nextLine();
						controller.handleClick(Integer.valueOf(curRow), Integer.valueOf(curCol), null);
						
					} else if (!hasClicked){
						System.out.println("You have not clicked on anything so you can't MOVE");
					} else if (hasClicked && model.getCell(Integer.valueOf(row), Integer.valueOf(col)).getPlayer() == null) {
						System.out.println("You have clicked on empty cell => CAN'T MOVE");
					}

					System.out.println("\nHere is the lastest board: ");
					printBoard();
					
					// prompt the user to enter new command
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					hasClicked = false;
					break;
				// if the user wants to spawn a new unit
				case "spawn": 
					//prompting the user the coordination
					System.out.println("\nEnter row: ");
					curRow = sc.nextLine();
					System.out.println("Enter column: ");
					curCol = sc.nextLine();
					
					while (Integer.valueOf(curRow) >= 10 || Integer.valueOf(curCol) >= 10) {
						System.out.println("\nCoordinates out of bounds! Please try again");
						System.out.println("Enter row: ");
						curRow = sc.nextLine();
						System.out.println("Enter column: ");
						curCol = sc.nextLine();
					}
					
					System.out.println("Enter character: ");
					String character = sc.nextLine();
					
					List<String> chars = new ArrayList<String>();
					chars.add("Archer"); chars.add("Warrior"); chars.add("Knight");
					chars.add("Catapult"); chars.add("Guard"); 

					while (!chars.contains(character)) {
						System.out.println("\nInvalid character! Please try again");
						System.out.println("Enter character: ");
						character = sc.nextLine();
					}
					System.out.println(curRow);
					System.out.println(curCol);
					System.out.println(character);

					isValidCell = false;
					while (!isValidCell) {
						if (model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol)).getPlayer() == null) {
							playerCoor = new int[2];
							playerCoor[0] = Integer.valueOf(curRow);
							playerCoor[1] = Integer.valueOf(curCol);
							controller.setSpawned();
							controller.handleClick(Integer.valueOf(curRow), Integer.valueOf(curCol), character);
							System.out.println("Here is the lastest board: ");
							printBoard();
							
							// prompt the user to enter new command
							System.out.println("Enter command: ");
							action =  sc.nextLine();
							isValidCell = true;
						} else if (model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol)).getPlayer().equals("Computer")) {
							// prompt the user again if the cell they chose to spawn at is occupied by their opponent
							System.out.println("Coordinates occupied by opponent");
							// prompt new command
							System.out.println("Enter row: ");
							curRow = sc.nextLine();
							System.out.println("Enter column: ");
							curCol = sc.nextLine();
						} else {
							break;
						}
					}

					break;
				// if the user wants to check the stats of one their unit or the opponent's units
				case "stats":
					//prompting the user the coordination
					System.out.println("\nEnter row: ");
					curRow = sc.nextLine();
					System.out.println("Enter column: ");
					curCol = sc.nextLine();
					
					while (Integer.valueOf(curRow) >= 10 || Integer.valueOf(curCol) >= 10) {
						System.out.println("\nCoordinates out of bounds! Please try again");
						System.out.println("Enter row: ");
						curRow = sc.nextLine();
						System.out.println("Enter column: ");
						curCol = sc.nextLine();
					}
					
					CivCharacter tempChar = controller.displayStats(Integer.valueOf(curRow), Integer.valueOf(curCol));
					// Does not show any stats if there are no units in the clicked cell
					if (tempChar == null) {
						System.out.println("No character here");
					} else {
						model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol));
						System.out.println("\nName: " + tempChar.getName());
						System.out.println("Health: " + tempChar.getHealth());
						System.out.println("Attack: " + tempChar.getAttack());
						System.out.println("Range: " + tempChar.getRange());
						System.out.println("Level: " + tempChar.getLevel());
						System.out.println("Movement: " + tempChar.getMovement());
					}
					System.out.println("\nHere is the lastest board: ");
					printBoard();
					// prompt new command
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					break;
				// if the user typed in an invalid command
				default:
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();

				}

			}
			System.out.println("Here is the lastest board: ");
			printBoard();
			System.out.println("Your turn has ended!");
			System.out.println();

			//COMPUTER'S TURN
			controller.computerMove();
			controller.endTurn("Computer");
			System.out.println("Here is the board after the computer's move: ");
			printBoard();
			System.out.println();
			
		}
		sc.close();
		String winner = controller.determineWinner();
		System.out.println("The winner is: " + winner);
	}

	/**
	 * This is a private helper method used to print out the current status of the game's map
	 * with the player and units being expressed using the first letter of their name; for example,
	 * an archer belonging to the human player would be "H:A".
	 */
	private static void printBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				CivCell tempCell = model.getCell(i, j);
				// if the cell if empty then print null
				if (tempCell.getPlayer() == null) {
					System.out.print(null + " ");
				} else {
					System.out.print(tempCell.getPlayer().substring(0,1) + ":" + 
							tempCell.getCharacter().getName().substring(0,1) + "  ");
				}
			}
			System.out.println();
		}
	}
}
