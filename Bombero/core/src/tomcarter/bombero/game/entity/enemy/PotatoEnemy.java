package tomcarter.bombero.game.entity.enemy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.entity.Explodable;
import tomcarter.bombero.game.entity.enemy.strategy.LongestPath;
import tomcarter.bombero.game.entity.enemy.strategy.RandomPath;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public class PotatoEnemy extends Enemy implements Explodable{
    private static final float FRAME_TIME = 0.4f;
    private static final int FRAMES = 3;
    private static final float EXPLOSION_FRAME_TIME = 0.1f;
    private static final int EXPLOSION_FRAMES = 9;
    private static final float DEFAULT_SPEED = 1f;

    private RandomPath randomPath;
    private LongestPath longestPath;


    public PotatoEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(0.95f, 0.95f);

        randomPath = new RandomPath(this, context);
        longestPath = new LongestPath(this, context);
        direction = randomPath.getDirection();
        speed = DEFAULT_SPEED;

        currentFrameTime = FRAME_TIME;
        regions = Assets.instance.enemies.potato;
        region = regions[0];
    }

    protected Direction chooseDirection(){
        int n = MathUtils.random(99);

        if (n < 66){
            return randomPath.getDirection();
        }
        else {
            return longestPath.getDirection();
        }
    }

    @Override
    public int getScore() {
        return 500;
    }

    @Override
    public boolean canGo(int x, int y) {
        return map.isEmpty(x, y);
    }

    @Override
    protected int getFramesCount() {
        return FRAMES;
    }

    @Override
    protected float getFrameTime() {
        return FRAME_TIME;
    }

    @Override
    protected int getExplodedFramesCount() {
        return EXPLOSION_FRAMES;
    }

    @Override
    protected float getExplodedFrameTime() {
        return EXPLOSION_FRAME_TIME;
    }

    @Override
    protected void setExplodedRegions() {
        regions = Assets.instance.enemies.potatoExplosion;
    }
}
