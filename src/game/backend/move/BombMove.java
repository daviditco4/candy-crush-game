package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.Bomb;
import game.backend.element.Candy;

public class BombMove extends Move {
	
	public BombMove(LevelBase levelBase) {
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
	}

}
