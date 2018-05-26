package tomcarter.bombero.game.entity.enemy;

import tomcarter.bombero.game.entity.Explodable;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.game.entity.enemy.strategy.EnemyStrategy;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.Level;
import tomcarter.bombero.game.logic.LevelMap;

import java.util.Random;

public abstract class Enemy extends GameObject implements Explodable{
    protected EnemyStrategy strategy;
    protected Level context;
    protected LevelMap map;


    protected float currentFrameTime;
    protected int frameIndex;

    public Enemy(float positionX, float positionY, Level context) {
        super(positionX, positionY);

        this.context = context;
        map = context.getMap();

        frameIndex = 0;
    }

    public abstract void explode();

    public abstract boolean isDestroyed();


    protected Direction getRandomDirection(){
        Random random = new Random();
        int n = random.nextInt(4);

        switch (n){
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
        }
        return Direction.LEFT;
    }
}
