package game.backend.element;

import game.backend.CandyGame;

public class CandyTimeBonus extends Candy {

    private int timeBonus;

    public CandyTimeBonus() { }

    public CandyTimeBonus(CandyColor color) { setColor(color); }

    public CandyTimeBonus(CandyColor color, int timeBonus) {
        this.timeBonus = timeBonus;
        setColor(color);
    }

    @Override
    public void onDestroyed() {
        if (CandyGame.instance.level().firstMoveDone)
            CandyGame.instance.level().state().increaseTimer(timeBonus);
    }

    public int getTimeBonus() {
        return timeBonus;
    }

    @Override
    public String getKey() {
        return getColor().toString() + "-TIME-CANDY";
    }

}
