package game.backend.level;

import game.backend.GameState;
import game.backend.cell.Cell;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Level2 extends LevelBase {

    private static Color cellColor = Color.SANDYBROWN;
    private Set<Cell> wallCells = new HashSet<>();
    private static final int MAX_MOVES = 40;

    @Override
    public void initialize(){
        super.initialize();
        colorCenterCells();
    }

    @Override
    public String getDisplayMessage() {
        return (state().getScore()) + " " + stepsLeft() + " remaining moves\t" + getRemainingCells() + " cells remaining";
    }

    public String getVictoryMessage(){
        return "Buena esa capo";
    }
    public String getLosingMessage(){
        return "Alpiste perdiste no hay nadie peor que vos";
    }

    @Override
    protected GameState newState() {
        return new Level2State(MAX_MOVES);
    }

    private int stepsLeft(){
        return MAX_MOVES - state().getMoves();
    }

    private void colorCenterCells(){
        int first = LevelBase.SIZE / 2 - 1;
        int last = LevelBase.SIZE / 2 + 1;
        for(int x = first; x <= last; x++){
            for(int y = first; y <= last; y++){
                Cell auxCell = getCell(y, x);
                auxCell.setColor(cellColor);
                wallCells.add(auxCell);
            }
        }
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

    public void clearCell(Cell cell){
        cell.setColor(null);
        wallCells.remove(cell);
    }

    public int getRemainingCells(){
        return wallCells.size();
    }

    private class Level2State extends GameState {
        private long maxMoves;

        public Level2State(int maxMoves) {
            this.maxMoves = maxMoves;
        }
        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() >= maxMoves;
        }
        @Override
        public boolean playerWon() {
            return getRemainingCells() == 0;
        }
    }
}
