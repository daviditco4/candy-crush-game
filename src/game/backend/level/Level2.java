package game.backend.level;

import game.backend.Grid;

import javafx.scene.paint.Color;
import java.lang.reflect.Array;

public class Level2 extends Level1 {

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        if(super.tryMove(i1, j1, i2, j2)){
            if(i1 == i2){
                for(int j=0 ; j < SIZE ; j++){
                    getCell(i1, j).setColor(Color.YELLOW);
                }
            } else {
                for(int i=0 ; i < SIZE ; i++){
                    getCell(i, j1).setColor(Color.YELLOW);
                }
            }
            wasUpdated();
            return true;
        }
        return false;
    }

}
