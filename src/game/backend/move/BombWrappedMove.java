package game.backend.move;

import game.backend.Grid;
import game.backend.element.Bomb;
import game.backend.element.Candy;

public class BombWrappedMove extends Move {

	public BombWrappedMove(Grid grid) {
		super(grid);	
	}
	
	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(y1, x1) instanceof Bomb ? get(y2, x2) : get(y1, x1));
		clearContent(y1, x1);
		clearContent(y2, x2);
		for(int i = 0; i < Grid.SIZE; i++) {
			for(int j = 0; j < Grid.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					clearContent(i, j);
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if (y1 + i >= 0 && y1 + i < Grid.SIZE && x1 + j >= 0 && x1 + j < Grid.SIZE) {
					clearContent(y1 + i, x1 + j);
				}
			}
		}
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if (y2 + i >= 0 && y2 + i < Grid.SIZE && x2 + j >= 0 && x2 + j < Grid.SIZE) {
					clearContent(y2 + i, x2 + j);
				}
			}
		}
	}

}
