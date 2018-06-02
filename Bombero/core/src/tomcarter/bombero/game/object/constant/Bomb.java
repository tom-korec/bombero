package tomcarter.bombero.game.object.constant;

import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.Explodable;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.utils.Constants;

public class Bomb extends GameObject implements Explodable {
    private static final float DEFAULT_FRAME_TIME = 0.4f;
    private static final int[] FRAME_INDEX_SEQUENCE = { 0, 1, 2, 1};
    private int size;
    private float timeToExplode;
    private float currentFrameTime;
    private int frameIndex;

    public Bomb(float positionX, float positionY, int size) {
        super(positionX, positionY);
        region = Assets.instance.bomb.textures[0];
        dimension.set( 1f, 1f);

        this.size = size;
        timeToExplode = Constants.BOMB_TIME_TO_EXPLODE;
        currentFrameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;

    }

    @Override
    public void update(float delta) {
        timeToExplode -= delta;
        currentFrameTime -= delta;

        if (timeToExplode < 0){
            return;
        }

        if (currentFrameTime < 0){
            currentFrameTime = DEFAULT_FRAME_TIME;
            frameIndex = ++frameIndex % 4;
            region = Assets.instance.bomb.textures[FRAME_INDEX_SEQUENCE[frameIndex]];
        }
    }

    public int getSize() {
        return size;
    }


    @Override
    public void explode() {
        timeToExplode = -1f;
    }

    @Override
    public boolean isDestroyed() {
        return timeToExplode < 0;
    }
}
