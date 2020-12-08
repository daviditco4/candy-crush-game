package game.backend.element;

import game.backend.move.Direction;

public class StripedCandy extends Candy {

    private Orientation orientation;
    public enum Orientation {
        HORIZONTAL("HORIZ", Direction.LEFT, Direction.RIGHT),
        VERTICAL("VERT", Direction.DOWN, Direction.UP);
        String key;
        Direction[] explosion;
        Orientation (String key, Direction explosion0, Direction explosion1){
            this.key = key;
            explosion = new Direction[]{explosion0,explosion1};
        }
    }

    public StripedCandy(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String getKey() {
        return orientation.key+"-STRIPED-" + super.getKey();
    }

    @Override
    public Direction[] explode() {
        return orientation.explosion;
    }

    @Override
    public long getScore() {
        return 80;
    }

}
