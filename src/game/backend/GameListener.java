package game.backend;

import game.backend.element.Element;

public interface GameListener {

	// Cada vez que cambie de frame, este método es ejecutado en todos los "listeners"
	default void gridUpdated(){}

	// Este método se ejecuta por cada vez que algunos caramelos desaparecen por formar un patrón válido
	default void cellExplosion(Element e){}
	
}