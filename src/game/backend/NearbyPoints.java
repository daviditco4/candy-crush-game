package game.backend;

public enum NearbyPoints {

	U(0, -1, 1),
	UU(0, -2, 2),
	D(0, 1, 4),
	DD(0, 2, 8),
	R(1, 0, 16),
	RR(2, 0, 32),
	L(-1, 0, 64),
	LL(-2, 0, 128);
	
	private int x;
	private int y;
	private int value;
	
	NearbyPoints(int x, int y, int value) {
		this.x = x;
		this.y = y;
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
