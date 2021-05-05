import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import characters.CivCharacter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


@SuppressWarnings("deprecation")
public class CivGUIView extends Application implements Observer{

	private static final int DIMENSION = 10;
	private CivModel model;
	private CivController controller;
	private BorderPane borderPane;
	private GridPane bigGridPane;
	private VBox vbox;
	private MenuBar menuBar;
	private TilePane tilePane;
	private String currChar;
	private boolean isSpawnArea;

	public static void main(String[] args) {
		launch();
	}

	public CivGUIView() {
		File file = new File("save_game.dat");
		if (file.exists()) {
			try {
				model = new CivModel("save_game.dat");
			} catch (FileNotFoundException e) {
				System.out.println("Can not find the saved game!");
			} catch (ClassNotFoundException e) {
				System.out.println("Can not load the board for the game!");
			} catch (IOException e) {
				System.out.println("Can not load the game!");
			}
		} else {
			model = new CivModel();
		}
		isSpawnArea = false;
		controller = new CivController(model);
		borderPane = new BorderPane();
		menuBar = new MenuBar();
		bigGridPane = new GridPane();
		vbox = new VBox();
		tilePane = new TilePane();
		currChar = null;
		model.addObserver(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Civilization");
		// Configure border pane structure
		borderPane.setTop(menuBar);
		borderPane.setCenter(bigGridPane);
		borderPane.setRight(vbox);
		borderPane.setBottom(tilePane);
		// Add contents to panes
		addMenuBar(primaryStage);
		addGridPane(primaryStage);
		addVBox();
		addTilePane();
		// Final scene

		Scene scene = new Scene(borderPane, 950, 700);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest((WindowEvent we) -> {
			if (!controller.isGameOver() && !controller.isGameBegin()) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream("save_game.dat"));
					oos.writeObject(model);
					oos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		//Start the game
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		model = (CivModel) o;
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				CivCell cell = model.getCell(j, i);
				updateCell(i, j, cell);
			}
		}
		addTilePane();
	}


	private void addVBox() {
		GridPane charsPane = new GridPane();
		addCharsPane(charsPane);
		charsPane.setHgap(10);
		charsPane.setVgap(10);
		charsPane.setPadding(new Insets(5));
		vbox.getChildren().add(0, charsPane);
		vbox.setStyle("-fx-border-color: black;\n" +
				"-fx-border-insets: 2;\n" +
				"-fx-border-width: 3;\n" +
				"-fx-border-style: dashed;\n");
		vbox.setPrefSize(300, 700);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
	}

	private void addCharsPane(GridPane charsPane) {
		spawnArcherBtn(charsPane);
		spawnCatapultBtn(charsPane);
		spawnGuardBtn(charsPane);
		spawnKnightBtn(charsPane);
		spawnWarriorBtn(charsPane);
		addEndBtn(charsPane);
	}

	private void spawnArcherBtn(GridPane charsPane) {
		Button archer = new Button("Archer ($3)");
		ImageView view = getSpawnView("Archer");
		archer.setGraphic(view);
		charsPane.add(archer, 0, 0);
		archer.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Archer";
		});
		archer.setPrefHeight(48);
		archer.setPrefWidth(140);
		archer.setOnMouseClicked((event) -> {
			//highLightspawnArea(3);
		});
	}

	private void spawnCatapultBtn(GridPane charsPane) {
		Button catapult = new Button("Catapult ($7)");
		ImageView view = getSpawnView("Catapult");
		catapult.setGraphic(view);
		charsPane.add(catapult, 0, 1);
		catapult.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Catapult";
		});
		catapult.setPrefHeight(48);
		catapult.setPrefWidth(140);
		catapult.setOnMouseClicked((event) -> {
			//highLightspawnArea(7);
		});
	}

	private void spawnGuardBtn(GridPane charsPane) {
		Button guard = new Button("Guard ($5)");
		ImageView view = getSpawnView("Guard");
		guard.setGraphic(view);
		charsPane.add(guard, 0, 2);
		guard.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Guard";
		});
		guard.setPrefHeight(48);
		guard.setPrefWidth(140);
		guard.setOnMouseClicked((event) -> {
			//highLightspawnArea(5);
		});
	}

	private void spawnKnightBtn(GridPane charsPane) {
		Button knight = new Button("Knight ($6)");
		ImageView view = getSpawnView("Knight");
		knight.setGraphic(view);
		charsPane.add(knight, 1, 0);
		knight.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Knight";
		});
		knight.setPrefHeight(48);
		knight.setPrefWidth(140);
		knight.setOnMouseClicked((event) -> {
			//highLightspawnArea(6);
		});
	}

	private void spawnWarriorBtn(GridPane charsPane) {
		Button warrior = new Button("Warrior ($2)");
		ImageView view = getSpawnView("Warrior");
		warrior.setGraphic(view);
		charsPane.add(warrior, 1, 1);
		warrior.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Warrior";
		});
		warrior.setPrefHeight(48);
		warrior.setPrefWidth(140);
		warrior.setOnMouseClicked((event) -> {
			//highLightspawnArea(2);
		});
	}

	private void addEndBtn(GridPane charsPane) {
		Button endBtn = new Button("End Turn");
		endBtn.setStyle("-fx-font-size: 15px;\n"
				+ "    -fx-padding: 1px;\n"
				+ "    -fx-background-color: red;\n"
				+ "    -fx-border-size: 1px;\n"
				+ "    -fx-border-style: solid");
		endBtn.setPrefHeight(48);
		endBtn.setPrefWidth(140);
		charsPane.add(endBtn, 1, 2);
		endBtn.setOnAction((event) -> {
			if (!controller.isGameOver()) {
				controller.endTurn("Human");
				controller.computerMove();
				if (controller.isGameOver()) {
					displayAlertWinner();
				}
			}
		});
	}
