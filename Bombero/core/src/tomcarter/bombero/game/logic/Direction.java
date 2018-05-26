package tomcarter.bombero.game.logic;

public enum Direction {
    DOWN( 0, -1) {
        @Override
        public Direction opposite() {
            return UP;
        }

        @Override
        public Direction nextClockwise() {
            return LEFT;
        }

        @Override
        public Direction nextAntiClockwise() {
            return RIGHT;
        }
    },

    LEFT( -1, 0) {
        @Override
        public Direction opposite() {
            return RIGHT;
        }

        @Override
        public Direction nextClockwise() {
            return UP;
        }

        @Override
        public Direction nextAntiClockwise() {
            return DOWN;
        }
    },

    RIGHT( 1, 0) {
        @Override
        public Direction opposite() {
            return LEFT;
        }

        @Override
        public Direction nextClockwise() {
            return DOWN;
        }

        @Override
        public Direction nextAntiClockwise() {
            return UP;
        }
    },

    UP( 0, 1) {
        @Override
        public Direction opposite() {
            return DOWN;
        }

        @Override
        public Direction nextClockwise() {
            return RIGHT;
        }

        @Override
        public Direction nextAntiClockwise() {
            return LEFT;
        }
    };

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

    public abstract Direction opposite();

    public abstract Direction nextClockwise();

    public abstract Direction nextAntiClockwise();
}
