package game.backend;

public abstract class GameState {
	
	private long score;
	private int moves;
	private int timer;
	
	public void addScore(long value) {
		this.score += value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}

	public void updateTimer() { if (timer>0) timer--;}

	public int getTimer() {return timer;}

	public void setTimer(int time) {timer = time;}

	public void increaseTimer (int time) {timer += time;}

	public boolean gameOver(){
		return playerWon() || playerLost();
	}
	
	public abstract boolean playerWon();

	public abstract boolean playerLost();
}
