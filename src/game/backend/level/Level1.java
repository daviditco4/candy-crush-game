package game.backend.level;

import game.backend.GameState;
import javafx.scene.paint.Color;

public class Level1 extends LevelBase {

    private static Color cellColor = Color.YELLOW;
    private static final int MAX_MOVES = 20;

    @Override
    public String getDisplayMessage() {
        return "Celdas Oro: " + getGoldCells() + "/" + LevelBase.SIZE * LevelBase.SIZE + "  Puntos: " + state().getScore() + "   Movimientos: " + stepsLeft() + "/" + MAX_MOVES;
    }

    @Override
    public String getVictoryMessage(){
        return "Enhorabuena! Ganaste en "+ state().getMoves() + " movimientos con " + state().getScore() + " puntos!";
    }

    @Override
    public String getLosingMessage(){
        return "Perdiste! Te faltaron " + getRemainingCells() + " celdas";
    }

    @Override
    protected GameState newState() {
        return new Level1State();
    }

    @Override
    public boolean tryMove(int x1, int y1, int x2, int y2) {
        if(super.tryMove(x1, y1, x2, y2)){
            if(y1 == y2){
                for(int x=0 ; x < SIZE ; x++){
                    if(getCell(x, y1).getColor() == null){
                        getCell(x, y1).setColor(cellColor);
                        ((Level1State)state()).increaseAcum();
                    }
                }
            } else {
                for(int y=0 ; y < SIZE ; y++){
                    if(getCell(x1, y).getColor() == null){
                        getCell(x1, y).setColor(cellColor);
                        ((Level1State)state()).increaseAcum();
                    }
                }
            }
            wasUpdated();
            return true;
        }
        return false;
    }

    public int getGoldCells(){
        return ((Level1State)state()).getAcum();
    }

    public int getRemainingCells(){
        return LevelBase.SIZE * LevelBase.SIZE - getGoldCells();
    }

    //Retorno la cantidad de pasos que quedan segun el nivel
    private int stepsLeft(){
        return MAX_MOVES - state().getMoves();
    }

    private class Level1State extends GameState {
        private int acum;
        public Level1State() {
            this.acum = 0;
        }
        @Override
        public boolean playerWon() {
            return getRemainingCells() == 0;
        }
        @Override
        public boolean playerLost(){
            return getMoves() >= MAX_MOVES;
        }

        public int getAcum() {return acum;}
        public void increaseAcum() {acum++;}
    }

}
