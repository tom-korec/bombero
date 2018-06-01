package tomcarter.bombero.game.entity.enemy.strategy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;

public class RandomPath extends Movement {
    public RandomPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    public Direction getFullPathDirection(boolean randomLength) {
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
