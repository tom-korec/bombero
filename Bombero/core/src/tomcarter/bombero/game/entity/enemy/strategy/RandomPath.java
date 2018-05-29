package tomcarter.bombero.game.entity.enemy.strategy;

import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;

public class RandomPath extends Movement {
    public RandomPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    @Override
    public Direction getDirection() {
        Direction direction = getRandomDirection();
        int length = getPathLength(direction.getX(), direction.getY());
        while (length < 1){
            System.out.println(length);
            direction = getRandomDirection();
            length = getPathLength(direction.getX(), direction.getY());
        }
        return direction;
    }
}
