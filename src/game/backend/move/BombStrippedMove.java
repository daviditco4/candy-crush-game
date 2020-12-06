package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.Bomb;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.HorizontalStripedCandy;
import game.backend.element.VerticalStripedCandy;

public class BombStrippedMove extends Move {

	public BombStrippedMove(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(y1, x1) instanceof Bomb ? get(y2, x2) : get(y1, x1));
		CandyColor color = candy.getColor();
		for(int i = 0; i < LevelBase.SIZE; i++) {
			for(int j = 0; j < LevelBase.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					setContent(i, j, createStriped(color));
				}
			}
		}
		wasUpdated();
		for(int i = 0; i < LevelBase.SIZE; i++) {
			for(int j = 0; j < LevelBase.SIZE; j++) {
				if (candy.equals(get(i, j))) {
					clearContent(i, j);
				}
			}
		}
	}
	
	private Candy createStriped(CandyColor color) {
		Candy c;
		if ((int)(Math.random() * 2) == 0) {
			c = new HorizontalStripedCandy();
		} else {
			c = new VerticalStripedCandy();
		}
		c.setColor(color);
		return c;
	}

}
