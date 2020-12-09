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
		specialMoveFlagMap.put(getKey(StripedCandy.class, StripedCandy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				for(int y = 0; y < LevelBase.SIZE; y++) {
					clearContent(x2, y);
				}
				for(int x = 0; x < LevelBase.SIZE; x++) {
					clearContent(x, y2);
				}
			}
		});
		specialMoveFlagMap.put(getKey(StripedCandy.class, WrappedCandy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				for(int y = -1; y < 2; y++) {
					for(int x = 0; x < LevelBase.SIZE; x++) {
						if (y2 + y >= 0 && y2 + y < LevelBase.SIZE) {
							clearContent(x, y2 + y);
						}
					}
				}
				for(int x = -1; x < 2; x++) {
					for(int y = 0; y < LevelBase.SIZE; y++) {
						if (x2 + x >= 0 && x2 + x < LevelBase.SIZE) {
							clearContent(x2 + x, y);
						}
					}
				}
			}
		});
		specialMoveFlagMap.put(getKey(WrappedCandy.class, WrappedCandy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				int currY, currX;
				if (y1 == y2) {
					if (x1 < x2) {
						currY = y1;
						currX = x1;
					} else {
						currY = y2;
						currX = x2;
					}
					if (currX > 0) clearContent(currX-1, currY);
					if (currX < LevelBase.SIZE - 2) clearContent(currX + 2, currY);
					for(int n = -1; n < 3; n++) {
						if (currY > 0) {
							if (n == -1 && currX <= 0) break;
							if (n == 2 && currX >= LevelBase.SIZE - 2) break;
							clearContent(currX + n, currY - 1);
						}
						if (currY < LevelBase.SIZE - 1) {
							if (n == -1 && currX <= 0) break;
							if (n == 2 && currX >= LevelBase.SIZE - 2) break;
							clearContent(currX + n, currY - 1);
						}
					}
				} else {
					if (y1 < y2) {
						currY = y1;
						currX = x1;
					} else {
						currY = y2;
						currX = x2;
					}
					if (currY > 0) clearContent(currX, currY-1);
					if (currY < LevelBase.SIZE - 2) clearContent(currX, currY+2);
					for(int n = -1; n < 3; n++) {
						if (currX > 0) {
							if (n == -1 && currY <= 0) break;
							if (n == 2 && currY >= LevelBase.SIZE - 2) break;
							clearContent(currX - 1, currY + n);
						}
						if (currX < LevelBase.SIZE - 1) {
							if (n == -1 && currY <= 0) break;
							if (n == 2 && currY >= LevelBase.SIZE - 2) break;
							clearContent(currX + 1, currY + n);
						}
					}
				}
			}
		});
		specialMoveFlagMap.put(getKey(Bomb.class, Candy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				Candy candy = (Candy) (get(x1, y1) instanceof Bomb ? get(x2, y2) : get(x1, y1));
				clearContent(x1, y1);
				clearContent(x2, y2);
				for(int i = 0; i < LevelBase.SIZE; i++) {
					for(int j = 0; j < LevelBase.SIZE; j++) {
						if (candy.equals(get(j, i))) {
							clearContent(j, i);
						}
					}
				}
			}
		});
		specialMoveFlagMap.put(getKey(Bomb.class, StripedCandy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				Candy candy = (Candy) (get(x1, y1) instanceof Bomb ? get(x2, y2) : get(x1, y1));
				CandyColor color = candy.getColor();
				for(int i = 0; i < LevelBase.SIZE; i++) {
					for(int j = 0; j < LevelBase.SIZE; j++) {
						if (candy.equals(get(j, i))) {
							setContent(j, i, createStriped(color));
						}
					}
				}
				wasUpdated();
				for(int i = 0; i < LevelBase.SIZE; i++) {
					for(int j = 0; j < LevelBase.SIZE; j++) {
						if (candy.equals(get(j, i))) {
							clearContent(j, i);
						}
					}
				}
			}

			private Candy createStriped(CandyColor color) {
				Candy c;
				if ((int)(Math.random() * 2) == 0) {
					c = new HorizontalStripedCandy();
				} else {
					c = new VerticalStripedCandy();
				}
				c.setColor(color);
				return c;
			}
		});
		specialMoveFlagMap.put(getKey(Bomb.class, WrappedCandy.class), new Move(levelBase){
			@Override
			public void removeElements() {
				Candy candy = (Candy) (get(x1, y1) instanceof Bomb ? get(x2, y2) : get(x1, y1));
				clearContent(x1, y1);
				clearContent(x2, y2);
				for(int i = 0; i < LevelBase.SIZE; i++) {
					for(int j = 0; j < LevelBase.SIZE; j++) {
						if (candy.equals(get(j, i))) {
							clearContent(j, i);
						}
					}
				}
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						if (y1 + i >= 0 && y1 + i < LevelBase.SIZE && x1 + j >= 0 && x1 + j < LevelBase.SIZE) {
							clearContent(x1 + j, y1 + i);
						}
					}
				}
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						if (y2 + i >= 0 && y2 + i < LevelBase.SIZE && x2 + j >= 0 && x2 + j < LevelBase.SIZE) {
							clearContent(x2 + j, y2 + i);
						}
					}
				}
			}
		});
		specialMoveFlagMap.put(getKey(Bomb.class, Bomb.class), new Move(levelBase){
			@Override
			public void removeElements() {
				for(int y = 0; y < LevelBase.SIZE; y++) {
					for(int x = 0; x < LevelBase.SIZE; x++) {
						clearContent(x, y);
					}
				}
			}
		});

	}
	
	public Move getMove(int x1, int y1, int x2, int y2) {
		Element el1 = levelBase.get(x1, y1);
		Element el2 = levelBase.get(x2, y2);
		int key = getKey(el1, el2);
		Move move;
		if (specialMoveFlagMap.containsKey(key)){
			move = specialMoveFlagMap.get(key);
		} else {
			move = defaultMove;
		}
		move.setCoords(x1, y1, x2, y2);
		return move;
	}

}
