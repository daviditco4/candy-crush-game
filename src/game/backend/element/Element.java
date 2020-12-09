package game.backend.element;

import game.backend.move.Direction;
//Clase abstracta elemente que es utilizada para todos los tipos de elementos
public abstract class Element {
	//metodo boolean si es movible segun el tipo de elemento
	public abstract boolean isMovable();
	//Metodo para la obtencion de imagen. ya que retornara la key del mapa imagen
	public abstract String getKey();
	//Si es solido no es vacio, por default no es vacio
	public boolean isSolid() {
		return true;
	}
	//Direccion de las explosiones
	public Direction[] explode() {
		return null;
	}
	//Metodo que verifica si el caramelo fue explotado, usado en candytimebonus para el agregado de tiempo
	public void onDestroyed() {}
	//Retorna el puntaje
	public long getScore() {
		return 0;
	}
	
}
