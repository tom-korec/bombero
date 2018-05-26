package tomcarter.bombero.game.logic;

public enum Direction {
    DOWN( 0, -1),
    LEFT( -1, 0),
    RIGHT( 1, 0),
    UP( 0, 1);

    private int x;
    private int y;

    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Direction opposite(Direction direction){
        switch (direction){
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            default:
                return Direction.LEFT;
        }
    }
}
