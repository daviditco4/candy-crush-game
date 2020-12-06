package game.backend.level;

import game.backend.Grid;
import javafx.scene.paint.Color;

public class Level1 extends Level0 {

    private int acum;

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
        return String.format("cells remaining: %d", Grid.SIZE * Grid.SIZE - acum);
    }


}
