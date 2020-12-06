package game.backend.move;

import game.backend.level.LevelBase;

public class WrappedStripedMove extends Move {

	public WrappedStripedMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public void removeElements() {
		for(int y = -1; y < 2; y++) {
			for(int x = 0; x < LevelBase.SIZE; x++) {
				if (y2 + y >= 0 && y2 + y < LevelBase.SIZE) {
					clearContent(y2 + y, x);
				}
			}
		}
		for(int x = -1; x < 2; x++) {
			for(int y = 0; y < LevelBase.SIZE; y++) {
				if (x2 + x >= 0 && x2 + x < LevelBase.SIZE) {
					clearContent(y, x2 + x);
				}
			}
		}
	}

}
