package game.backend.level;

import game.backend.GameState;
import javafx.scene.paint.Color;

public class Level1 extends LevelBase {

    private int acum;
    private static final int MAX_MOVES = 20;

    @Override
    public String getDisplayString() {
        return "TODO: LEVEL 1 SCORES";
    }

    @Override
    protected GameState newState() {
        return new Level1State();
    }

    @Override
    public boolean tryMove(int y1, int x1, int y2, int x2) {
        if(super.tryMove(y1, x1, y2, x2)){
            if(y1 == y2){
                for(int x=0 ; x < SIZE ; x++){
                    if(getCell(y1, x).getColor() == null){
                        getCell(y1, x).setColor(Color.YELLOW);
                        acum++;
                    }
                }
            } else {
                for(int y=0 ; y < SIZE ; y++){
                    if(getCell(y, x1).getColor() == null){
                        getCell(y, x1).setColor(Color.YELLOW);
                        acum++;
                    }
                }
            }
            wasUpdated();
            return true;
        }
        return false;
    }

    public String CellsUnColored(){
        return String.format("cells remaining: %d", LevelBase.SIZE * LevelBase.SIZE - acum);
    }

    private static class Level1State extends GameState {

        public Level1State() {
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
