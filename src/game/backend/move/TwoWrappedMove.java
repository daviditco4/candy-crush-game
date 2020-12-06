package game.backend.move;

import game.backend.Grid;

public class TwoWrappedMove extends Move {
	
	public TwoWrappedMove(Grid grid) {
		super(grid);
	}
	
	@Override
	public void removeElements() {
		int currY, currX;
		if (y1 == y2) {
			if (x1 < x2) {
				currY = y1;
				currX = x1;
			} else {
				currY = y2;
				currX = x2;
			}
			clearContent(currY,currX-1);
			clearContent(currY, currX + 2);
			for(int n = -1; n < 3; n++) {
				clearContent(currY - 1, currX + n);
				clearContent(currY + 1, currX + n);
			}
		} else {
			if (y1 < y2) {
				currY = y1;
				currX = x1;
			} else {
				currY = y2;
				currX = x2;
			}
			clearContent(currY,currX-1);
			clearContent(currY,currX+2);
			for(int n = -1; n < 3; n++) {
				clearContent(currY - 1, currX + n);
				clearContent(currY + 1, currX + n);
			}
		}
	}

}
