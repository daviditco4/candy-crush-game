package game.backend.move;

import game.backend.Grid;

public class WrappedStripedMove extends Move {

	public WrappedStripedMove(Grid grid) {
		super(grid);
	}
	
	@Override
	public void removeElements() {
		for(int y = -1; y < 2; y++) {
			for(int x = 0; x < Grid.SIZE; x++) {
				if (y2 + y >= 0 && y2 + y < Grid.SIZE) {
					clearContent(y2 + y, x);
				}
			}
		}
		for(int x = -1; x < 2; x++) {
			for(int y = 0; y < Grid.SIZE; y++) {
				if (x2 + x >= 0 && x2 + x < Grid.SIZE) {
					clearContent(y, x2 + x);
				}
			}
		}
	}

}
