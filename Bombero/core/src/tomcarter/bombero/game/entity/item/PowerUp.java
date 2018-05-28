package tomcarter.bombero.game.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public abstract class PowerUp extends Item {
    private final static float DESTROY_FRAME_TIME = 0.1f;
    private final static int DESTROY_FRAMES = 7;
    private int destroyFrameIndex;

    protected boolean isTaken;
    private boolean destroyed;
    private TextureRegion destroyedRegions[];


    public PowerUp(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(1f, 1f);
        isTaken = false;
    }

    @Override
    public void update(float delta) {
        frameTime -= delta;
        if (frameTime < 0){
            if (destroyed){
                ++destroyFrameIndex;
                frameTime = DESTROY_FRAME_TIME;
            }
            else{
                frameTime = DEFAULT_FRAME_TIME;
                region = regions[(++frameIndex % FRAMES)];
            }

        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (destroyed && destroyFrameIndex != DESTROY_FRAMES){
            region = destroyedRegions[destroyFrameIndex];
            super.render(batch);
            region = regions[0];
        }
    }

    public void explode() {
        if (destroyed){
            return;
        }

        destroyed = true;
        destroyFrameIndex = 0;
        frameTime = DESTROY_FRAME_TIME;
        region = regions[0];
        destroyedRegions = Assets.instance.powerUp.itemDestroy;
    }

    public boolean isDestroyed(){
        return isTaken || destroyFrameIndex == DESTROY_FRAMES;
    }
}
