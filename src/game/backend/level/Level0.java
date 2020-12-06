package game.backend.level;

import game.backend.GameState;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.element.Wall;

public class Level0 extends LevelBase {
	
	private static final int REQUIRED_SCORE = 5000;
	private static final int MAX_MOVES = 20;

	@Override
	protected GameState newState() {
		return new Level0State(REQUIRED_SCORE, MAX_MOVES);
	}

	@Override
	protected void fillCells() {
		Cell wallCell = new Cell(this);
		wallCell.setContent(new Wall());
		Cell candyGenCell = new CandyGeneratorCell(this);
		
		//corners
		g()[0][0].setAround(candyGenCell, g()[0][1], wallCell, g()[1][0]);
		g()[SIZE-1][0].setAround(candyGenCell, g()[SIZE-1][1], g()[SIZE-2][0], wallCell);
		g()[0][SIZE-1].setAround(g()[0][SIZE-2], wallCell, wallCell, g()[1][SIZE-1]);
		g()[SIZE-1][SIZE-1].setAround(g()[SIZE-1][SIZE-2], wallCell, g()[SIZE-2][SIZE-1], wallCell);

		//upper line cells
		for (int x = 1; x < SIZE-1; x++) {
			g()[x][0].setAround(candyGenCell,g()[x][1],g()[x-1][0],g()[x+1][0]);
		}
		//bottom line cells
		for (int x = 1; x < SIZE-1; x++) {
			g()[x][SIZE-1].setAround(g()[x][SIZE-2], wallCell, g()[x-1][SIZE-1],g()[x+1][SIZE-1]);
		}
		//left line cells
		for (int y = 1; y < SIZE-1; y++) {
			g()[0][y].setAround(g()[0][y-1],g()[0][y+1], wallCell,g()[1][y]);
		}
		//right line cells
		for (int y = 1; y < SIZE-1; y++) {
			g()[SIZE-1][y].setAround(g()[SIZE-1][y-1],g()[SIZE-1][y+1], g()[SIZE-2][y], wallCell);
		}		
		//central cells
		for (int x = 1; x < SIZE-1; x++) {
			for (int y = 1; y < SIZE-1; y++) {
				g()[x][y].setAround(g()[x][y-1],g()[x][y+1],g()[x-1][y],g()[x+1][y]);
			}
		}
	}
	
	@Override
	public boolean tryMove(int y1, int x1, int y2, int x2) {
		boolean ret;
		if (ret = super.tryMove(y1, x1, y2, x2)) {
			state().addMove();
			wasUpdated();
		}
		return ret;
	}

	public int getMaxMoves(){
		return MAX_MOVES;
	}
	
	private static class Level0State extends GameState {
		private long requiredScore;
		private long maxMoves;
		
		public Level0State(long requiredScore, int maxMoves) {
			this.requiredScore = requiredScore;
			this.maxMoves = maxMoves;
		}
		@Override
		public boolean gameOver() {
			return playerWon() || getMoves() >= maxMoves;
		}
		@Override
		public boolean playerWon() {
			return getScore() > requiredScore;
		}
	}

	public Element generateCandy(CandyColor color){
		return new Candy(color);
	}

}
