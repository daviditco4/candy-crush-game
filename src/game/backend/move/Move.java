package game.backend.move;

import game.backend.level.LevelBase;
import game.backend.element.Element;

public abstract class Move {
	
	protected LevelBase levelBase;
	protected int x1, y1, x2, y2;
	
	public Move(LevelBase levelBase) {
		this.levelBase = levelBase;
	}
	
	public void setCoords(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	//Se chequea que un movimiento sea valido al ver que ambas posiciones esten al lado de la otra
	public boolean isValid() {
		if ( (y1 == y2 && Math.abs(x1 - x2) == 1) || (x1 == x2 && Math.abs(y1 - y2) == 1)) {
			return internalValidation();
		}
		return false;
	}

	//Permite que un move especifico utilize su propia validación
	protected boolean internalValidation() {
		return true;
	}

	//Retorna el elemento en las coordenadas x y
	protected Element get(int x, int y) {
		return levelBase.get(x, y);
	}

	//Se borra el elemento en las coordenadas x y
	protected void clearContent(int x, int y) {
		levelBase.clearContentSpecial(x, y);
	}

	//Se coloca un elemento en las coordenadas x y
	protected void setContent(int x, int y, Element e){
		levelBase.setContent(x, y, e);
	}

	//Se actualiza el nivel
	protected void wasUpdated(){
		levelBase.wasUpdated();
	}

	//Se borran los elementos, dependiendo de cada tipo de movimiento.
	//Esta es la clase que cada instancia de Move debe sobre-escribir para definir el funcionamiento del movimiento.
	public abstract void removeElements();

}
