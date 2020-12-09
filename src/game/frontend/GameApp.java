package game.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {

	//Inicia la aplicación de JavaFX
	public static void main(String[] args) {
		launch(args);
	}

	public static CandyFrame frame;

	@Override
	public void start(Stage primaryStage) {
		frame = new CandyFrame();
		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
