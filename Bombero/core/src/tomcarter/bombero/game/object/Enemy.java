package tomcarter.bombero.game.object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.object.constant.Bomb;
import tomcarter.bombero.game.object.Explodable;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.game.object.movement.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.utils.MathHelper;

public abstract class Enemy extends GameObject implements Explodable{
    private static final float EXPLODED_FRAME_TIME = 0.3f;

    protected Level context;
    protected LevelMap map;
    protected TextureRegion[] regions;
    protected TextureRegion[] regionsDeath;

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

    public void blockMovement(float blockedMovement){
        this.blockedMovement = blockedMovement;
    }

    public abstract int getScore();

    public abstract boolean canGo(int x, int y);

    protected abstract Direction chooseDirection();

    protected abstract int getFramesCount();

    protected abstract float getFrameTime();

    protected abstract int getExplodedFramesCount();

    protected abstract float getExplodedFrameTime();

    protected abstract void setExplodedRegions();

    private void move(float delta){
        float addX = direction.getX() * speed * delta;
        float addY = direction.getY() * speed * delta;
        blockedMovement -= (Math.abs(addX) + Math.abs(addY));

        position.add(addX, addY);
    }

    protected boolean canMove(){
        int x = getNormalizedPositionX();
        int y = getNormalizedPositionY();

        return canGo(x-1, y) || canGo(x+1, y) || canGo(x, y-1) || canGo(x, y+1);
    }

    private boolean canChangeDirection(){
        return blockedMovement <= 0;
    }

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
