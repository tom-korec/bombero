package tomcarter.bombero.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.utils.Int2D;

/**
 * Super class of game objects
 * Contains update method
 * Contains render method
 */
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

    /**
     * Updates object - move, changes texture...
     * @param delta - time since last update
     */
    public abstract void update(float delta);

    /**
     * Renders object
     * @param batch - libGdx batch
     */
    public void render(SpriteBatch batch){
        batch.draw(region.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * @return absolute X center of object
     */
    public float getCenterX() {
        return position.x + dimension.x / 2;
    }

    /**
     * @return absolute Y center of object
     */
    public float getCenterY() {
        return position.y + dimension.y / 2;
    }

    /**
     * @return X center cast to int
     */
    public int getNormalizedPositionX() {
        return (int) (position.x + dimension.x / 2);
    }

    /**
     * @return Y center cast to int
     */
    public int getNormalizedPositionY() {
        return (int) (position.y + dimension.y / 2);
    }

    public Vector2 getDimension() {
        return dimension;
    }

    private Vector2 getMiddleDimension() {
        return new Vector2(dimension).scl(0.5f);
    }

    /**
     * @return absolute center of object as a Vector2
     */
    public Vector2 getCenter(){
        return new Vector2(position).add(getMiddleDimension());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @return X,y center cast to int wrapped in Ind2D object
     */
    public Int2D getNormalizedPosition(){
        return new Int2D((int) (position.x + dimension.x / 2), (int) (position.y + dimension.y / 2));
    }
}
