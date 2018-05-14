package tomcarter.bombero.game.entity;

import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.utils.Assets;

public class Player extends GameObject {
    private static final float START_FRAME_TIME = 0.05f;
    private static final float DEFAULT_FRAME_TIME = 0.1f;
    private static final float DEFAULT_SPEED = 2.5f;
    private float currentFrameTime;
    private int frameIndex;
    private float speed;
    private boolean isMoving;
    private Vector2 directionMultiplier;

    private Direction direction;


    public Player(Vector2 position){
        super();
        region = Assets.instance.player.down[0];
        position.set(position);
        dimension.set(0.8f, 0.8f);

        currentFrameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;

        speed = DEFAULT_SPEED;
        isMoving = false;
        directionMultiplier = new Vector2();
        direction = Direction.DOWN;
    }

    @Override
    public void update(float delta) {
        if (isMoving) {
            directionMultiplier.set(direction.getX(), direction.getY());
            position.mulAdd(directionMultiplier, speed*delta);
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
