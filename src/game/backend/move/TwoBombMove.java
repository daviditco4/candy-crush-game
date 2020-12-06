package game.backend.move;

import game.backend.level.LevelBase;

public class TwoBombMove extends Move {
	
	public TwoBombMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public void removeElements() {
		for(int y = 0; y < LevelBase.SIZE; y++) {
			for(int x = 0; x < LevelBase.SIZE; x++) {
				clearContent(y,x);
			}
		}
	}

}
