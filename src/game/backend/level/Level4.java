package game.backend.level;

import game.backend.GameState;
import game.backend.element.*;

public class Level4 extends LevelBase {

    private static final float timeBonusCandyChance = 0.05f;
    private static final int startingTime = 60;

    @Override
    public String getDisplayString() {
        return "Time left: "+ state().getTimer();
    }

    public String getVictoryMessage(){
        return "Buena esa capo";
    }
    public String getLoosingMessage(){
        return "Alpiste perdiste no hay nadie peor que vos";
    }

    @Override
    protected GameState newState() {
        return new Level4State(startingTime);
    }

    @Override
    public boolean tryMove(int y1, int x1, int y2, int x2) {
        boolean ret;
        if (ret = super.tryMove(y1, x1, y2, x2)) {
            state().addMove();
            wasUpdated();
        }
        return ret;
    }

    @Override
    public void updateFixedTime() {
        state().updateTimer();
    }

    @Override
    public Element generateCandy(CandyColor color){
        int bonus = 10;
        if (Math.random() < 0.5){
            bonus = 5;
        }
        if (Math.random() < 0.2){
            bonus = 15;
        }
        return (Math.random() < timeBonusCandyChance)? new CandyTimeBonus(color, bonus) : new Candy(color);
    }

    private static class Level4State extends GameState {

        public Level4State(int startingTime) {
            setTimer(startingTime);
        }
        @Override
        public boolean gameOver() {
            return getTimer() <= 0;
        }
        @Override
        public boolean playerWon() {
            return false;
        }
    }

}
