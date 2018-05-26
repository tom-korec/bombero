package tomcarter.bombero.game.logic.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import tomcarter.bombero.game.entity.Bomb;
import tomcarter.bombero.game.entity.Brick;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.game.entity.Wall;
import tomcarter.bombero.game.entity.item.Item;
import tomcarter.bombero.utils.Int2D;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public boolean isItem(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Item;
    }

    public boolean isBomb(int x, int y){
        return !isEmpty(x, y) && staticMap[x][y] instanceof Bomb;
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
