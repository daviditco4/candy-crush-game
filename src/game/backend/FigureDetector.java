package game.backend;

import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;

import java.awt.Point;

public class FigureDetector {
	
	private Grid grid;
	
	public FigureDetector(Grid grid) {
		this.grid = grid;
	}
	
	public Figure checkFigure(int i, int j) {
		int acum = readCheckpoints(i, j);
		if (acum > 0) {
			for(Figure f: Figure.values()) {
				if (f.matches(acum)) {
					return f;
				}
			}
		}
		return null;
	}
	
	private int readCheckpoints(int y, int x) {
		Element curr = grid.get(y,x);
		int acum = 0;
		for (Checkpoint cp: Checkpoint.values()) {
			int newY = y + cp.getY();
			int newX = x + cp.getX();
			if (newY >= 0 && newY < Grid.SIZE && newX >= 0 && newX < Grid.SIZE) {
				if (curr.equals(grid.get(newY, newX))) {
					acum += cp.getValue();
				}
			}
		}
		return acum;
	}
	
	public void removeFigure(int y, int x, Figure f) {
		CandyColor color = ((Candy)grid.get(y, x)).getColor();
		grid.clearContent(y, x);
		if (f.hasReplacement()) {
			grid.setContent(y, x, f.generateReplacement(color));
		}
		for (Point p: f.getPoints()) {
			grid.clearContent(y + p.y, x + p.x);
		}
	}
	
}
