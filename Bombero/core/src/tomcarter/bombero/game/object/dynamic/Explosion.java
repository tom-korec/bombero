package tomcarter.bombero.game.object.dynamic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import tomcarter.bombero.game.object.Explodable;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.assets.Assets;


public class Explosion extends GameObject {
    public static final float FIRE_OFFSET = 0.2f;
    private Level context;
    private LevelMap map;

    private static final float STARTER_FRAME_TIME = 0.2f;
    private static final float DEFAULT_FRAME_TIME = 0.05f;
    private float currentFrameTime;
    private int frameIndex;

    private Rectangle horizontal;
    private Rectangle vertical;

    private int maxSize;
    private ExplosionPart[] left;
    private ExplosionPart[] right;
    private ExplosionPart[] up;
    private ExplosionPart[] down;


    public Explosion(Level context, int positionX, int positionY, int size) {
        super(positionX, positionY);
        region = Assets.instance.explosion.center[0];

        this.context = context;
        this.map = context.getMap();
        this.maxSize = size;

        left = createSideFire(-1, 0);
        right = createSideFire(1,0);
        up = createSideFire(0,1);
        down = createSideFire(0,-1);

        horizontal = createHorizontal();
        vertical = createVertical();
        currentFrameTime = STARTER_FRAME_TIME;
        frameIndex = 0;
    }

    @Override
    public void update(float delta) {
        currentFrameTime -= delta;
        checkCollisions();
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

    private void checkCollisions(){
        Player player = context.getPlayer();
        if (didCollide(player)){
            player.explode();
        }

        for(Enemy enemy : context.getEnemies()){
            if (didCollide(enemy)){
                enemy.explode();
            }
        }
    }

    private boolean didCollide(GameObject object){
        return horizontal.overlaps(object.getBounds()) || vertical.overlaps(object.getBounds());
    }

    /**
     * @return true if explosion is over
     */
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

    /**
     * Computes size of 1 side
     * @param right - horizontal modifier (-1 left, 1 right)
     * @param up - vertical modifier (-1 down, 1 up)
     * @return size of side explosion
     */
    private int getPartSize(int right, int up){
        int size = 0;
        int x = (int) position.x;
        int y = (int) position.y;
        for (int i = 1; i <= maxSize; ++i){
            int xPos = x + right * i;
            int yPos = y + up * i;
            GameObject object = map.at(xPos, yPos);
            if (object != null){
                if (object instanceof Explodable){
                    ((Explodable) object).explode();
                }
                break;
            }
            ++size;
        }
        return size;
    }

    /**
     * Creates parts of 1 side of explosion
     * @param right - horizontal modifier (-1 left, 1 right)
     * @param up - vertical modifier (-1 down, 1 up)
     * @return array containing parts of explosion's side
     */
    private ExplosionPart[] createSideFire(int right, int up){
        int size = getPartSize(right, up);
        TextureRegion[] bodyTextures = getTexturesForBody(right);
        ExplosionPart[] sideFire = new ExplosionPart[size];

        for (int i = 1; i <= size; ++i) {
            ExplosionPart part;
            int x = (int) position.x + right * i;
            int y = (int) position.y + up * i;

            if (i == maxSize){
                part = new ExplosionPart(x, y, getTexturesForEnd(right, up));
            }
            else {
                part = new ExplosionPart(x, y, bodyTextures);
            }

            sideFire[i-1] = part;
        }
        return sideFire;
    }

    private TextureRegion[] getTexturesForBody(int right){
        return right == 0 ? Assets.instance.explosion.vertical : Assets.instance.explosion.horizontal;
    }

    private TextureRegion[] getTexturesForEnd(int right, int up){
        if (right == -1){
            return Assets.instance.explosion.leftEnd;
        }
        else if (right == 1){
            return Assets.instance.explosion.rightEnd;
        }
        else if (up == 1){
            return Assets.instance.explosion.upEnd;
        }
        else {
            return Assets.instance.explosion.downEnd;
        }
    }

    /**
     * @return rectangle of horizontal part for collision checking
     */
    private Rectangle createHorizontal(){
        float posX = position.x - left.length + FIRE_OFFSET;
        float posY = position.y + FIRE_OFFSET;
        float width = left.length + dimension.x + right.length - 2 * FIRE_OFFSET;
        float height = dimension.y - 2 * FIRE_OFFSET;

        return new Rectangle(posX, posY, width, height);
    }

    /**
     * @return rectangle of vertical part for collision checking
     */
    private Rectangle createVertical(){
        float posX = position.x + FIRE_OFFSET;
        float posY = position.y - down.length + FIRE_OFFSET;
        float width = dimension.x - 2 * FIRE_OFFSET;
        float height = down.length + dimension.y + up.length - 2 * FIRE_OFFSET;

        return new Rectangle(posX, posY, width, height);
    }

    /**
     * Part of explosion
     */
    public class ExplosionPart extends GameObject {
        private TextureRegion[] regions;

        public ExplosionPart(int positionX, int positionY, TextureRegion[] regions) {
            super(positionX, positionY);
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
