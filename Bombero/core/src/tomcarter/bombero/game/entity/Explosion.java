package tomcarter.bombero.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.logic.Level;
import tomcarter.bombero.game.logic.LevelMap;
import tomcarter.bombero.utils.Assets;

import java.util.ArrayList;
import java.util.List;


public class Explosion extends GameObject {
    public static final float FIRE_OFFSET = 0.15f;
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

        createLeft();
        createRight();
        createUp();
        createDown();

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
        return horizontal.overlaps(object.bounds) || vertical.overlaps(object.bounds);
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
//                if (context.isBrick(x + right * i, y + up * i)){
//                    ((Brick) context.at(x + right * i, y + up * i)).explode();
//                }
                break;
            }
            ++size;
        }
        return size;
    }

    private void createLeft(){
        int size = getPartSize(-1, 0);
        left = new ExplosionPart[size];

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

    private void createRight(){
        int size = getPartSize(1, 0);
        right = new ExplosionPart[size];

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

    private void createUp(){
        int size = getPartSize(0, 1);
        up = new ExplosionPart[size];

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

    private void createDown(){
        int size = getPartSize(0, -1);
        down = new ExplosionPart[size];

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

    private Rectangle createHorizontal(){
        float posX = position.x - left.length + FIRE_OFFSET;
        float posY = position.y + FIRE_OFFSET;
        float width = left.length + dimension.x + right.length - 2 * FIRE_OFFSET;
        float height = dimension.y - 2 * FIRE_OFFSET;

        return new Rectangle(posX, posY, width, height);
    }

    private Rectangle createVertical(){
        float posX = position.x + FIRE_OFFSET;
        float posY = position.y - down.length + FIRE_OFFSET;
        float width = dimension.x - 2 * FIRE_OFFSET;
        float height = down.length + dimension.y + up.length - 2 * FIRE_OFFSET;

        return new Rectangle(posX, posY, width, height);
    }

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
