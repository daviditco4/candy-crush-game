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
		Element curr = levelBase.get(y,x);
		int acum = 0;
		for (Checkpoint cp: Checkpoint.values()) {
			int newY = y + cp.getY();
			int newX = x + cp.getX();
			if (newY >= 0 && newY < LevelBase.SIZE && newX >= 0 && newX < LevelBase.SIZE) {
				if (curr.equals(levelBase.get(newY, newX))) {
					acum += cp.getValue();
				}
			}
		}
		return acum;
	}
	
	public void removeFigure(int y, int x, Figure f) {
		CandyColor color = ((Candy) levelBase.get(y, x)).getColor();
		levelBase.clearContent(y, x);
		if (f.hasReplacement()) {
			levelBase.setContent(y, x, f.generateReplacement(color));
		}
		for (Point p: f.getPoints()) {
			levelBase.clearContent(y + p.y, x + p.x);
		}
	}
	
}
