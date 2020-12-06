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
	public String getDisplayMessage() {
		return (state().getScore()) + "                   " + stepsLeft() + " moves remaining";
	}

	public String getVictoryMessage(){
		return "Buena esa capo";
	}

	public String getLosingMessage(){
		return "Alpiste perdiste no hay nadie peor que vos";
	}

	//Retorno la cantidad de pasos que quedan segun el nivel
	private int stepsLeft(){
		return MAX_MOVES - state().getMoves();
	}

	@Override
	protected GameState newState() {
		return new Level0State(REQUIRED_SCORE, MAX_MOVES);
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

}
