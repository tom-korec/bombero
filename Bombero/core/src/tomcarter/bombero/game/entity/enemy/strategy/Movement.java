package tomcarter.bombero.game.entity.enemy.strategy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.entity.Bomb;
import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;

public abstract class Movement {
    protected Enemy enemy;
    protected Level context;
    protected LevelMap map;

    public Movement(Enemy enemy, Level context) {
        this.enemy = enemy;
        this.context = context;
        this.map = context.getMap();
    }

    public abstract Direction getDirection();

    protected int getPathLength(int left, int up){
        int x = enemy.getNormalizedPositionX();
        int y = enemy.getNormalizedPositionY();

        int length = 0;

        for (int i = 1; ; ++i){
            int posX = x + left * i;
            int posY = y + up * i;
            if (enemy.canGo(posX, posY)){
                ++length;
                continue;
            }
            break;
        }
        return length;
    }

    protected static Direction getRandomDirection(){
        int n = MathUtils.random(3);
        return Direction.values()[n];
    }
}
