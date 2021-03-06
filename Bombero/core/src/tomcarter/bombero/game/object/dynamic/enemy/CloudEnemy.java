package tomcarter.bombero.game.object.dynamic.enemy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.object.movement.LongestPath;
import tomcarter.bombero.game.object.movement.RandomPath;
import tomcarter.bombero.game.object.movement.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.assets.Assets;

public class CloudEnemy extends Enemy {
    private static final float FRAME_TIME = 0.4f;
    private static final int FRAMES = 3;
    private static final float EXPLOSION_FRAME_TIME = 0.1f;
    private static final int EXPLOSION_FRAMES = 9;
    private static final float DEFAULT_SPEED = 2f;

    private RandomPath randomPath;
    private LongestPath longestPath;

    public CloudEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);

        dimension.set(1f, 1f);

        randomPath = new RandomPath(this, context);
        longestPath = new LongestPath(this, context);
        direction = randomPath.getDirection(true);
        speed = DEFAULT_SPEED;

        currentFrameTime = FRAME_TIME;
        regions = Assets.instance.enemies.cloud;
        region = regions[0];
    }

    @Override
    public int getScore() {
        return 1000;
    }

    @Override
    protected Direction chooseDirection() {
        int n = MathUtils.random(99);

        if (n < 70){
            return randomPath.getDirection(false);
        }
        else if (n < 90){
            return randomPath.getDirection(true);
        }
        else {
            return longestPath.getLongestPathDirection();
        }
    }

    @Override
    public boolean canGo(int x, int y) {
        return map.isEmpty(x, y) || map.isBrick(x, y);
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
        regions = Assets.instance.enemies.cloudExplosion;
    }
}
