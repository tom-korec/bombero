package tomcarter.bombero.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.LevelMap;
import tomcarter.bombero.utils.Assets;

public class Player extends GameObject {
    private LevelMap context;

    private static final float START_FRAME_TIME = 0.05f;
    private static final float DEFAULT_FRAME_TIME = 0.1f;
    private static final float DEFAULT_SPEED = 2.5f;
    private static final float DEFAULT_SIDE_SPEED = DEFAULT_SPEED / 2;
    private float currentFrameTime;
    private int frameIndex;

    private boolean isMoving;
    private Direction direction;
    private float speed;
    private Vector2 directionMultiplier;



    public Player(float positionX, float positionY){
        super(positionX, positionY);
        region = Assets.instance.player.down[0];
        dimension.set(0.8f, 0.8f);

        bounds.set(0, 0, dimension.x, dimension.y);

        currentFrameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;

        speed = DEFAULT_SPEED;
        isMoving = false;
        directionMultiplier = new Vector2();
        direction = Direction.DOWN;
    }

    public void setContext(LevelMap context) {
        this.context = context;
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void update(float delta) {
        if (isMoving) {
            directionMultiplier.set(direction.getX(), direction.getY());
            position.mulAdd(directionMultiplier, speed*delta);
            bounds.set(position.x + 0.05f, position.y + + 0.05f, dimension.x - 0.05f, dimension.y - 0.05f);
            handleCollisions();
            animate(delta);
        }
    }

    public void move(Direction direction){
        boolean wasDirectionChanged = this.direction != direction;
        isMoving = true;
        this.direction = direction;

        if(wasDirectionChanged){
            resetFrame();
        }
    }

    public void stop(){
        isMoving = false;
        resetFrame();
    }

    private void handleCollisions(){
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

    private void handleCollisionUp(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!context.overlapsField(this, x, y+1)){
            return;
        }

        boolean left = context.overlapsField(this, x - 1, y);
        boolean right = context.overlapsField(this, x + 1, y);

        if (context.isBrickOrWall(x, y+1)){
            position.set(position.x, y+1 - dimension.y);
        }

        if (left){
            if (context.isBrickOrWall(x-1, y+1)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(position.x + sideSpeed, y+1 - dimension.y);
            }
        }

        if (right){
            if (context.isBrickOrWall(x+1, y+1)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(position.x - sideSpeed, y+1 - dimension.y);
            }
        }
    }

    private void handleCollisionDown(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!context.overlapsField(this, x, y-1)){
            return;
        }

        boolean left = context.overlapsField(this, x - 1, y);
        boolean right = context.overlapsField(this, x + 1, y);

        if (context.isBrickOrWall(x, y-1)){
            position.set(position.x, y);
        }

        if (left){
            if (context.isBrickOrWall(x-1, y-1)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(position.x + sideSpeed, y);
            }
        }

        if (right){
            if (context.isBrickOrWall(x+1, y-1)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(position.x - sideSpeed, y);
            }
        }
    }

    private void handleCollisionLeft(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!context.overlapsField(this, x-1, y)){
            return;
        }

        boolean up = context.overlapsField(this, x, y+1);
        boolean down = context.overlapsField(this, x, y-1);

        if (context.isBrickOrWall(x-1, y)){
            position.set((float) x, position.y);
        }

        if (up){
            if (context.isBrickOrWall(x-1, y+1) && !context.isBrickOrWall(x-1, y)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(x, position.y - sideSpeed);
            }
        }

        if (down){
            if (context.isBrickOrWall(x-1, y-1) && !context.isBrickOrWall(x-1, y)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(x, position.y + sideSpeed);
            }
        }
    }

    private void handleCollisionRight(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        if (!context.overlapsField(this, x+1, y)){
            return;
        }

        boolean up = context.overlapsField(this, x, y+1);
        boolean down = context.overlapsField(this, x, y-1);

        if (context.isBrickOrWall(x+1, y)){
            position.set(x + 1 - dimension.x, position.y);
        }

        if (up){
            if (context.isBrickOrWall(x+1, y+1) && !context.isBrickOrWall(x+1, y)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(x + 1 - dimension.x, position.y - sideSpeed);
            }
        }

        if (down){
            if (context.isBrickOrWall(x+1, y-1) && !context.isBrickOrWall(x+1, y)){
                float sideSpeed = DEFAULT_SIDE_SPEED * Gdx.graphics.getDeltaTime();
                position.set(x + 1 - dimension.x, position.y + sideSpeed);
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
}
