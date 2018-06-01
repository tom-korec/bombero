package tomcarter.bombero.game.object.dynamic.enemy;

import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.object.movement.LongestPath;
import tomcarter.bombero.game.object.movement.PlayerPath;
import tomcarter.bombero.game.object.movement.RandomPath;
import tomcarter.bombero.game.object.movement.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.assets.Assets;

public class PigEnemy extends Enemy {
    private static final float FRAME_TIME = 0.4f;
    private static final int FRAMES = 3;
    private static final float EXPLOSION_FRAME_TIME = 0.1f;
    private static final int EXPLOSION_FRAMES = 9;
    private static final float DEFAULT_SPEED = 3f;

    private RandomPath randomPath;
    private PlayerPath playerPath;

    public PigEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);

        dimension.set(1f, 1f);

        randomPath = new RandomPath(this, context);
        playerPath = new PlayerPath(this, context);
        direction = canMove() ? randomPath.getDirection(false) : Direction.LEFT;
        speed = DEFAULT_SPEED;

        currentFrameTime = FRAME_TIME;
        regions = Assets.instance.enemies.pig;
        region = regions[0];
    }

    @Override
    public int getScore() {
        return 2000;
    }

    @Override
    public boolean canGo(int x, int y) {
        return map.isEmpty(x, y);
    }

    @Override
    protected Direction chooseDirection() {
        int n = MathUtils.random(99);

        int straightProbability = context.getPlayer().getCenter().dst(getCenter()) <= 10 ? 90 : 50;

        if (n < straightProbability){
            return playerPath.getDirection();
        }
        else if (n < 85){
            return randomPath.getDirection(false);
        }
        else {
            return randomPath.getDirection(true);
        }
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
        regions = Assets.instance.enemies.pigExplosion;
    }
}
