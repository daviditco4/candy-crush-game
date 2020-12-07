package game.backend.level;

import game.backend.cell.Cell;
import game.backend.element.Nothing;
import game.backend.move.Move;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Level2 extends Level1 {

    private int acum;
    private static int blastSize = 9;
    private static Color blastColor = Color.SANDYBROWN;
    private int first = LevelBase.SIZE/2 - 1;
    private int last = LevelBase.SIZE/2 + 1;
    private Set<Cell> wallCells;

    @Override
    public void initialize(){
        super.initialize();
        wallCells = new HashSet<>();
        colorCenterCells();
    }

    private void colorCenterCells(){
        for(int x=first ; x <= last ; x++){
            for(int y=first ; y <= last ; y++){
                Cell auxCell = getCell(y, x);
                auxCell.setColor(blastColor);
                wallCells.add(auxCell);
            }
        }
    }

    public void clearCell(Cell cell){
        cell.setColor(null);
        wallCells.remove(cell);
    }

    /*
    private void clearBlast(){
        for(int x=first ; x <= last ; x++){
            for(int y=first ; y <= last ; y++){
                if(getCell(y, x).getContent() instanceof Nothing){
                    clearCell(y, x);
                }
            }
        }
    }
    */

//    @Override
//    public boolean tryMove(int y1, int x1, int y2, int x2) {
//        Move move = getMoveMaker().getMove(y1, x1, y2, x2);
//        swapContent(y1, x1, y2, x2);
//        if (move.isValid()) {
//            if (!firstMoveDone) firstMoveDone = true;
//            move.removeElements();
//            clearBlast();
//            fallElements();
//            wasUpdated();
//            return true;
//        } else {
//            swapContent(y1, x1, y2, x2);
//            return false;
//        }
//    }

}
