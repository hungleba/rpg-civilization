import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import characters.CivCharacter;

public class CivTextView {
	private static CivModel model;
	private static CivController controller;

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

		while(!controller.isGameOver()) {

			//PLAYER'S TURN	
			System.out.println("Enter command: ");
			String action =  sc.nextLine();
			boolean isEndTurn = false;
			boolean hasClicked = false;
			int row = 0;
			int col = 0;
			while (!isEndTurn) {
				String curRow;
				String curCol;
				switch (action) {
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
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					break;
				case "end": 
					controller.endTurn();
					isEndTurn = true;
					break;
				case "attack": 
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
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					hasClicked = false;
					break;
				case "move":
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

					printBoard();
					System.out.println("\nHere is the lastest board: ");
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					hasClicked = false;
					break;
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
							System.out.println("Enter command: ");
							action =  sc.nextLine();
							isValidCell = true;
						} else if (model.getCell(Integer.valueOf(curRow), Integer.valueOf(curCol)).getPlayer().equals("Computer")) {
							System.out.println("Coordinates occupied by opponent");
							System.out.println("Enter row: ");
							curRow = sc.nextLine();
							System.out.println("Enter column: ");
							curCol = sc.nextLine();
						} else {
							break;
						}
					}

					break;
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
					System.out.println("\nEnter command: ");
					action =  sc.nextLine();
					break;
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
			controller.endTurn();
			System.out.println("Here is the board after the computer's move: ");
			printBoard();
			System.out.println();
			
		}
		sc.close();
		String winner = controller.determineWinner();
		System.out.println("The winner is: " + winner);
	}

	private static void printBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				CivCell tempCell = model.getCell(i, j);
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
