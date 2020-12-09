package game.frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

//El ScorePanel es el que aparece abajo con los datos de la partida
public class ScorePanel extends BorderPane {

	private Label scoreLabel;

	public ScorePanel() {
		setStyle("-fx-background-color: #5490ff");
		scoreLabel = new Label();
		scoreLabel.setAlignment(Pos.CENTER);
		scoreLabel.setStyle("-fx-font-size: 24");
		setCenter(scoreLabel);
	}
	
	public void updateScore(String text) {
		scoreLabel.setText(text);
	}

}