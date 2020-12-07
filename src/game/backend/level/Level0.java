package game.backend.level;

import game.backend.GameState;

public class Level0 extends LevelBase {
	
	private static final int REQUIRED_SCORE = 5000;
	private static final int MAX_MOVES = 5;

	@Override
	public String getDisplayMessage() {
		return "Puntaje: " + state().getScore() + " / Movimientos Restantes: " + stepsLeft();
	}

	@Override
	public String getVictoryMessage(){
		return "Enorabuena! Ganaste en "+ state().getMoves() +" movimientos!";
	}

	@Override
	public String getLosingMessage(){
		return "Perdiste! Puntaje final: " + state().getScore()+ ". Te faltaron " + (REQUIRED_SCORE - state().getScore());
	}

	//Retorno la cantidad de pasos que quedan segun el nivel
	private int stepsLeft(){
		return MAX_MOVES - state().getMoves();
	}

	@Override
	protected GameState newState() {
		return new Level0State(REQUIRED_SCORE, MAX_MOVES);
	}
	
	private static class Level0State extends GameState {
		private long requiredScore;
		private int maxMoves;
		
		public Level0State(long requiredScore, int maxMoves) {
			this.requiredScore = requiredScore;
			this.maxMoves = maxMoves;
		}

		@Override
		public boolean playerWon() {
			return getScore() > requiredScore;
		}

		@Override
		public boolean playerLost(){
			return getMoves() >= maxMoves;
		}
	}

}
