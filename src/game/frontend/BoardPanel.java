package game.frontend;

import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class BoardPanel extends TilePane {

	private StackPane[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		//Configuración estandar para el panel con los caramelos
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		cells = new StackPane[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new StackPane();
				getChildren().add(cells[i][j]);
			}
		}
	}

	public BoardPanel(final int rows, final int cellSize) { this(rows, rows, cellSize); }

	//Cambia el color de una celda en la tabla. Utilizado en niveles GoldenBoard y WallBlast
	public void setColor(int row, int column, Color color) {
		Light.Distant spotLight = new Light.Distant();
		if (color == null) {
			cells[row][column].setEffect(null);
			return;
		}
		spotLight.setColor(color);
		spotLight.setElevation(100);
		Lighting lighting = new Lighting(spotLight);
		cells[row][column].setEffect(lighting);
	}

	//Coloca una imagen en la tabla añadiendo el nodo ImageView
	public void setImage(int row, int column, Image image) {
		addNode(row, column, new ImageView(image));
	}

	//Añade un nodo cualquiera en la tabla, Utilizado para añadir los numeros a los Time Bonus Candy
	public void addNode(int row, int column, Node node){
		cells[row][column].getChildren().add(node);
	}


}