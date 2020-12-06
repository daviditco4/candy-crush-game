package game.backend.element;

import game.backend.move.Direction;

public abstract class Element {
	
	public abstract boolean isMovable();
	
	public abstract String getKey();
	
	public String getFullKey() {
		return getKey();
	}

	public boolean isSolid() {
		return true;
	}
	
	public Direction[] explode() {
		return null;
	}

	public void onDestroyed() {}

	public void onSpecialDestroyed(){}

	public long getScore() {
		return 0;
	}
	
}
