package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.Element;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;

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
		GameListener listener;
		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(17);
				Duration frameTime = Duration.ZERO;
				for (int y = game().getSize() - 1; y >= 0; y--) {
					for (int x = game().getSize() - 1; x >= 0; x--) {
						int finalI = y;
						int finalJ = x;
						Cell cell = CandyFrame.this.game.get(y, x);
						Element element = cell.getContent();
						Color cellColor = cell.getColor();
						Image image = images.getImage(element);
						if(cellColor != null){
							timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setColor(finalI, finalJ, cellColor)));
						}
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image)));
					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}
			@Override
			public void cellExplosion(Element e) {
				//
			}
		});

		listener.gridUpdated();

		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// If game is finished clicking on cells does nothing
			if(!game().isFinished()) {
				if (lastPoint == null) {
					lastPoint = translateCoords(event.getSceneX(), event.getSceneY());
					System.out.println("Get first = " + lastPoint);
				} else {
					Point2D newPoint = translateCoords(event.getSceneX(), event.getSceneY());
					if (newPoint != null) {
						System.out.println("Get second = " + newPoint);
						game().tryMove((int) lastPoint.getY(), (int) lastPoint.getX(), (int) newPoint.getY(), (int) newPoint.getX());
						String message = ((Long) game().getScore()).toString() + "                   " + game().stepsLeft() + " moves remaining";
						if (game().isFinished()) {
							if (game().playerWon()) {
								message = message + " Finished - Player Won!";
							} else {
								message = message + " Finished - Loser !";
							}
						}
						scorePanel.updateScore(message);
						lastPoint = null;
					}
				}
			}
		});

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
