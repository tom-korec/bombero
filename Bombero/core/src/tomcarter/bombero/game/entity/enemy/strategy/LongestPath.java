package tomcarter.bombero.game.entity.enemy.strategy;

import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;

public class LongestPath extends Movement {
    public LongestPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    public Direction getLongestPathDirection() {
        Direction direction = Direction.LEFT;
        int last = getPathLength(-1, 0);
        int current = getPathLength(1,0);
        if (last < current){
            direction = Direction.RIGHT;
            last = current;
        }

        current = getPathLength(0, -1);
        if (last < current){
            direction = Direction.DOWN;
            last = current;
        }

        current = getPathLength(0, 1);
        if (last < current){
            direction = Direction.UP;
            last = current;
        }

        enemy.blockMovement(last - 0.1f);

        return direction;
    }
}