import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class CivGUIView extends Application{

	private BorderPane borderPane;
	private GridPane bigGridPane;
	private VBox vbox;
	private MenuBar menuBar;
	private TilePane tilePane;
	private String currChar;
	private int currRow;
	private int currCol;

	public static void main(String[] args) {
		Application.launch();
	}

	public CivGUIView() {
		borderPane = new BorderPane();
		menuBar = new MenuBar();
		bigGridPane = new GridPane();
		vbox = new VBox();
		tilePane = new TilePane();
		currChar = new String();
		currRow = -1;
		currCol = -1;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CIV");
		// Configure border pane structure
		borderPane.setTop(menuBar);
		borderPane.setLeft(bigGridPane);
		borderPane.setRight(vbox);
		borderPane.setBottom(tilePane);
		// Add contents to panes
		addMenuBar();
		addGridPane(primaryStage);
		addVBox();
		addTilePane();
		// Final scene
		Scene scene = new Scene(borderPane, 900, 700);
		primaryStage.setScene(scene);
		//Start the game
		primaryStage.show();
	}

	private void addVBox() {
		GridPane charsPane = new GridPane();
		addCharsPane(charsPane);
		vbox.getChildren().add(charsPane);
		vbox.setStyle("-fx-border-color: red;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n");
		vbox.setPrefSize(250, 700);
	}
	
	private void addCharsPane(GridPane charsPane) {
		Button archer = new Button("Archer");
		charsPane.getChildren().add(archer);
	}

	private void addTilePane() {
		Label score = new Label("Here to show score");
		tilePane.getChildren().add(score);
	}

	private void addGridPane(Stage primaryStage) {
		bigGridPane.setMaxWidth(630);
		bigGridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
		bigGridPane.setPadding(new Insets(8));
		addStackPane(primaryStage);
	}

	private void addStackPane(Stage primaryStage) {
		for (int i = 0; i < 10; i++) {
			GridPane innerGrid = new GridPane();
			for (int j = 0; j < 10; j++) {
				StackPane stack = new StackPane();
				stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setPadding(new Insets(10));
				File file = new File("src/Icons/archer_icon.png");
				Image img = new Image(file.toURI().toString());
				ImageView imgView = new ImageView(img);
				imgView.setFitHeight(40);
				imgView.setFitWidth(40);
				imgView.setVisible(false);
				stack.getChildren().add(imgView);
				addEvent(stack, i, j, primaryStage);
				innerGrid.addRow(j, stack);
			}
			bigGridPane.addColumn(i, innerGrid);
		}
		
	}

	private void addEvent(StackPane stack, int i, int j, Stage primaryStage) {
		stack.setOnMouseClicked((event) -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				currRow = i;
				currCol = j;
				Popup popup = new Popup();
				// String info = getInfo(i, j);
				String info = "This is a sample popup\nsecond line";
				Label label = new Label(info);
				// linear-gradient(#808080, #707070)
				label.setStyle("\n"
						+ "    -fx-text-fill: white;\n"
						+ "    -fx-font-family: \"Arial Narrow\";\n"
						+ "    -fx-font-weight: bold;\n"
						+ "    -fx-background-color: grey;\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
				popup.setX(900);
				popup.setY(110);
				popup.getContent().add(label);
				popup.show(primaryStage);
			}
		});
		stack.setOnMousePressed((event) -> {
			if (event.getButton() != MouseButton.SECONDARY) {
				stack.setEffect(new DropShadow());
			}
		});
		stack.setOnMouseReleased((event) -> {
			if (event.getButton() != MouseButton.SECONDARY) {
				stack.setBorder(new Border(new BorderStroke(Color.WHITE, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				ImageView imgView = (ImageView) stack.getChildren().get(0);
				imgView.setVisible(true);
			}
		});
		
	}

	private void addMenuBar() {
		// MenuItems
		MenuItem newGame = new MenuItem("New Game");
		// Menu
		Menu file = new Menu("File");
		file.getItems().add(newGame);
		// Menu bar
		menuBar.getMenus().add(file);
	}

}
