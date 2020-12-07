package game.backend.move;

import game.backend.level.LevelBase;

public class TwoWrappedMove extends Move {
	
	public TwoWrappedMove(LevelBase levelBase) {
		super(levelBase);
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
			if (currX > 0) clearContent(currY,currX-1);
			if (currX < LevelBase.SIZE - 2) clearContent(currY, currX + 2);
			for(int n = -1; n < 3; n++) {
				if (currY > 0) {
					if (n == -1 && currX <= 0) break;
					if (n == 2 && currX >= LevelBase.SIZE - 2) break;
					clearContent(currY - 1, currX + n);
				}
				if (currY < LevelBase.SIZE - 1) {
					if (n == -1 && currX <= 0) break;
					if (n == 2 && currX >= LevelBase.SIZE - 2) break;
					clearContent(currY - 1, currX + n);
				}
			}
		} else {
			if (y1 < y2) {
				currY = y1;
				currX = x1;
			} else {
				currY = y2;
				currX = x2;
			}
			if (currY > 0) clearContent(currY-1, currX);
			if (currY < LevelBase.SIZE - 2) clearContent(currY+2,currX);
			for(int n = -1; n < 3; n++) {
				if (currX > 0) {
					if (n == -1 && currY <= 0) break;
					if (n == 2 && currY >= LevelBase.SIZE - 2) break;
					clearContent(currY + n, currX - 1);
				}
				if (currX < LevelBase.SIZE - 1) {
					if (n == -1 && currY <= 0) break;
					if (n == 2 && currY >= LevelBase.SIZE - 2) break;
					clearContent(currY + n, currX + 1);
				}
			}
		}
	}

}
