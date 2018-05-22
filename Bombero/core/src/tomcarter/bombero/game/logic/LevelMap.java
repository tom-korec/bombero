package tomcarter.bombero.game.logic;

import com.badlogic.gdx.math.Rectangle;
import tomcarter.bombero.game.entity.Brick;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.game.entity.Wall;

import java.util.List;

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

    public boolean isBrickOrWall(int x, int y){
        return isBrick(x, y) || isWall(x, y);
    }

    public boolean isEmpty(int x, int y){
         return staticMap[x][y] == null;
    }

    public boolean isWall(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Wall;
    }

    public boolean isBrick(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Brick;
    }

    public GameObject at(int x, int y){
        return staticMap[x][y];
    }

    public void set(int x, int y, GameObject gameObject){
        staticMap[x][y] = gameObject;
    }

    public boolean overlapsField(GameObject object, int x, int y){
        Rectangle field = new Rectangle(x, y, 1, 1);
        return object.getBounds().overlaps(field);
    }
}
