package game.backend;

public enum Checkpoint {

	U(-1,0, 1),
	UU(-2,0, 2),
	D(1,0, 4),
	DD(2,0, 8),
	R(0,1, 16),
	RR(0,2, 32),
	L(0,-1, 64),
	LL(0,-2, 128);
	
	private int y;
	private int x;
	private int value;
	
	Checkpoint(int y, int x, int value) {
		this.y = y;
		this.x = x;
		this.value = value;
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getValue() {
		return value;
	}

}
