package tomcarter.bombero.game.entity.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.entity.Explodable;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.game.logic.Level;

public abstract class Item extends GameObject implements Explodable {
    protected TextureRegion[] regions;

    protected static final int FRAMES = 2;
    protected static final float DEFAULT_FRAME_TIME = 0.5f;

    protected int frameIndex;
    protected float frameTime;

    protected Level context;

    public Item(float positionX, float positionY, Level context) {
        super(positionX, positionY);

        this.context = context;

        frameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;
    }

    public abstract void explode();

    public abstract boolean isDestroyed();

    public abstract void enter();
}
