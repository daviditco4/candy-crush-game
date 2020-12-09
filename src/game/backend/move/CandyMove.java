package game.backend.move;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.level.LevelBase;

//El movimiento default para cualquier combinación no especial
public class CandyMove extends Move {

	private Figure f1;
	private Figure f2;
	
	private FigureDetector detector;
	
	public CandyMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public boolean internalValidation() {
		this.detector = new FigureDetector(levelBase);
		f1 = detector.checkFigure(x1, y1);
		f2 = detector.checkFigure(x2, y2);
		return f1 != null || f2 != null;
	}	

	@Override
	public void removeElements() {
		if (f1 != null) {
			detector.removeFigure(x1, y1, f1);
		}
		if (f2 != null) {
			detector.removeFigure(x2, y2, f2);
		}
	}

}
