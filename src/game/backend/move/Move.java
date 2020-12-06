package game.backend.move;

import game.backend.Grid;
import game.backend.element.Element;

public abstract class Move {
	
	private Grid grid;
	protected int y1, x1, y2, x2;
	
	public Move(Grid grid) {
		this.grid = grid;
	}
	
	public void setCoords(int y1, int x1, int y2, int x2){
		this.y1 = y1;
		this.x1 = x1;
		this.y2 = y2;
		this.x2 = x2;
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
	
	protected Element get(int y, int x) {
		return grid.get(y, x);
	}
	
	protected void clearContent(int y, int x) {
		grid.clearContent(y, x);
	}
	
	protected void setContent(int y, int x, Element e){
		grid.setContent(y, x, e);
	}
	
	protected void wasUpdated(){
		grid.wasUpdated();
	}
	
	public abstract void removeElements();

}
