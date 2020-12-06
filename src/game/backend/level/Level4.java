package game.backend.level;

import game.backend.GameState;
import game.backend.element.*;

public class Level4 extends LevelBase {

    private static final float timeBonusCandyChance = 0.05f;

    @Override
    public String getDisplayString() {
        return "TODO: LEVEL 4 SCORES";
    }

    @Override
    protected GameState newState() {
        return new Level4State();
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

        public Level4State() {
        }
        @Override
        public boolean gameOver() {
            return false;
        }
        @Override
        public boolean playerWon() {
            return false;
        }
    }

}
