package tomcarter.bombero.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.item.Item;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.MathHelper;

public class Player extends GameObject implements Explodable{
    private Level context;
    private LevelMap map;

    private static final float START_FRAME_TIME = 0.05f;
    private static final float DEFAULT_FRAME_TIME = 0.1f;

    private static final float EXPLODE_FRAME_TIME = 0.15f;
    private static final int EXPLODE_FRAMES = 8;

    private static final float TRANSPORT_FRAME_TIME = 0.1f;
    private static final float TRANSPORT_START_TIME = 3f;
    private float transportTime;

    private static final float DEFAULT_SPEED = 2.5f;
    private static final float DEFAULT_SIDE_SPEED = DEFAULT_SPEED / 2;
    private float currentFrameTime;
    private int frameIndex;

    private boolean isExploded;
    private TextureRegion explodedRegions[];

    private boolean isTransporting;

    private boolean isMoving;
    private Direction direction;
    private float speed;
    private float currentSpeed;
    private Vector2 directionMultiplier;


    public Player(float positionX, float positionY, Level context){
        super(positionX, positionY);

        this.context = context;

        region = Assets.instance.player.down[0];
        dimension.set(1f, 1f);

        isExploded = false;
        isTransporting = false;

        currentFrameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;

        speed = DEFAULT_SPEED;

        isMoving = false;
        directionMultiplier = new Vector2();
        direction = Direction.DOWN;
    }

    public void setMap(LevelMap map) {
        this.map = map;
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void update(float delta) {
        if (isExploded){
            animateDeath(delta);
        }
        else if (isTransporting){
            animateTransport(delta);
        }
        else if (isMoving) {
            currentSpeed = speed * delta;
            directionMultiplier.set(direction.getX(), direction.getY());
            position.mulAdd(directionMultiplier, currentSpeed);
            bounds.set(position.x + 0.05f, position.y + 0.05f, dimension.x - 0.05f, dimension.y - 0.05f);
            handleCollisions();
            animate(delta);
        }
    }

    public void move(Direction direction){
        if (isExploded || isTransporting){
            return;
        }

        boolean wasDirectionChanged = this.direction != direction;
        isMoving = true;
        this.direction = direction;

        if(wasDirectionChanged){
            resetFrame();
        }
    }

    public void stop(){
        if (isExploded || isTransporting){
            return;
        }

        isMoving = false;
        resetFrame();
    }

    private void handleCollisions(){
        checkItems();

        switch (direction){
            case UP:
                handleCollisionUp();
                break;
            case DOWN:
                handleCollisionDown();
                break;
            case LEFT:
                handleCollisionLeft();
                break;
            case RIGHT:
                handleCollisionRight();
                break;
        }
    }

    private void checkItems(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (map.isItem(x, y)){
            ((Item)map.at(x, y)).enter();
        }
    }

    private void handleCollisionUp(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!map.overlapsField(this, x, y+1)){
            return;
        }

        boolean left = map.overlapsField(this, x - 1, y);
        boolean right = map.overlapsField(this, x + 1, y);

        if (map.isBrickOrWall(x, y+1)){
            position.set(position.x, y+1 - dimension.y);
        }

        if (left){
            if (map.isBrickOrWall(x-1, y+1) && !map.isBrickOrWall(x, y+1)){
                position.set(position.x + currentSpeed, position.y);
            }
        }

        if (right){
            if (map.isBrickOrWall(x+1, y+1) && !map.isBrickOrWall(x, y+1)){
                position.set(position.x - currentSpeed, position.y);
            }
        }
    }

    private void handleCollisionDown(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!map.overlapsField(this, x, y-1)){
            return;
        }

        boolean left = map.overlapsField(this, x - 1, y);
        boolean right = map.overlapsField(this, x + 1, y);

        if (map.isBrickOrWall(x, y-1)){
            position.set(position.x, y - 0.05f);
        }

