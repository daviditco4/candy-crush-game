package game.backend.move;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.level.LevelBase;

public class CandyMove extends Move {

	private Figure f1;
	private Figure f2;
	
	private FigureDetector detector;
	private LevelBase levelBase;
	
	public CandyMove(LevelBase levelBase) {
		super(levelBase);
		this.levelBase = levelBase;
	}
	
	@Override
	public boolean internalValidation() {
		this.detector = new FigureDetector(levelBase);
		f1 = detector.checkFigure(y1, x1);
		f2 = detector.checkFigure(y2, x2);
		return f1 != null || f2 != null;
	}	

	@Override
	public void removeElements() {
		if (f1 != null) {
			detector.removeFigure(y1, x1, f1);
		}
		if (f2 != null) {
			detector.removeFigure(y2, x2, f2);
		}
	}

}
