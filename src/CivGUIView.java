import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;


public class CivGUIView extends Application{

	private BorderPane borderPane;
	private GridPane bigGridPane;
	private MenuBar menuBar;
	private TilePane tilePane;

	public static void main(String[] args) {
		Application.launch();
	}

	public CivGUIView() {
		borderPane = new BorderPane();
		menuBar = new MenuBar();
		bigGridPane = new GridPane();
		tilePane = new TilePane();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CIV");
		// Configure border pane structure
		borderPane.setTop(menuBar);
		borderPane.setCenter(bigGridPane);
		borderPane.setBottom(tilePane);
		// Add contents to panes
		addMenuBar();
		addGridPane();
		addTilePane();
		// Final scene
		Scene scene = new Scene(borderPane, 1000, 700);
		primaryStage.setScene(scene);
		//Start the game
		primaryStage.show();
	}

	private void addTilePane() {
		Label score = new Label("Here to show score");
		tilePane.getChildren().add(score);
	}

	private void addGridPane() {
		bigGridPane.setMaxHeight(1000);
		bigGridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
		bigGridPane.setPadding(new Insets(8));
		addStackPane();
	}

	private void addStackPane() {
		for (int i = 0; i < 10; i++) {
			GridPane innerGrid = new GridPane();
			for (int j = 0; j < 10; j++) {
				StackPane stack = new StackPane();
				stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setPadding(new Insets(10));
				Circle circl = new Circle(20, Color.TRANSPARENT);
				stack.getChildren().add(circl);
				addEvent(stack, i, j);
				innerGrid.addRow(j, stack);
			}
			bigGridPane.addColumn(i, innerGrid);
		}
		
	}

	private void addEvent(StackPane stack, int i, int j) {
//		stack.setOnMouseClicked((event) -> {
//			int[] coor = {i, j};
//		});
		stack.setOnMousePressed((event) -> {
			stack.setEffect(new DropShadow());
		});
		stack.setOnMouseReleased((event) -> {
			stack.setBorder(new Border(new BorderStroke(Color.WHITE, 
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			Circle hex = (Circle) stack.getChildren().get(0);
			hex.setFill(Color.ORANGE);
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
