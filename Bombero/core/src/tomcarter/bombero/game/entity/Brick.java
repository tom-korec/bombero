package tomcarter.bombero.game.entity;

import tomcarter.bombero.game.logic.Level;
import tomcarter.bombero.utils.Assets;

public class Brick extends GameObject {
    private static final int LAST_FRAME = 4;
    private static final float DEFAULT_FRAME_TIME = 0.05f;

    private Level level;
    private GameObject hiddenObject;

    private boolean exploded;
    private float frameTime;
    private int frameIndex;

    public Brick(float positionX, float positionY, Level level) {
        super(positionX, positionY);
        dimension.set( 1f, 1f);
        exploded = false;

        this.level = level;

        region = Assets.instance.brick.texture;
    }

    public GameObject getHiddenObject() {
        return hiddenObject;
    }

    public void setHiddenObject(GameObject hiddenObject) {
        this.hiddenObject = hiddenObject;
    }

    @Override
    public void update(float delta) {
        if (!exploded){
            return;
        }

        frameTime -= delta;
        if (frameTime < 0){
            ++frameIndex;
            if (frameIndex <= LAST_FRAME){
                frameTime = DEFAULT_FRAME_TIME;
                region = Assets.instance.brick.breaking[frameIndex];
            }
        }
    }

    @Override
    public void explode() {
        level.addFloor(getNormalizedPositionX(), getNormalizedPositionY());

        exploded = true;
        frameIndex = 0;
        frameTime = DEFAULT_FRAME_TIME;
        region = Assets.instance.brick.breaking[frameIndex];
    }

    public boolean isDestroyed(){
        return frameIndex > LAST_FRAME;
    }
}
