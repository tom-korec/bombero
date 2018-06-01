package tomcarter.bombero.game.entity.enemy.strategy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Int2D;

public class PlayerPath extends Movement {
    public PlayerPath(Enemy enemy, Level context) {
        super(enemy, context);
    }

    public Direction getDirection() {
        Direction direction = getDirectionToPlayer();
        int block;
        if (isPossibleDirection(direction)){
            block = 1;
        }
        else {
            Direction[] possible = new Direction[3];
            int size = 0;
            Direction left = direction.nextAntiClockwise();
            Direction right = direction.nextClockwise();
            Direction back = direction.opposite();

            if (isPossibleDirection(left)) possible[size++] = left;
            if (isPossibleDirection(right)) possible[size++] = right;
            if (isPossibleDirection(back)) possible[size++] = back;

            direction = possible[(MathUtils.random(size-1))];
            int length = getPathLength(direction.getX(), direction.getY());
            if (length >= 3){
                block = MathUtils.random(1,3);
            }
            else block = 1;
        }
        enemy.blockMovement(block - 0.1f);
        return direction;
    }

    private Direction getDirectionToPlayer(){
        Int2D playerPos = context.getPlayer().getNormalizedPosition();
        Int2D enemyPos = enemy.getNormalizedPosition();

        int xDif = enemyPos.x - playerPos.x;
        int yDif = enemyPos.y - playerPos.y;
        boolean preferHorizontal = MathUtils.randomBoolean();

        if (xDif == 0){
            return yDif < 0 ? Direction.UP : Direction.DOWN;
        }

        if (yDif == 0){
            return xDif < 0 ? Direction.RIGHT : Direction.LEFT;
        }

        if (preferHorizontal){
            return xDif < 0 ? Direction.RIGHT : Direction.LEFT;
        }
        else {
            return yDif < 0 ? Direction.UP : Direction.DOWN;
        }
    }

    private boolean isPossibleDirection(Direction direction){
        Int2D enemyPos = enemy.getNormalizedPosition();
        return enemy.canGo(enemyPos.x + direction.getX(), enemyPos.y + direction.getY());
    }
}
