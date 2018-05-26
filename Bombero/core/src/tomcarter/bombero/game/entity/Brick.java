package tomcarter.bombero.game.entity;

import tomcarter.bombero.game.entity.item.Item;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public class Brick extends GameObject implements Explodable{
    private static final int LAST_FRAME = 4;
    private static final float DEFAULT_FRAME_TIME = 0.05f;

    private Level level;
    private Item hiddenObject;

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

    public Item getHiddenObject() {
        return hiddenObject;
    }

    public void setHiddenObject(Item hiddenObject) {
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

    public void explode() {
        if (hiddenObject == null){
            level.addFloor(getNormalizedPositionX(), getNormalizedPositionY());
        }
        else {
            level.addItem(hiddenObject);
        }

        exploded = true;
        frameIndex = 0;
        frameTime = DEFAULT_FRAME_TIME;
        region = Assets.instance.brick.breaking[frameIndex];
    }

    public boolean isDestroyed(){
        return frameIndex > LAST_FRAME;
    }
}
