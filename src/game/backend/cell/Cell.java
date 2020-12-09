package game.backend.cell;

import game.backend.CandyGame;
import game.backend.level.Level2;
import game.backend.level.LevelBase;
import game.backend.element.Element;
import game.backend.element.Nothing;
import game.backend.move.Direction;
import javafx.scene.paint.Color;

public class Cell {
	
	private LevelBase levelBase;
	private Cell[] around = new Cell[Direction.values().length];
	private Element content;
	// Color del cell (usado como color de fondo del candy)
	private Color color;
	
	public Cell(LevelBase levelBase) {
		this.levelBase = levelBase;
		this.content = new Nothing();
	}

	public void setColor(Color color){
		this.color = color;
	}

	public Color getColor(){
		return color;
	}
	
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	public boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}
	
	public boolean isMovable(){
		return content.isMovable();
	}
	
	public boolean isEmpty() {
		return !content.isSolid();
	}

	public Element getContent() {
		return content;
	}
	
	public void clearContent() {
		if (content.isMovable()) {
			content.onDestroyed();
			Direction[] explosionCascade = content.explode();
			levelBase.cellExplosion(content);
			this.content = new Nothing();
			if (explosionCascade != null) {
				onSpecialDestroyed();
				expandExplosion(explosionCascade);
			}
		}
	}
	
	private void expandExplosion(Direction[] explosion) {
		for(Direction d: explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}
	
	private void explode(Direction d) {
		onSpecialDestroyed();
		clearContent();
		if (this.around[d.ordinal()] != null)
			this.around[d.ordinal()].explode(d);
	}
	
	public Element getAndClearContent() {
		if (content.isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}

	public boolean fallUpperContent() {
		Cell up = around[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			if (this.hasFloor()) {
				levelBase.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		}
		return false;
	}

	public void onSpecialDestroyed(){
		if(CandyGame.instance.level() instanceof Level2){
			((Level2)CandyGame.instance.level()).clearCell(this);
		}
	}
	
	public void setContent(Element content) {
		this.content = content;
	}

}
