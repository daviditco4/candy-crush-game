package game.backend.level;

import javafx.scene.paint.Color;

public class Level1 extends Level0 {

    @Override
    public boolean tryMove(int y1, int x1, int y2, int x2) {
        if(super.tryMove(y1, x1, y2, x2)){
            if(y1 == y2){
                for(int x=0 ; x < SIZE ; x++){
                    getCell(y1, x).setColor(Color.YELLOW);
                }
            } else {
                for(int y=0 ; y < SIZE ; y++){
                    getCell(y, x1).setColor(Color.YELLOW);
                }
            }
            wasUpdated();
            return true;
        }
        return false;
    }

}