/**
	private void highLightspawnArea(int cost) {
		if (model.getPlayer("Human").getGold() < cost)
			return;
		for (int x = 0; x < 10; x++) { // last two rows
			for (int y = 8; y < 10; y++) { // Elements within the last two rows
				GridPane rowPane = (GridPane) bigGridPane.getChildren().get(x); //x-axis
				StackPane stack = (StackPane) rowPane.getChildren().get(y); //y-axis
				stack.setBorder(new Border(new BorderStroke(Color.GREY, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.RED, 5, 0.75, 0, 0));
				isSpawnArea = true;
			}
		}
	}

	private void hideSpawnArea() {
		for (int x = 0; x < 10; x++) {
			for (int y = 8; y < 10; y++) {
				GridPane rowPane = (GridPane) bigGridPane.getChildren().get(x);
				StackPane stack = (StackPane) rowPane.getChildren().get(y);
				stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setEffect(null);
				isSpawnArea = false;
			}
		}
	}*/

	private void displayAlertWinner() {
		File file = new File("save_game.dat");
		file.delete();
		String message = "You won!";
		if (controller.determineWinner() == null) {
			message = "Draw!";
		} else if (controller.determineWinner().equals("Computer")) {
			message = "You lost";
		}
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setContentText(message);
		a.setHeaderText("Civilization");
		a.showAndWait();
	}

	private ImageView getSpawnView(String character) {
		character = character.toLowerCase();
		String url = "src/icons/" + character +"_icon.png";
		File file = new File(url);
		Image img = new Image(file.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(40);
		imgView.setFitWidth(40);
		return imgView;
	}

	private void addTilePane() {
		int humanMoney = model.getPlayer("Human").getGold();
		int computerMoney = model.getPlayer("Computer").getGold();
		Label score = new Label("Human: $"+humanMoney+" - Computer: $"+computerMoney);
		score.setStyle("-fx-font-size: 15px;\n"
				+"		-fx-padding: 7px;\n");
		tilePane.getChildren().clear();
		tilePane.getChildren().add(score);
		tilePane.setPrefHeight(100);
	}

	private void addGridPane(Stage primaryStage) {
		bigGridPane.setMaxWidth(630);
		Color color = Color.GREEN;
		if (model.getColor().equals("Blue")) {
			color = Color.BLUE;
		} else if (model.getColor().equals("Grey")) {
			color = Color.GREY;
		}
		bigGridPane.setBackground(new Background(new 
				BackgroundFill(color, new CornerRadii(0), Insets.EMPTY)));
		bigGridPane.setPadding(new Insets(8));
		addStackPane(primaryStage);
	}

	private void addStackPane(Stage primaryStage) {
		for (int i = 0; i < DIMENSION; i++) {
			GridPane innerGrid = new GridPane();
			for (int j = 0; j < DIMENSION; j++) {
				StackPane stack = new StackPane();
				stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setPadding(new Insets(10));
				ImageView imgView = new ImageView();
				imgView.setFitHeight(40);
				imgView.setFitWidth(40);
				imgView.setVisible(false);
				stack.getChildren().clear();
				stack.getChildren().add(imgView);
				addEvent(stack, i, j, primaryStage);
				innerGrid.addRow(j, stack);
			}
			bigGridPane.addColumn(i, innerGrid);
		}
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				CivCell cell = model.getCell(j, i);
				updateCell(i, j, cell);
			}
		}

	}

	private void addEvent(StackPane stack, int i, int j, Stage primaryStage) {
		Popup popup = new Popup();
		stack.setOnMouseClicked((event) -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				String info = getStatsInfo(j, i);
				Label label = new Label(info);
				label.setPadding(new Insets(5));
				label.setMinWidth(285);
				// linear-gradient(#808080, #707070)
				label.setStyle("\n"
						+ "    -fx-text-fill: white;\n"
						+ "    -fx-font-family: \"Courier New\";\n"
						+ "    -fx-font-weight: bold;\n"
						+ "    -fx-background-color: #b5651d;\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
				popup.setX(primaryStage.getX()+655);
				popup.setY(primaryStage.getY()+55);
				popup.getContent().clear();
				popup.getContent().add(label);
				popup.show(primaryStage);
			}
		});
		stack.setOnMouseExited((event) -> {
			if (isSpawnArea)
				return;
			stack.setEffect(null);
			popup.hide();
			Map<String, List<Integer>> validMoves = controller.allPossibleMoves(j, i, "Human");
			if (validMoves != null) {
				List<Integer> attack = validMoves.get("Attack");
				List<Integer> move = validMoves.get("Move");
				for (int coord : attack) {
					int row = coord / DIMENSION;
					int col = coord % DIMENSION;
					removeCellEffect(col, row);
				}
				for (int coord : move) {
					int row = coord / DIMENSION;
					int col = coord % DIMENSION;
					removeCellEffect(col, row);
				}
			}
		});
		stack.setOnMousePressed((event) -> {
			if (event.getButton() == MouseButton.PRIMARY && !controller.isGameOver()) {
				stack.setEffect(new InnerShadow());
				controller.handleClick(j, i, currChar);
				if (controller.isGameOver()) {
					displayAlertWinner();
				}
			}
			if (isSpawnArea) {
				//hideSpawnArea();
			}
		});
		stack.setOnMouseEntered((event) -> {
			//if (isSpawnArea)
				//return;
			Map<String, List<Integer>> validMoves = controller.allPossibleMoves(j, i, "Human");
			if (validMoves != null) {
				List<Integer> attack = validMoves.get("Attack");
				List<Integer> move = validMoves.get("Move");
				for (int coord : attack) {
					int row = coord / DIMENSION;
					int col = coord % DIMENSION;
					displayCellEffect(col, row, "Attack");
				}
				for (int coord : move) {
					int row = coord / DIMENSION;
					int col = coord % DIMENSION;
					displayCellEffect(col, row, "Move");
				}
			}
		});
	}
	
