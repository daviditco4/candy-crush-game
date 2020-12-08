package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.CandyTimeBonus;
import game.backend.element.Element;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class CandyFrame extends VBox {

	private static final int CELL_SIZE = 65;

	private BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private ImageManager images;
	private Point2D lastPoint;
	private CandyGame game = CandyGame.instance;

	public CandyFrame() {
		getChildren().add(new AppMenu());
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);
		game.initGame();
		addGameListenerToCurrentLevel();

		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// If game is finished clicking on cells does nothing
			if(!game().isGameFinished()) {
				if (lastPoint == null) {
					lastPoint = translateCoords(event.getSceneX(), event.getSceneY());
					System.out.println("Get first = " + lastPoint);
				} else {
					Point2D newPoint = translateCoords(event.getSceneX(), event.getSceneY());
					if (newPoint != null) {
						System.out.println("Get second = " + newPoint);
						game().tryMove((int) lastPoint.getY(), (int) lastPoint.getX(), (int) newPoint.getY(), (int) newPoint.getX());
						updateScorePanel();
						lastPoint = null;
					}
				}
				CandyGame.instance.level().wasUpdated();
			}
		});

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new TimerTask() {
					@Override
					public void run() {
						game.level().updateFixedTime();
						updateScorePanel();
					}
				});
			}
		}, 0, 1000);
	}

	boolean alertPoppedUp = false;
	public void updateScorePanel(){
		if (game.isGameFinished() && !alertPoppedUp) {
			alertPoppedUp = true;
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle(game.playerWon()?"Has Ganado!":"Has Perdido");
			alert.setHeaderText("Jugar de nuevo?");
			alert.setContentText(game.getFinalMessageBody());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				CandyGame.instance.resetLevel();
				alertPoppedUp = false;
			}
		}
		String message = game.getDisplayString();
		scorePanel.updateScore(message);
	}

	public void addGameListenerToCurrentLevel(){
		GameListener listener;
		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(17);
				Duration frameTime = Duration.ZERO;
				for (int y = game().getSize() - 1; y >= 0; y--) {
					for (int x = game().getSize() - 1; x >= 0; x--) {
						int finalY = y;
						int finalX = x;
						Cell cell = CandyFrame.this.game.get(y, x);
						Element element = cell.getContent();
						Color cellColor = cell.getColor();
						Image image = images.getImage(element);
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setColor(finalY, finalX, cellColor)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalY, finalX, null)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalY, finalX, image)));
						if (element instanceof CandyTimeBonus){
							DropShadow dropShadow = new DropShadow();
							dropShadow.setRadius(3.0);
							dropShadow.setOffsetX(3.0);
							dropShadow.setOffsetY(3.0);
							dropShadow.setColor(Color.CYAN);
							Text text = new Text("+"+((CandyTimeBonus) element).getTimeBonus());
							text.setFont(Font.font("Impact", FontWeight.BOLD, 40));
							text.setFill(Color.BLACK);
							text.setEffect(dropShadow);
							timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.addNode(finalY, finalX, text)));
						}
					}
					frameTime = frameTime.add(frameGap);
				}
					timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> {
						if (lastPoint != null){
							boardPanel.setImage((int)lastPoint.getY(), (int)lastPoint.getX(), images.getImage("JELLY"));
						}
					}));
				timeLine.play();
			}
		});

		listener.gridUpdated();
	}

	private CandyGame game() {
		return game;
	}

	private Point2D translateCoords(double x, double y) {
		double i = y / CELL_SIZE;
		double j = x / CELL_SIZE;
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize()) ? new Point2D(j, i) : null;
	}

}
