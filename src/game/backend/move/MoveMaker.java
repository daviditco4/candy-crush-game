package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.*;

import java.util.HashMap;
import java.util.Map;

public class MoveMaker {

	private Map<Class<? extends Element>, Integer> elementIDMap;
	private Map<Integer, Move> specialMoveFlagMap;
	private static Move defaultMove;
	private LevelBase levelBase;
	
	public MoveMaker(LevelBase levelBase) {
		this.levelBase = levelBase;
		initMap();
	}

	//Commutative pairing function found in http://benpaulthurstonblog.blogspot.com/2015/12/possible-commutative-pairing-function.html
	private int getKey (Class<? extends Element> candy1, Class<? extends Element> candy2){
		int a = elementIDMap.get(elementIDMap.containsKey(candy1)?candy1:candy1.getSuperclass());
		int b = elementIDMap.get(elementIDMap.containsKey(candy2)?candy2:candy2.getSuperclass());
		return (int)Math.pow(a, 2) + (int)Math.pow(b, 2) + a*b + a + b;
	}
	private int getKey (Element candy1, Element candy2){
		return getKey(candy1.getClass(), candy2.getClass());
	}

	@SafeVarargs
	private final void addCandiesToMap(Class<? extends Element> ... elements){
		int index = 1;
		for (Class<? extends Element> element : elements){
			elementIDMap.put(element, index++);
		}
	}

	private void initMap(){
		elementIDMap = new HashMap<>();
		//Registramos los tipos distintos de caramelos
		addCandiesToMap(Candy.class, StripedCandy.class, WrappedCandy.class, Bomb.class);
		//Se añade el CandyTimeBonus aparte con el mismo ID que Candy ya que los movimientos son los mismos
		elementIDMap.put(CandyTimeBonus.class, elementIDMap.get(Candy.class));

		defaultMove = new CandyMove(levelBase);
		specialMoveFlagMap = new HashMap<>();
		specialMoveFlagMap.put(getKey(StripedCandy.class, StripedCandy.class), new TwoStripedMove(levelBase));
		specialMoveFlagMap.put(getKey(StripedCandy.class, WrappedCandy.class), new WrappedStripedMove(levelBase));
		specialMoveFlagMap.put(getKey(WrappedCandy.class, WrappedCandy.class), new TwoWrappedMove(levelBase));
		specialMoveFlagMap.put(getKey(Bomb.class, Candy.class), new BombMove(levelBase));
		specialMoveFlagMap.put(getKey(Bomb.class, StripedCandy.class), new BombStrippedMove(levelBase));
		specialMoveFlagMap.put(getKey(Bomb.class, WrappedCandy.class), new BombWrappedMove(levelBase));
		specialMoveFlagMap.put(getKey(Bomb.class, Bomb.class), new TwoBombMove(levelBase));

	}
	
	public Move getMove(int y1, int x1, int y2, int x2) {
		Element el1 = levelBase.get(y1, x1);
		Element el2 = levelBase.get(y2, x2);
		int key = getKey(el1, el2);
		Move move;
		if (specialMoveFlagMap.containsKey(key)){
			move = specialMoveFlagMap.get(key);
		} else {
			move = defaultMove;
		}
		move.setCoords(y1, x1, y2, x2);
		return move;
	}

}
