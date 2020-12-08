package game.backend.element;

import game.backend.move.Direction;

import java.util.Arrays;

public class WrappedCandy extends Candy {
	
	private Direction[] explosion;
	
	public WrappedCandy() {
		// Como WrappedCandy explota en todas las direcciones, tomo directamente todos los values() de Direction
		explosion = Direction.values();
	}
	
	@Override
	public String getKey() {
		return "WRAPPED-" + super.getKey();
	}
	
	@Override
	public Direction[] explode() {
		return explosion;
	}
	
	@Override
	public long getScore() {
		return 60;
	}

}
