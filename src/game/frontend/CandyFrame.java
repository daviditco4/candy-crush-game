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
	private AppMenu appMenu;

	public CandyFrame() {
		appMenu = new AppMenu();
		getChildren().add(appMenu);
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);
		game.initGame();
		addFrameUpdateGameListenerToCurrentLevel();

		//Event handler para detectar clicks del mouse
		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			//Si el juego no esta en curso, el click no hace nada
			if(!game.isGameFinished()) {
				//Cuando lastPoint es nulo, el click marca la primera selección
				if (lastPoint == null) {
					lastPoint = translateCoords(event.getSceneX(), event.getSceneY() - appMenu.getHeight());
				} else {
					//Si lastPoint no es nulo, el click es la segunda selección
					Point2D newPoint = translateCoords(event.getSceneX(), event.getSceneY() - appMenu.getHeight());
					if (newPoint != null) {
						//Al realizar la segunda selección, se ejecuta un movimiento, luego se actualiza el panel de puntajes, y se resetea la primera selección
						game.tryMove((int) lastPoint.getX(), (int) lastPoint.getY(), (int) newPoint.getX(), (int) newPoint.getY());
						updateScorePanel();
						lastPoint = null;
					}
				}
				game.level().wasUpdated();
			}
		});

		//Este timer se ejecuta cada 1 segundo. Se utiliza para el nivel TimeLimit
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
		//Al actualizar el panel de puntajes, se chequea si el juego termino y si es asi se abre la ventana que pregunta si se desea jugar de nuevo
		if (game.isGameFinished() && !alertPoppedUp) {
			alertPoppedUp = true;
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle(game.playerWon()?"Has Ganado!":"Has Perdido");
			alert.setHeaderText("Jugar de nuevo?");
			alert.setContentText(game.getFinalMessageBody());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				game.resetLevel();
				alertPoppedUp = false;
			}
		}
		String message = game.getDisplayString();
		scorePanel.updateScore(message);
	}

	//Este metodo añade un GameListener al nivel actualmente cargado.
	//Dicho GameListener actualiza graficamente la tabla
	public void addFrameUpdateGameListenerToCurrentLevel(){
		GameListener listener;
		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(17);
				Duration frameTime = Duration.ZERO;
				for (int y = game.getSize() - 1; y >= 0; y--) {
					for (int x = game.getSize() - 1; x >= 0; x--) {
						int finalY = y;
						int finalX = x;
						Cell cell = game.get(x, y);
						Element element = cell.getContent();
						Color cellColor = cell.getColor();
						Image image = images.getImage(element);
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setColor(finalY, finalX, cellColor)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalY, finalX, null)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalY, finalX, image)));
						//Si el caramelo es de tipo CandyTimeBonus, se le añade un display grafico adicional con el numero del tiempo extra que otorga.
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
				//Se añade un marco en el caramelo seleccionado por el primer click
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

	//Se pasan las coordenadas de pixel-space a grid-space
	private Point2D translateCoords(double x, double y) {
		double xValue = x / CELL_SIZE;
		double yValue = y / CELL_SIZE;
		return (yValue >= 0 && yValue < game.getSize() && xValue >= 0 && xValue < game.getSize()) ? new Point2D(xValue, yValue) : null;
	}

}
