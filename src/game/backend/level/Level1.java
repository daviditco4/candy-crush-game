package game.backend.level;

import game.backend.GameState;
import javafx.scene.paint.Color;

public class Level1 extends LevelBase {

    private int acum;
    private static Color cellColor = Color.YELLOW;
    private static final int MAX_MOVES = 20;

    @Override
    public void initialize(){
        super.initialize();
        acum = 0;
    }

    @Override
    public String getDisplayMessage() {
        return "Celdas Restantes: " + getRemainingCells() + " / Movimientos Restantes: " + stepsLeft();
    }

    @Override
    public String getVictoryMessage(){
        return "Enhorabuena! Ganaste en "+ state().getMoves() + " movimientos!";
    }

    @Override
    public String getLosingMessage(){
        return "Perdiste! Te faltaron " + getRemainingCells() + " celdas";
    }

    @Override
    protected GameState newState() {
        return new Level1State(MAX_MOVES);
    }

    @Override
    public boolean tryMove(int y1, int x1, int y2, int x2) {
        if(super.tryMove(y1, x1, y2, x2)){
            if(y1 == y2){
                for(int x=0 ; x < SIZE ; x++){
                    if(getCell(y1, x).getColor() == null){
                        getCell(y1, x).setColor(cellColor);
                        acum++;
                    }
                }
            } else {
                for(int y=0 ; y < SIZE ; y++){
                    if(getCell(y, x1).getColor() == null){
                        getCell(y, x1).setColor(cellColor);
                        acum++;
                    }
                }
            }
            wasUpdated();
            return true;
        }
        return false;
    }

    public int getRemainingCells(){
        return LevelBase.SIZE * LevelBase.SIZE - acum;
    }

    //Retorno la cantidad de pasos que quedan segun el nivel
    private int stepsLeft(){
        return MAX_MOVES - state().getMoves();
    }

    private class Level1State extends GameState {
        private long maxMoves;

        public Level1State(int maxMoves) {
            this.maxMoves = maxMoves;
        }
        @Override
        public boolean playerWon() {
            return getRemainingCells() == 0;
        }
        @Override
        public boolean playerLost(){
            return getMoves() >= maxMoves;
        }
    }

}
