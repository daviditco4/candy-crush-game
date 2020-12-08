package game.backend.level;

import game.backend.GameState;

public class Level0 extends LevelBase {
	
	private static final int REQUIRED_SCORE = 5000;
	private static final int MAX_MOVES = 20;

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
		return new Level0State();
	}
	
	private static class Level0State extends GameState {
		@Override
		public boolean playerWon() {
			return getScore() > REQUIRED_SCORE;
		}

		@Override
		public boolean playerLost(){
			return getMoves() >= MAX_MOVES;
		}
	}

}
