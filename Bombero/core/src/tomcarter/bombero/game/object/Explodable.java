package tomcarter.bombero.game.object;

/**
 * Interface for objects which reacts to bomb explosion
 */
public interface Explodable {
    /**
     * Starts animating object explosion
     */
    void explode();

    /**
     * Checks if explosion of object is over
     * @return true if object is destroyed and should be cleared
     */
    boolean isDestroyed();
}