        if (left){
            if (map.isBrickOrWall(x-1, y-1)  && !map.isBrickOrWall(x, y-1)){
                position.set(position.x + currentSpeed, position.y);
            }
        }

        if (right){
            if (map.isBrickOrWall(x+1, y-1)  && !map.isBrickOrWall(x, y-1)){
                position.set(position.x - currentSpeed, position.y);
            }
        }
    }

    private void handleCollisionLeft(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!map.overlapsField(this, x-1, y)){
            return;
        }

        boolean up = map.overlapsField(this, x, y+1);
        boolean down = map.overlapsField(this, x, y-1);

        if (map.isBrickOrWall(x-1, y)){
            position.set(x - 0.05f, position.y);
        }

        if (up){
            if (map.isBrickOrWall(x-1, y+1) && !map.isBrickOrWall(x-1, y)){
                position.set(position.x, position.y - currentSpeed);
            }
        }

        if (down){
            if (map.isBrickOrWall(x-1, y-1) && !map.isBrickOrWall(x-1, y)){
                position.set(position.x, position.y + currentSpeed);
            }
        }
    }

    private void handleCollisionRight(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!map.overlapsField(this, x+1, y)){
            return;
        }

        boolean up = map.overlapsField(this, x, y+1);
        boolean down = map.overlapsField(this, x, y-1);

        if (map.isBrickOrWall(x+1, y)){
            position.set(x + 1 - dimension.x, position.y);
        }

        if (up){
            if (map.isBrickOrWall(x+1, y+1) && !map.isBrickOrWall(x+1, y)){
                position.set(position.x, position.y - currentSpeed);
            }
        }

        if (down){
            if (map.isBrickOrWall(x+1, y-1) && !map.isBrickOrWall(x+1, y)){
                position.set(position.x, position.y + currentSpeed);
            }
        }
    }

    private void resetFrame(){
        currentFrameTime = START_FRAME_TIME;
        frameIndex = 0;
        updateRegion();
    }

    private void updateRegion(){
        switch (direction){
            case DOWN:
                region = Assets.instance.player.down[frameIndex];
                break;
            case LEFT:
                region = Assets.instance.player.left[frameIndex];
                break;
            case RIGHT:
                region = Assets.instance.player.right[frameIndex];
                break;
            case UP:
                region = Assets.instance.player.up[frameIndex];
                break;
        }
    }

    private void animate(float delta){
        currentFrameTime -= delta;
        if (currentFrameTime < 0){
            currentFrameTime = DEFAULT_FRAME_TIME;
            frameIndex = ++frameIndex % 4;
            updateRegion();
        }
    }

    private void animateDeath(float delta){
        currentFrameTime -= delta;
        if (currentFrameTime < 0){
            currentFrameTime = EXPLODE_FRAME_TIME;
            if (++frameIndex < EXPLODE_FRAMES){
                region = explodedRegions[frameIndex];
            }
        }
    }

    private void animateTransport(float delta){
        currentFrameTime -= delta;
        transportTime -= delta;

        if (transportTime < 0){
            context.nextLevel();
            return;
        }

        if (currentFrameTime < 0){
            currentFrameTime = TRANSPORT_FRAME_TIME;
            direction = direction.nextClockwise();
            updateRegion();
        }
    }

    public void beginTransport(){
        context.levelPassed();
        isTransporting = true;
        currentFrameTime = TRANSPORT_FRAME_TIME;
        transportTime = TRANSPORT_START_TIME;
        frameIndex = 0;
    }

    @Override
    public void explode() {
        isExploded = true;
        frameIndex = 0;
        currentFrameTime = 0;
        explodedRegions = Assets.instance.player.death;
        region = explodedRegions[0];
    }

    @Override
    public boolean isDestroyed() {
        return isExploded && frameIndex == EXPLODE_FRAMES;
    }

    public boolean isExploded(){
        return isExploded;
    }

    public boolean isTransporting() {
        return isTransporting;
    }
}
