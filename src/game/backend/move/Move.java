package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.Element;

public abstract class Move {
	
	protected LevelBase levelBase;
	protected int x1, y1, x2, y2;
	
	public Move(LevelBase levelBase) {
		this.levelBase = levelBase;
	}
	
	public void setCoords(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean isValid() {
		if ( (y1 == y2 && Math.abs(x1 - x2) == 1) || (x1 == x2 && Math.abs(y1 - y2) == 1)) {
			return internalValidation();
		}
		return false;
	}
	
	protected boolean internalValidation() {
		return true;
	}
	
	protected Element get(int x, int y) {
		return levelBase.get(x, y);
	}

	protected void clearContent(int x, int y) {
		levelBase.clearContentSpecial(x, y);
	}
	
	protected void setContent(int x, int y, Element e){
		levelBase.setContent(x, y, e);
	}
	
	protected void wasUpdated(){
		levelBase.wasUpdated();
	}
	
	public abstract void removeElements();

}
