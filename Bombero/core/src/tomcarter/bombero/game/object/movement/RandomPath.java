package tomcarter.bombero.game.object.movement;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.logic.level.Level;

/**
 * Enemy movement - random direction
 */
public class RandomPath extends Movement {
    public RandomPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    /**
     * Chooses random nonzero-length direction
     * @param randomLength if true, length is also being randomized
     * @return random nonzero-length direction
     */
    public Direction getDirection(boolean randomLength) {
        Direction direction = getRandomDirection();
        int length = getPathLength(direction.getX(), direction.getY());
        while (length < 1){
            direction = getRandomDirection();
            length = getPathLength(direction.getX(), direction.getY());
        }
        if (length > 1 && randomLength){
            length = MathUtils.random(1, length);
        }

        enemy.blockMovement(length-0.1f);
        return direction;
    }
}
