package tomcarter.bombero.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import tomcarter.bombero.game.entity.Bomb;
import tomcarter.bombero.game.entity.Explodable;
import tomcarter.bombero.game.logic.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public class PotatoEnemy extends Enemy implements Explodable{
    private static final float DEFAULT_FRAME_TIME = 0.4f;
    private static final int FRAMES = 3;
    private TextureRegion[] regions;
    private Direction direction;

    private static final float SPEED = 1f;
    private boolean blocked;
    private float blockedMovement;

    private static final float EXPLODED_FRAME_TIME = 0.3f;
    private static final int EXPLODED_LAST_FRAME = 5;
    private boolean isExploded;


    public PotatoEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(0.95f, 0.95f);

        direction = getRandomDirection();
        blocked = false;
        blockedMovement = 0f;


        isExploded = false;

        currentFrameTime = DEFAULT_FRAME_TIME;
        regions = Assets.instance.enemies.potato;
        region = regions[0];
    }

    @Override
    public void update(float delta) {
        if (isExploded){
            animateDeath(delta);
            return;
        }
        checkCollisionWithPlayer();
        checkCollisionWithBomb();
        if (canChangeDirection()){
            direction = getNextDirection();
        }
        move(delta);
        bounds.set(position.x, position.y, dimension.x, dimension.y);
        currentFrameTime -= delta;
        if (currentFrameTime < 0){
            currentFrameTime = DEFAULT_FRAME_TIME;
            frameIndex = ++frameIndex % FRAMES;
            region = regions[frameIndex];
        }
    }

    private void checkCollisionWithBomb(){
        int nextX = getNormalizedPositionX() + direction.getX();
        int nextY = getNormalizedPositionY() + direction.getY();

        if (map.isBomb(nextX, nextY)){
            Bomb bomb = (Bomb) map.at(nextX, nextY);
            if (Math.abs(getCenter().dst(bomb.getCenter())) <= 1f){
                direction = direction.opposite();
            }
            blockedMovement = -1f;
        }
    }

    private Direction getNextDirection(){
        int n = MathUtils.random(5);

        switch (n){
            case 0:
            case 1:
            case 2:
                return chooseLongestPathWithoutObstacles();
            case 3:
            case 4:
            default:
                return chooseRandomDirection();
        }
    }


    private Direction chooseRandomDirection(){
        Direction direction = Direction.LEFT;
        int length = 0;
        for (int i = 0; i < 10; ++i){
            direction = getRandomDirection();
            length = getPathLength(direction.getX(), direction.getY());
            if (length >= 1){
                blockedMovement = length - 0.1f;
                break;
            }
        }

        blockIfStuck(length);

        return direction;
    }


    private Direction chooseLongestPathWithoutObstacles(){
        Direction direction = Direction.LEFT;
        int last = getPathLength(-1, 0);
        int current = getPathLength(1,0);
        if (last < current){
            direction = Direction.RIGHT;
            last = current;
        }

        current = getPathLength(0, -1);
        if (last < current){
            direction = Direction.DOWN;
            last = current;
        }

        current = getPathLength(0, 1);
        if (last < current){
            direction = Direction.UP;
            last = current;
        }

        blockIfStuck(last);

        if (last > 10){
            last -= 2;
        }

        if (last > 6){
            last -= 2;
        }

        blockedMovement = last - 0.05f;

        return direction;
    }

    private void blockIfStuck(int pathLength){
        if (pathLength == 0){
            blockedMovement = 1f;
            blocked = true;
        }
    }

    private int getPathLength(int left, int up){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        int length = 0;

        for (int i = 1; ; ++i){
            int posX = x + left * i;
            int posY = y + up * i;
            if (map.isEmpty(posX, posY)){
                ++length;
                continue;
            }
            break;
        }
        return length;
    }

    private boolean canChangeDirection(){
        if (blockedMovement > 0){
            return false;
        }
        float x = getCenterX() - getNormalizedPositionX();
        float y = getCenterY() - getNormalizedPositionY();
        return x > 0.45f && x < 0.55f && y > 0.45f && y < 0.55f;
    }

    private void move(float delta){
        float addX = direction.getX() * SPEED * delta;
        float addY = direction.getY() * SPEED * delta;
        blockedMovement -= (Math.abs(addX) + Math.abs(addY));

        if (blocked){
            if (blockedMovement < 0){
                blocked = false;
            }
            return;
        }

        position.add(addX, addY);
    }

    @Override
    public int getScore() {
        return 200;
    }

    @Override
    public void explode() {
        if (isExploded){
            return;
        }

        isExploded = true;
        frameIndex = FRAMES;
        region = regions[frameIndex];
        currentFrameTime = EXPLODED_FRAME_TIME;
    }

    @Override
    public boolean isDestroyed() {
        return frameIndex == EXPLODED_LAST_FRAME;
    }

    private void animateDeath(float delta){
         currentFrameTime -= delta;
         if (currentFrameTime < 0){
             currentFrameTime = EXPLODED_FRAME_TIME;
             if (++frameIndex != EXPLODED_LAST_FRAME){
                 region = regions[frameIndex];
             }
         }
    }
}
