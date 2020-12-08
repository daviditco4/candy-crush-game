package game.backend.element;

import game.backend.move.Direction;

public abstract class Element {
	
	public abstract boolean isMovable();

	public abstract String getKey();

	public boolean isSolid() {
		return true;
	}
	
	public Direction[] explode() {
		return null;
	}

	public void onDestroyed() {}

	public long getScore() {
		return 0;
	}
	
}