//	private Timeline getTimeLineAttack(int row, int col) {
//		GridPane rowPane = (GridPane) bigGridPane.getChildren().get(col);
//		StackPane stack = (StackPane) rowPane.getChildren().get(row);
//		Timeline timeline = new Timeline() ;
//		timeline.setCycleCount( Animation.INDEFINITE ) ;
//		KeyFrame keyframe = new KeyFrame( Duration.millis( 500 ),
//				(event) -> {
//					ImageView imgView = (ImageView) stack.getChildren().get(0);
//					if (imgView.isVisible()) {
//						imgView.setVisible(false);
//					} else {
//						imgView.setVisible(true);
//					}
//				}) ;
//
//		timeline.getKeyFrames().add( keyframe);
//		return timeline;
//	}

	private String getStatsInfo(int row, int col) {
		CivCharacter character = controller.displayStats(row, col);
		String message = "";
		if (character == null) {
			message += "No information on this cell!";
		} else {
			String name = model.getCell(row, col).getPlayer();
			message += "Type: " + character.getName() + "\n";
			message += "Attack: " + String.valueOf(character.getAttack()) + "\n";
			message += "Attack Range: " + String.valueOf(character.getRange()) + "\n";
			message += "Movement Range: " + String.valueOf(character.getMovement()) + "\n";
			message += "Health: " + String.valueOf(character.getHealth()) + "\n";
			message += "Current Level: " + String.valueOf(character.getLevel()) + "\n";
			message += "Max Level: " + String.valueOf(CivCharacter.getMaxLevel()) + "\n";
			if (name.equals("Human")) {
				message += "Did This Piece Move/Attack: " + String.valueOf(character.getIsMoved()) + "\n";
			}
		}
		return message;
	}

	private void setBackgroundWowFactor(Menu menu) {
		Menu background = new Menu("Set Background");
		MenuItem blue = new MenuItem("Blue Theme");
		MenuItem grey = new MenuItem("Grey Theme");
		MenuItem green = new MenuItem("Green Theme");
		background.getItems().add(blue);
		background.getItems().add(grey);
		background.getItems().add(green);
		blue.setOnAction((ActionEvent ae) -> {
			bigGridPane.setBackground(new Background(new 
					BackgroundFill(Color.BLUE, new CornerRadii(0), Insets.EMPTY)));
			controller.setColor("Blue");
		});
		grey.setOnAction((ActionEvent ae) -> {
			bigGridPane.setBackground(new Background(new 
					BackgroundFill(Color.GREY, new CornerRadii(0), Insets.EMPTY)));
			controller.setColor("Grey");
		});
		green.setOnAction((ActionEvent ae) -> {
			bigGridPane.setBackground(new Background(new 
					BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
			controller.setColor("Green");
		});
		menu.getItems().add(background);
	}

	private void addMenuBar(Stage primaryStage) {
		// MenuItems
		MenuItem newGame = new MenuItem("New Game");
		MenuItem gameRule = new MenuItem("Game Rule");
		MenuItem about = new MenuItem("About");

		// Menu
		Menu menu = new Menu("Menu");
		menu.getItems().add(newGame);
		menu.getItems().add(gameRule);
		menu.getItems().add(about);
		setBackgroundWowFactor(menu);

		// Menu bar
		menuBar.getMenus().add(menu);
		newGame.setOnAction((ActionEvent ae) -> {
			model.deleteObserver(this);
			model = new CivModel();
			controller = new CivController(model);
			model.addObserver(this);
			for (int i=0; i<DIMENSION; i++) {
				for (int j=0; j<DIMENSION; j++) {
					CivCell cell = model.getCell(j, i);
					updateCell(i, j, cell);
				}
			}
			bigGridPane.setBackground(new Background(new 
					BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
			addTilePane();
			File file = new File("save_game.dat");
			file.delete();
		});
		gameRule.setOnAction((ActionEvent ae) -> {
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("Game Rule");
			String message = "This game simulates Civilization game. Human and Computer both "
					+ "start with 0 units and 10 golds. Each side can have a maximum 10 units.\n\n"
					+ "There are 5 types of units: Archer, Catapult, Guard, Knight, Warrior. Each "
					+ "unit has different stats and costs, starting at level 1."
					+ "\n\nIn a single turn, a player (Human or Computer) can spawn as many "
					+ "units as they want as long has they have enough golds to buy the units, but for "
					+ "each of their units, they can only do at most 1 move/attack action. A newly spawned "
					+ "unit can not attack/move in that turn.\n\nEvery time an unit A of a player kills the "
					+ "opponent's unit, the player gains bonus gold based on the level of the opponent's unit "
					+ "(for example, if the opponent's unit is Level 3, then the player gains 3 more golds), "
					+ "and the unit A level up, gaining bonus health and bonus stats based "
					+ "the type of unit A. The maximum level can be reached is Level 5.\n\n"
					+ "After each turn, the player (Human or Computer) gains 2 more golds. The game ends when "
					+ "one player has no units on the board, and the winner of the game is the one who still "
					+ "has units on the board.";
			a.setContentText(message);
			a.setHeaderText("Civilization");
			a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			a.showAndWait();
		});
		about.setOnAction((ActionEvent ae) -> {
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("About");
			String message = "Anh Nguyen Phung\nHung Le Ba\nPeter Vo\nThu Tra Ly Huong";
			a.setContentText(message);
			a.setHeaderText("Credits");
			a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			a.showAndWait();
		});
	}

	private void updateCell(int x, int y, CivCell cell) {
		GridPane rowPane = (GridPane) bigGridPane.getChildren().get(x);
		StackPane stack = (StackPane) rowPane.getChildren().get(y);
		String player = cell.getPlayer();
		if (player != null) {
			Color color = Color.AQUA;
			if (player.equals("Computer")) {
				color = Color.VIOLET;
			}
			CivCharacter character = cell.getCharacter();
			String name = character.getName();
			ImageView imgView = getSpawnView(name);
			stack.getChildren().clear();
			stack.getChildren().add(imgView);
			stack.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), Insets.EMPTY)));;
		} else {
			ImageView imgView = (ImageView) stack.getChildren().get(0);
			imgView.setVisible(false);
			stack.setBackground(new Background(new 
					BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));;
		}
	}

	private void displayCellEffect(int col, int row, String type) {
		GridPane rowPane = (GridPane) bigGridPane.getChildren().get(col);
		StackPane stack = (StackPane) rowPane.getChildren().get(row);
		if (type.equals("Attack")) {
			stack.setEffect(new SepiaTone());
		} else if (type.equals("Move")) {
			stack.setEffect(new Glow());
		}
	}

	private void removeCellEffect(int col, int row) {
		GridPane rowPane = (GridPane) bigGridPane.getChildren().get(col);
		StackPane stack = (StackPane) rowPane.getChildren().get(row);
		stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		stack.setEffect(null);
	}

}
