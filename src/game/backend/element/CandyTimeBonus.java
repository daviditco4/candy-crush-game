package game.backend.element;

import game.backend.CandyGame;

public class CandyTimeBonus extends Candy {

    private int timeBonus;

    public CandyTimeBonus() {
        this.timeBonus = 0;
    }

    public CandyTimeBonus(CandyColor color) {
        this.timeBonus = 0;
        setColor(color);
    }

    public CandyTimeBonus(CandyColor color, int timeBonus) {
        this.timeBonus = timeBonus;
        setColor(color);
    }

    @Override
    public void onDestroyed() {
        CandyGame.instance.level().state().increaseTimer(timeBonus);
    }

    public int getTimeBonus() {
        return timeBonus;
    }

    @Override
    public String getFullKey() {
        return getColor().toString() + "-TIME-CANDY";
    }

    @Override
    public String getKey() {
        return "TIME-CANDY";
    }

}
