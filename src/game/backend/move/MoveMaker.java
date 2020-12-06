package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.*;

import java.util.HashMap;
import java.util.Map;

public class MoveMaker {

	private Map<String, Move> map;
	private LevelBase levelBase;
	
	public MoveMaker(LevelBase levelBase) {
		this.levelBase = levelBase;
		initMap();
	}

	private void initMap(){
		map = new HashMap<>();
		map.put(new Candy().getKey() + new Candy().getKey(), new CandyMove(levelBase));
		map.put(new Candy().getKey() + new HorizontalStripedCandy().getKey(), new CandyMove(levelBase));
		map.put(new Candy().getKey() + new VerticalStripedCandy().getKey(), new CandyMove(levelBase));
		map.put(new Candy().getKey() + new WrappedCandy().getKey(), new CandyMove(levelBase));
		map.put(new Candy().getKey() + new Bomb().getKey(), new BombMove(levelBase));
		map.put(new Candy().getKey() + new CandyTimeBonus().getKey(), new CandyMove(levelBase));
	
		map.put(new HorizontalStripedCandy().getKey() + new Candy().getKey(), new CandyMove(levelBase));
		map.put(new HorizontalStripedCandy().getKey() + new HorizontalStripedCandy().getKey(), new TwoStripedMove(levelBase));
		map.put(new HorizontalStripedCandy().getKey() + new VerticalStripedCandy().getKey(), new TwoStripedMove(levelBase));
		map.put(new HorizontalStripedCandy().getKey() + new WrappedCandy().getKey(), new WrappedStripedMove(levelBase));
		map.put(new HorizontalStripedCandy().getKey() + new Bomb().getKey(), new BombStrippedMove(levelBase));
		map.put(new HorizontalStripedCandy().getKey() + new CandyTimeBonus().getKey(), new CandyMove(levelBase));

		map.put(new VerticalStripedCandy().getKey() + new Candy().getKey(), new CandyMove(levelBase));
		map.put(new VerticalStripedCandy().getKey() + new HorizontalStripedCandy().getKey(), new TwoStripedMove(levelBase));
		map.put(new VerticalStripedCandy().getKey() + new VerticalStripedCandy().getKey(), new TwoStripedMove(levelBase));
		map.put(new VerticalStripedCandy().getKey() + new WrappedCandy().getKey(), new WrappedStripedMove(levelBase));
		map.put(new VerticalStripedCandy().getKey() + new Bomb().getKey(), new BombStrippedMove(levelBase));
		map.put(new VerticalStripedCandy().getKey() + new CandyTimeBonus().getKey(), new CandyMove(levelBase));

		map.put(new WrappedCandy().getKey() + new Candy().getKey(), new CandyMove(levelBase));
		map.put(new WrappedCandy().getKey() + new HorizontalStripedCandy().getKey(), new WrappedStripedMove(levelBase));
		map.put(new WrappedCandy().getKey() + new VerticalStripedCandy().getKey(), new WrappedStripedMove(levelBase));
		map.put(new WrappedCandy().getKey() + new WrappedCandy().getKey(), new TwoWrappedMove(levelBase));
		map.put(new WrappedCandy().getKey() + new Bomb().getKey(), new BombWrappedMove(levelBase));
		map.put(new WrappedCandy().getKey() + new CandyTimeBonus().getKey(), new CandyMove(levelBase));

		map.put(new Bomb().getKey() + new Candy().getKey(), new BombMove(levelBase));
		map.put(new Bomb().getKey() + new HorizontalStripedCandy().getKey(), new BombStrippedMove(levelBase));
		map.put(new Bomb().getKey() + new VerticalStripedCandy().getKey(), new BombStrippedMove(levelBase));
		map.put(new Bomb().getKey() + new WrappedCandy().getKey(), new BombWrappedMove(levelBase));
		map.put(new Bomb().getKey() + new Bomb().getKey(), new TwoBombMove(levelBase));
		map.put(new Bomb().getKey() + new CandyTimeBonus().getKey(), new BombMove(levelBase));

		map.put(new CandyTimeBonus().getKey() + new Candy().getKey(), new CandyMove(levelBase));
		map.put(new CandyTimeBonus().getKey() + new HorizontalStripedCandy().getKey(), new CandyMove(levelBase));
		map.put(new CandyTimeBonus().getKey() + new VerticalStripedCandy().getKey(), new CandyMove(levelBase));
		map.put(new CandyTimeBonus().getKey() + new WrappedCandy().getKey(), new CandyMove(levelBase));
		map.put(new CandyTimeBonus().getKey() + new Bomb().getKey(), new BombMove(levelBase));
		map.put(new CandyTimeBonus().getKey() + new CandyTimeBonus().getKey(), new CandyMove(levelBase));
	}
	
	public Move getMove(int y1, int x1, int y2, int x2) {
		Move move = map.get(levelBase.get(y1, x1).getKey() + levelBase.get(y2, x2).getKey());
		move.setCoords(y1, x1, y2, x2);
		return move;
	}

}
