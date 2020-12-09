package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.level.Level0;
import game.backend.level.LevelBase;
import game.frontend.GameApp;

public class CandyGame implements GameListener {
	public static CandyGame instance = new CandyGame();

	private LevelBase level = new Level0();
	
	private CandyGame() {
	}
	
	public void initGame() {
		resetLevel();
		addGameListener(this);
	}

	public LevelBase level() {
		return level;
	}

	public void setLevel(LevelBase level){
		this.level = level;
		initGame();
		GameApp.frame.addGameListenerToCurrentLevel();
	}

	public void resetLevel(){
		level.initialize();
		level.firstMoveDone = false;
		level.wasUpdated();
	}

	public int getSize() {
		return LevelBase.SIZE;
	}
	
	public boolean tryMove(int x1, int y1, int x2, int y2){
		return level.tryMove(x1, y1, x2, y2);
	}
	
	public Cell get(int x, int y){
		return level.getCell(x, y);
	}
	
	public void addGameListener(GameListener listener) {
		level.addListener(listener);
	}

	public boolean isGameFinished(){
		return level.state().gameOver();
	}

	public String getDisplayString(){
		return level.getDisplayMessage();
	}
	
	public boolean playerWon() {
		return level.state().playerWon();
	}

	public String getFinalMessageBody(){
		if(playerWon()){
			return level.getVictoryMessage();
		}
		return level.getLosingMessage();
	}

	@Override
	public void cellExplosion(Element e) {
		level.state().addScore(e.getScore());
	}
}
