package game.backend;

import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.level.LevelBase;

import java.awt.Point;

public class FigureDetector {
	
	private LevelBase levelBase;
	
	public FigureDetector(LevelBase levelBase) {
		this.levelBase = levelBase;
	}
	
	public Figure checkFigure(int x, int y) {
		int acum = readCheckpoints(x, y);
		if (acum > 0) {
			for(Figure f: Figure.values()) {
				if (f.matches(acum)) {
					return f;
				}
			}
		}
		return null;
	}
	
	private int readCheckpoints(int x, int y) {
		Element curr = levelBase.get(x, y);
		int acum = 0;
		for (Checkpoint cp: Checkpoint.values()) {
			int newY = y + cp.getY();
			int newX = x + cp.getX();
			if (newY >= 0 && newY < LevelBase.SIZE && newX >= 0 && newX < LevelBase.SIZE) {
				if (curr.equals(levelBase.get(newX, newY))) {
					acum += cp.getValue();
				}
			}
		}
		return acum;
	}
	
	public void removeFigure(int x, int y, Figure f) {
		CandyColor color = ((Candy) levelBase.get(x, y)).getColor();
		levelBase.clearContent(x, y);
		if (f.hasReplacement()) {
			levelBase.setContent(x, y, f.generateReplacement(color));
		}
		for (Point p: f.getPoints()) {
			levelBase.clearContent(x + p.x, y + p.y);
		}
	}
	
}
