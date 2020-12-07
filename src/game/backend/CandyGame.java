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
		// level.createState();
		level.initialize();
		addGameListener(this);
	}

	public LevelBase level() {
		return level;
	}

	public void setLevel(LevelBase level){
		this.level = level;
		resetLevel();
		GameApp.frame.addClickListenerToCurrentLevel();
	}

	public void resetLevel(){
		initGame();
		GameApp.frame.updateScorePanel();
		level.firstMoveDone = false;
		level.wasUpdated();
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
	
	public boolean isGameFinished() {
		return level.state().gameOver() || level.state().playerWon();
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
	
	@Override
	public void gridUpdated() {

	}
}
