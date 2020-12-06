package game.backend.move;

import game.backend.Grid;

public class TwoBombMove extends Move {
	
	public TwoBombMove(Grid grid) {
		super(grid);
	}
	
	@Override
	public void removeElements() {
		for(int y = 0; y < Grid.SIZE; y++) {
			for(int x = 0; x < Grid.SIZE; x++) {
				clearContent(y,x);
			}
		}
	}

}
