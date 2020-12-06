package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	private int timer = 0;
	
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

	public abstract boolean gameOver();
	
	public abstract boolean playerWon();
}
