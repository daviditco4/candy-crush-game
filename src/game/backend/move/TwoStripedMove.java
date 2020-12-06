package game.backend.move;

import game.backend.level.LevelBase;

public class TwoStripedMove extends Move {

	public TwoStripedMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public void removeElements() {
		for(int y = 0; y < LevelBase.SIZE; y++) {
			clearContent(y, x2);
		}
		for(int x = 0; x < LevelBase.SIZE; x++) {
			clearContent(y2, x);
		}
	}

}
