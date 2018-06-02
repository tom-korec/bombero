package tomcarter.bombero.game.logic.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import tomcarter.bombero.game.object.constant.Bomb;
import tomcarter.bombero.game.object.constant.Brick;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.constant.Wall;
import tomcarter.bombero.game.object.Item;
import tomcarter.bombero.utils.Int2D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents static objects by 2D array
 */
public class LevelMap {
    private GameObject[][] staticMap;

    public LevelMap(int width, int height, List<GameObject> objects){
        staticMap = new GameObject[width][height];

        for (GameObject object : objects){
            int x = (int) object.getPosition().x;
            int y = (int) object.getPosition().y;
            staticMap[x][y] = object;
        }
    }

    public int getWidth(){
        return staticMap.length;
    }

    public int getHeight(){
        return staticMap[0].length;
    }

    /**
     * Returns true if object at (x,y) is Brick or Wall
     */
    public boolean isBrickOrWall(int x, int y){
        return isBrick(x, y) || isWall(x, y);
    }

    /**
     * Returns true if object at (x,y) is null
     */
    public boolean isEmpty(int x, int y){
         return staticMap[x][y] == null;
    }

    /**
     * Returns true if object at (x,y) is Wall
     */
    public boolean isWall(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Wall;
    }

    /**
     * Returns true if object at (x,y) is Brick
     */
    public boolean isBrick(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Brick;
    }

    /**
     * Returns true if object at (x,y) is Item
     */
    public boolean isItem(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Item;
    }

    /**
     * Returns true if object at (x,y) is Bomb
     */
    public boolean isBomb(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Bomb;
    }

    /**
     * Returns GameObject at (x,y)
     */
    public GameObject at(int x, int y){
        return staticMap[x][y];
    }

    /**
     * Sets GameObject at (x,y)
     */
    public void set(int x, int y, GameObject gameObject){
        staticMap[x][y] = gameObject;
    }

    /**
     * Returns true if object overlaps field at (x,y)
     */
    public boolean overlapsField(GameObject object, int x, int y){
        Rectangle field = new Rectangle(x, y, 1, 1);
        return object.getBounds().overlaps(field);
    }

    /**
     * Finds randomly empty fields
     * Used for generating enemies
     * @param awayFrom - fields has to be at certain distance from this point
     * @param count - number of empty fields
     * @return List of positions (x,y)
     */
    public Set<Int2D> getEmptyFieldsAwayFromField(Int2D awayFrom, int count){
        Set<Int2D> fields = new HashSet<Int2D>();

        while (fields.size() < count){
            fields.add(getRandomEmptyField(awayFrom));
        }
        return fields;
    }

    private Int2D getRandomEmptyField(Int2D awayFrom){
        int x;
        int y;
        Int2D field = new Int2D();
        while (true){
            x = MathUtils.random(staticMap.length - 1);
            y = MathUtils.random(staticMap[0].length - 1);
            field.set(x, y);
            if (isEmpty(x, y) && field.distance(awayFrom) > 4){
                return field;
            }
        }
    }
}
