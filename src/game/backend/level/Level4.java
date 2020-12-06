package game.backend.level;

import game.backend.element.*;

public class Level4 extends Level0 {

    private static final float timeBonusCandyChance = 0.05f;


    @Override
    public Element generateCandy(CandyColor color){
        int bonus = 10;
        if (Math.random() < 0.5){
            bonus = 5;
        }
        if (Math.random() < 0.2){
            bonus = 15;
        }
        return (Math.random() < timeBonusCandyChance)? new CandyTimeBonus(color, bonus) : new Candy(color);
    }

}
