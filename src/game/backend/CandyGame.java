package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.level.Level0;
import game.backend.level.Level1;
import game.backend.level.LevelBase;

public class CandyGame implements GameListener {
	public static CandyGame instance = new CandyGame();

	private LevelBase level = new Level1();
	private GameState state;
	
	private CandyGame() {
	}
	
	public void initGame() {
		state = level.createState();
		level.initialize();
		addGameListener(this);
	}

	public LevelBase level() {
		return level;
	}

	public int getSize() {
		return LevelBase.SIZE;
	}
	
	public boolean tryMove(int y1, int x1, int y2, int x2){
		return level.tryMove(y1, x1, y2, x2);
	}
	
	public Cell get(int y, int x){
		return level.getCell(y, x);
	}
	
	public void addGameListener(GameListener listener) {
		level.addListener(listener);
	}
	
	public long getScore() {
		return state.getScore();
	}
	
	public boolean isFinished() {
		return state.gameOver();
	}

	//Retorno la cantidad de pasos que quedan segun el nivel
	public int stepsLeft(){
		return level.getMaxMoves() - state.getMoves();
	}
	
	public boolean playerWon() {
		return state.playerWon();
	}
	
	@Override
	public void cellExplosion(Element e) {
		state.addScore(e.getScore());
	}
	
	@Override
	public void gridUpdated() {

	}
}
