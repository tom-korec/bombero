package tomcarter.bombero.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.utils.Int2D;

public abstract class GameObject {
    protected Vector2 position;
    protected Vector2 dimension;
    protected Vector2 origin;
    protected Vector2 scale;
    protected float rotation;
    protected Rectangle bounds;
    protected TextureRegion region;

    public GameObject ( float positionX, float positionY) {
        position = new Vector2(positionX, positionY);
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        bounds = new Rectangle(positionX, positionY, dimension.x, dimension.y);
        region = null;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch){
        batch.draw(region.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getCenterX() {
        return position.x + dimension.x / 2;
    }

    public float getCenterY() {
        return position.y + dimension.y / 2;
    }

    public int getNormalizedPositionX() {
        return (int) (position.x + dimension.x / 2);
    }

    public int getNormalizedPositionY() {
        return (int) (position.y + dimension.y / 2);
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public Vector2 getMiddleDimension() {
        return new Vector2(dimension).scl(0.5f);
    }

    public Vector2 getCenter(){
        return new Vector2(position).add(getMiddleDimension());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Int2D getNormalizedPosition(){
        return new Int2D((int) (position.x + dimension.x / 2), (int) (position.y + dimension.y / 2));
    }
}
