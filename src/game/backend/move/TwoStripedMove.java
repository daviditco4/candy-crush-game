package game.backend.move;

import game.backend.Grid;

public class TwoStripedMove extends Move {

	public TwoStripedMove(Grid grid) {
		super(grid);
	}
	
	@Override
	public void removeElements() {
		for(int y = 0; y < Grid.SIZE; y++) {
			clearContent(y, x2);
		}
		for(int x = 0; x < Grid.SIZE; x++) {
			clearContent(y2, x);
		}
	}

}
