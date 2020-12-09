package game.backend.level;

import game.backend.GameState;
import game.backend.cell.Cell;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Level2 extends LevelBase {

    private static Color cellColor = Color.SANDYBROWN;
    private static final int MAX_MOVES = 40;

    @Override
    public void initialize(){
        super.initialize();
        colorCenterCells();
    }

    @Override
    public String getDisplayMessage() {
        return "Celdas Pared: " + getRemainingCells() + "   Puntos: " + state().getScore() + "   Movimientos: " + stepsLeft() + "/" + MAX_MOVES;
    }

    @Override
    public String getVictoryMessage(){
        return "Enhorabuena! Ganaste en "+ state().getMoves() +" movimientos con " + state().getScore() + " puntos!";
    }

    @Override
    public String getLosingMessage(){
        return "Perdiste! Te faltaron " + getRemainingCells() + " celdas";
    }

    @Override
    protected GameState newState() {
        return new Level2State();
    }

    private int stepsLeft(){
        return MAX_MOVES - state().getMoves();
    }

    // Se pintan las 9 celdas centrales de color cellColor (En este caso, Color.SANDYBROWN)
    private void colorCenterCells(){
        // Como SIZE es entero, se aprovecha el resultado de la division entera para que
        // first = last - 3, y que la pared sea de 3x3 por cómo se recorren los nested for's
        int first = LevelBase.SIZE / 2 - 1;
        int last = LevelBase.SIZE / 2 + 1;
        for(int x = first; x <= last; x++){
            for(int y = first; y <= last; y++){
                Cell auxCell = getCell(x, y);
                auxCell.setColor(cellColor);
                ((Level2State)state()).addCell(auxCell);
            }
        }
    }

    public void clearCell(Cell cell){
        cell.setColor(null);
        ((Level2State)state()).removeCell(cell);
    }

    public int getRemainingCells(){
        return ((Level2State)state()).cellCount();
    }

    private class Level2State extends GameState {
        // Se usa un set de cells para que cada una de las 9 celdas
        // sean distintas por defecto por ser distintas instancias.
        // Además facilita obtener el tamaño restante de la pared, pues
        // solo se usa wallCells.size()
        private Set<Cell> wallCells;

        public Level2State() {
            this.wallCells = new HashSet<>();
        }

        @Override
        public boolean playerWon() {
            return getRemainingCells() == 0;
        }
        @Override
        public boolean playerLost(){
            return getMoves() >= MAX_MOVES;
        }

        public void removeCell(Cell cell) {wallCells.remove(cell);}

        public void addCell(Cell cell) {wallCells.add(cell);}

        public int cellCount() { return wallCells.size();}
    }
}
