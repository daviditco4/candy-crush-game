package game.backend.level;

import game.backend.CandyGame;
import game.backend.GameState;
import game.backend.element.*;

public class Level4 extends LevelBase {

    private static final float TIME_BONUS_CANDY_CHANCE = 0.05f;
    private static final int STARTING_TIME = 60;
    private static final int REQUIRED_SCORE = 10000;

    @Override
    public String getDisplayMessage() {
        return "Puntos: " + state().getScore() + "/" + REQUIRED_SCORE + "   Tiempo Restante: "+ state().getTimer()+"s";
    }

    @Override
    public String getVictoryMessage(){
        return "Enorabuena! Ganaste en "+((Level4State)state()).getPassedTime()+" segundos!";
    }

    @Override
    public String getLosingMessage(){
        return "Perdiste! Puntaje final: " + state().getScore()+ ". Te faltaron " + (REQUIRED_SCORE - state().getScore());
    }

    @Override
    protected GameState newState() {
        return new Level4State(STARTING_TIME);
    }

    @Override
    public void updateFixedTime() {
        if (firstMoveDone && !CandyGame.instance.isGameFinished()){
            state().updateTimer();
        }
    }

    @Override
    public Element generateCandy(CandyColor color){
        int bonus = 5;
        if (Math.random() < 0.5){
            bonus = 2;
        }
        if (Math.random() < 0.1){
            bonus = 10;
        }
        return (Math.random() < TIME_BONUS_CANDY_CHANCE)? new CandyTimeBonus(color, bonus) : new Candy(color);
    }

    private static class Level4State extends GameState {
        // Se usa un passedTime aparte del timer propio de GameState para facilitar
        // el poder informar del tiempo pasado desde que se inició el nivel
        private int passedTime;
        public Level4State(int startingTime) {
            setTimer(startingTime);
        }
        @Override
        public void updateTimer() {
            super.updateTimer();
            passedTime++;
        }
        public int getPassedTime (){
            return passedTime;
        }
        @Override
        public boolean playerWon() {
            return getScore() >= REQUIRED_SCORE;
        }
        @Override
        public boolean playerLost(){
            return getTimer() <= 0;
        }

    }

}
