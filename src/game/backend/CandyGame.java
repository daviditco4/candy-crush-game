package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.level.Level0;
import game.backend.level.LevelBase;

public class CandyGame implements GameListener{
	
	private static Level0 level;
	private LevelBase levelBase;
	private GameState state;
	
	public CandyGame(Level0 level) {
		this.level = level;
	}
	
	public void initGame() {
		try {
			levelBase = level.getClass().newInstance();
		} catch(IllegalAccessException | InstantiationException e) {
			System.out.println("ERROR AL INICIAR");
		}
		state = levelBase.createState();
		levelBase.initialize();
		addGameListener(this);
	}

	public static Level0 level(){
		return level;
	}

	public int getSize() {
		return LevelBase.SIZE;
	}
	
	public boolean tryMove(int y1, int x1, int y2, int x2){
		return levelBase.tryMove(y1, x1, y2, x2);
	}
	
	public Cell get(int y, int x){
		return levelBase.getCell(y, x);
	}
	
	public void addGameListener(GameListener listener) {
		levelBase.addListener(listener);
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
		//
	}



}
