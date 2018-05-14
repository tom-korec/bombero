package tomcarter.bombero.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.utils.Assets;


public class Explosion extends GameObject {
    private static final float STARTER_FRAME_TIME = 0.2f;
    private static final float DEFAULT_FRAME_TIME = 0.05f;
    private float currentFrameTime;
    private int frameIndex;

    private ExplosionPart center;
    private ExplosionPart[] left;
    private ExplosionPart[] right;
    private ExplosionPart[] up;
    private ExplosionPart[] down;

    public Explosion(int positionX, int positionY, int size) {
        super();
        region = Assets.instance.explosion.center[0];
        position.set(positionX, positionY);

        left = new ExplosionPart[size];
        right = new ExplosionPart[size];
        up = new ExplosionPart[size];
        down = new ExplosionPart[size];

        createLeft(size);
        createRight(size);
        createUp(size);
        createDown(size);

        currentFrameTime = STARTER_FRAME_TIME;
        frameIndex = 0;
    }

    @Override
    public void update(float delta) {
        currentFrameTime -= delta;

        if (currentFrameTime < 0){
            ++frameIndex;
            currentFrameTime = DEFAULT_FRAME_TIME;

            if (frameIndex < 3){
                nextFrame(left, frameIndex);
                nextFrame(right, frameIndex);
                nextFrame(up, frameIndex);
                nextFrame(down, frameIndex);
            }
        }
    }

    public boolean isOver(){
        return frameIndex > 3;
    }

    private void nextFrame(ExplosionPart[] parts, int frame){
        region = Assets.instance.explosion.center[frame];

        for (int i = 0; i < parts.length; i++) {
            parts[i].setFrame(frame);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (ExplosionPart part : left){
            part.render(batch);
        }
        for (ExplosionPart part : right){
            part.render(batch);
        }
        for (ExplosionPart part : up){
            part.render(batch);
        }
        for (ExplosionPart part : down){
            part.render(batch);
        }
    }


    private void createLeft(int size){
        for (int i = 1; i <= size; ++i) {
            ExplosionPart part;
            if (i == size){
                part = new ExplosionPart((int) position.x - i, (int) position.y, Assets.instance.explosion.leftEnd);
            }
            else {
                part = new ExplosionPart((int) position.x - i, (int) position.y, Assets.instance.explosion.horizontal);
            }

            left[i-1] = part;
        }
    }

    private void createRight(int size){
        for (int i = 1; i <= size; ++i) {
            ExplosionPart part;
            if (i == size){
                part = new ExplosionPart((int) position.x + i, (int) position.y, Assets.instance.explosion.rightEnd);
            }
            else {
                part = new ExplosionPart((int) position.x + i, (int) position.y, Assets.instance.explosion.horizontal);
            }
            right[i-1] = part;
        }
    }

    private void createUp(int size){
        for (int i = 1; i <= size; ++i) {
            ExplosionPart part;
            if (i == size){
                part = new ExplosionPart((int) position.x, (int) position.y + i, Assets.instance.explosion.upEnd);
            }
            else {
                part = new ExplosionPart((int) position.x, (int) position.y + i, Assets.instance.explosion.vertical);
            }
            up[i-1] = part;
        }
    }

    private void createDown(int size){
        for (int i = 1; i <= size; ++i) {
            ExplosionPart part;
            if (i == size){
                part = new ExplosionPart((int) position.x, (int) position.y - i, Assets.instance.explosion.downEnd);
            }
            else {
                part = new ExplosionPart((int) position.x, (int) position.y - i, Assets.instance.explosion.vertical);
            }
            down[i-1] = part;
        }
    }

    public class ExplosionPart extends GameObject {
        private TextureRegion[] regions;

        public ExplosionPart(int positionX, int positionY, TextureRegion[] regions) {
            super();
            position.set(positionX, positionY);
            dimension.set(1f, 1f);
            this.region = regions[0];
            this.regions = regions;
        }

        public void setFrame(int frame){
            region = regions[frame];
        }

        @Override
        public void update(float delta) {

        }
    }
}
