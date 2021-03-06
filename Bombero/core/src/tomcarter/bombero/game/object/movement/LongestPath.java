package tomcarter.bombero.game.object.movement;

import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.logic.level.Level;

/**
 * Enemy movement specific class
 * Chooses the longest path available
 */
public class LongestPath extends Movement {
    public LongestPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    /**
     * @return direction of the longest path
     */
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
