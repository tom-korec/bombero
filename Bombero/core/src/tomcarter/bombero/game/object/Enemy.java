package tomcarter.bombero.game.object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.object.constant.Bomb;
import tomcarter.bombero.game.object.Explodable;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.game.object.movement.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.utils.Int2D;
import tomcarter.bombero.utils.MathHelper;

/**
 * Super class for enemies
 * Contains shared methods for movement
 */
public abstract class Enemy extends GameObject implements Explodable{
    private static final float EXPLODED_FRAME_TIME = 0.3f;

    protected Level context;
    protected LevelMap map;
    protected TextureRegion[] regions;

    protected Direction direction;
    protected float speed;
    protected float blockedMovement;

    protected boolean isExploded;

    protected float currentFrameTime;
    protected int frameIndex;

    public Enemy(float positionX, float positionY, Level context) {
        super(positionX, positionY);
        this.context = context;
        map = context.getMap();

        blockedMovement = 0f;
        isExploded = false;

        frameIndex = 0;
    }

    @Override
    public void update(float delta) {
        if (isExploded){
            animateDeath(delta);
            return;
        }

        checkCollisionWithPlayer();
        checkCollisionWithBomb();

        if (inCenter()){
            if (canChangeDirection() && canMove()){
                direction = chooseDirection();
            }
            if (canMove()){
                move(delta);
            }
        }
        else {
            move(delta);
        }
        bounds.set(position.x, position.y, dimension.x, dimension.y);
        animate(delta);
    }

    @Override
    public void explode() {
        if (!isExploded){
            isExploded = true;
            frameIndex = 0;
            setExplodedRegions();
            region = regions[frameIndex];
            currentFrameTime = getExplodedFrameTime();
        }
    }

    @Override
    public boolean isDestroyed() {
        return frameIndex == getExplodedFramesCount();
    }

    /**
     * Blocks choosing direction for specified distance
     * @param blockedMovement - distance in fields ( 1 field = 1.0)
     */
    public void blockMovement(float blockedMovement){
        this.blockedMovement = blockedMovement;
    }

    /**
     * @return score for destroying enemy
     */
    public abstract int getScore();

    /**
     * @return true if enemy can enter field (x,y)
     */
    public abstract boolean canGo(int x, int y);

    /**
     * Selects a direction using strategy
     * @return next direction
     */
    protected abstract Direction chooseDirection();

    /**
     * @return - normal animation frames count
     */
    protected abstract int getFramesCount();

    /**
     * @return - normal animation frame time
     */
    protected abstract float getFrameTime();

    /**
     * @return - exploding animation frames count
     */
    protected abstract int getExplodedFramesCount();

    /**
     * @return - exploding animation frames time
     */
    protected abstract float getExplodedFrameTime();

    /**
     * sets regions to exploding textures
     */
    protected abstract void setExplodedRegions();

    private void move(float delta){
        float addX = direction.getX() * speed * delta;
        float addY = direction.getY() * speed * delta;
        blockedMovement -= (Math.abs(addX) + Math.abs(addY));

        position.add(addX, addY);
    }

    /**
     * @return true if there is any direction where enemy can move
     */
    protected boolean canMove(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        return canGo(x-1, y) || canGo(x+1, y) || canGo(x, y-1) || canGo(x, y+1);
    }

    private boolean canChangeDirection(){
        return blockedMovement <= 0;
    }

    /**
     * @return true if it is in center of field
     */
    private boolean inCenter(){
        float x = MathHelper.fractionalPart(getCenterX());
        float y = MathHelper.fractionalPart(getCenterY());
        return x > 0.475f && x < 0.525f && y > 0.475f && y < 0.525f;
    }

    private void checkCollisionWithPlayer(){
        Player player = context.getPlayer();
        if ( player.getCenter().dst(getCenter()) < 0.8f){
            player.explode();
        }
    }

    private void checkCollisionWithBomb(){
        handleWallCollision();

        int nextX = getNormalizedPositionX() + direction.getX();
        int nextY = getNormalizedPositionY() + direction.getY();

        if (map.isBomb(nextX, nextY)){
            Bomb bomb = (Bomb) map.at(nextX, nextY);
            float distance = Math.abs(getCenter().dst(bomb.getCenter()));
            if (distance <= 1f){
                direction = direction.opposite();
                blockedMovement = 1 - distance;
            }
            else{
                blockedMovement = -1f;
            }
        }
    }

    private void handleWallCollision(){
        Int2D pos = getNormalizedPosition();
        if (!canGo(pos.x, pos.y)){
            direction = direction.opposite();
            position.set(pos.x + direction.getX(), pos.y + direction.getY());
        }
    }

    private void animate(float delta) {
        currentFrameTime -= delta;
        if (currentFrameTime < 0){
            currentFrameTime = getFrameTime();
            frameIndex = ++frameIndex % getFramesCount();
            region = regions[frameIndex];
        }
    }

    private void animateDeath(float delta){
        currentFrameTime -= delta;
        if (currentFrameTime < 0){
            currentFrameTime = EXPLODED_FRAME_TIME;
            if (++frameIndex != getExplodedFramesCount()){
                region = regions[frameIndex];
            }
        }
    }
}
