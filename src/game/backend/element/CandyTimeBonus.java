package game.backend.element;

import game.backend.CandyGame;

public class CandyTimeBonus extends Candy {
    //cant de segundos de bonus
    private int timeBonus;
    //Seteo de color
    public CandyTimeBonus(CandyColor color) { setColor(color); }

    public CandyTimeBonus(CandyColor color, int timeBonus) {
        this.timeBonus = timeBonus;
        setColor(color);
    }

    //Cuando hubo un intercambio valido, empieza el contador de tiempo
    @Override
    public void onDestroyed() {
        if (CandyGame.instance.level().firstMoveDone)
            CandyGame.instance.level().state().increaseTimer(timeBonus);
    }
    //Getter de timebonus
    public int getTimeBonus() {
        return timeBonus;
    }

    //Metodo para la imagen
    @Override
    public String getKey() {
        return getColor().toString() + "-TIME-CANDY";
    }

}
