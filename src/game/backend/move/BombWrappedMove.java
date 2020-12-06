package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.Bomb;
import game.backend.element.Candy;

public class BombWrappedMove extends Move {

	public BombWrappedMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(y1, x1) instanceof Bomb ? get(y2, x2) : get(y1, x1));
		clearContent(y1, x1);
		clearContent(y2, x2);
		for(int i = 0; i < LevelBase.SIZE; i++) {
			for(int j = 0; j < LevelBase.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					clearContent(i, j);
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if (y1 + i >= 0 && y1 + i < LevelBase.SIZE && x1 + j >= 0 && x1 + j < LevelBase.SIZE) {
					clearContent(y1 + i, x1 + j);
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if (y2 + i >= 0 && y2 + i < LevelBase.SIZE && x2 + j >= 0 && x2 + j < LevelBase.SIZE) {
					clearContent(y2 + i, x2 + j);
				}
			}
		}
	}

}
