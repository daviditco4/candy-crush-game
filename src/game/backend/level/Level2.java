package game.backend.level;

import javafx.scene.paint.Color;

public class Level2 extends Level1 {

    private static int blastSize = 9;
    private static Color blastColor = Color.SANDYBROWN;

    @Override
    public void initialize(){
        super.initialize();
        colorCenterCells();
    }

    private void colorCenterCells(){
        int first = LevelBase.SIZE/2 - 1;
        int last = LevelBase.SIZE/2 + 1;
        for(int x=first ; x <= last ; x++){
            for(int y=first ; y <= last ; y++){
                getCell(y, x).setColor(blastColor);
            }
        }
    }

}
