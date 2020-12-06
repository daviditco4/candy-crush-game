package game.frontend;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BoardPanel extends TilePane {

	private StackPane[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		this.cells = new StackPane[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new StackPane();
				getChildren().add(cells[i][j]);
			}
		}
	}

	public void setColor(int row, int column, Color color){
		Light.Distant spotLight = new Light.Distant();
		spotLight.setColor(Color.YELLOW);
		spotLight.setElevation(100);
		Lighting lighting = new Lighting(spotLight);
		cells[row][column].setEffect(lighting);
	}

	public void setImage(int row, int column, Image image) {
		cells[row][column].getChildren().add(new ImageView(image));
	}

	public void addNode(int row, int column, Node node){
		cells[row][column].getChildren().add(node);
	}

	/*
    Light.Distant spotLight = new Light.Distant();
    spotLight.setColor(Color.YELLOW);
    spotLight.setElevation(100);
    Lighting lighting = new Lighting(spotLight);
    cells[row][column].setEffect(lighting);
    */


}