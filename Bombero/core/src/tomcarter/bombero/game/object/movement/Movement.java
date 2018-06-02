package tomcarter.bombero.game.object.movement;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;


/**
 * Super class of enemy movement
 * Decides which direction should enemy take
 */
public abstract class Movement {
    protected Enemy enemy;
    protected Level context;
    protected LevelMap map;

    public Movement(Enemy enemy, Level context) {
        this.enemy = enemy;
        this.context = context;
        this.map = context.getMap();
    }

    /**
     * Counts path length
     * @param right - horizontal modifier (-1 left, 1 right)
     * @param up - vertical modifier (-1 down, 1 up)
     * @return length of path which could enemy take
     */
    protected int getPathLength(int right, int up){
        int x = enemy.getNormalizedPositionX();
        int y = enemy.getNormalizedPositionY();

        int length = 0;

        for (int i = 1; ; ++i){
            int posX = x + right * i;
            int posY = y + up * i;
            if (enemy.canGo(posX, posY)){
                ++length;
                continue;
            }
            break;
        }
        return length;
    }

    /**
     * @return Random direction
     */
    protected static Direction getRandomDirection(){
        int n = MathUtils.random(3);
        return Direction.values()[n];
    }
}
