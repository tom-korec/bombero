package tomcarter.bombero.game.logic.level;

import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.constant.Bomb;
import tomcarter.bombero.game.object.constant.BombPowerUp;
import tomcarter.bombero.game.object.constant.Brick;
import tomcarter.bombero.game.object.constant.Wall;
import tomcarter.bombero.utils.Int2D;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LevelMapTest {

    private LevelMap levelMap;
    private static int width = 100;
    private static int height = 100;
    private GameObject brick;
    private GameObject bombPowerUp;
    private GameObject wall;
    private GameObject bomb;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        Level level = mock(Level.class);
        brick = new Brick(35.4f, 50.3f, level);
        bombPowerUp = new BombPowerUp(36, 50, level);
        wall = new Wall(0, 0);
        bomb = new Bomb(10, 10, 1);
        levelMap = new LevelMap(width, height, Arrays.asList(brick, bombPowerUp, wall, bomb));
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(width, levelMap.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(height, levelMap.getHeight());
    }

    @Test
    public void isBrickOrWall() throws Exception {
        assertTrue(levelMap.isBrickOrWall(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
        assertTrue(levelMap.isBrickOrWall(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
        assertFalse(levelMap.isBrickOrWall(bombPowerUp.getNormalizedPositionX(), bombPowerUp.getNormalizedPositionY()));
        assertFalse(levelMap.isBrickOrWall(15, 52));
    }

    @Test
    public void isEmpty() throws Exception {
        assertFalse(levelMap.isEmpty(bombPowerUp.getNormalizedPositionX(), bombPowerUp.getNormalizedPositionY()));
        assertTrue(levelMap.isEmpty(15, 52));
    }

    @Test
    public void isWall() throws Exception {
        assertTrue(levelMap.isWall(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
        assertFalse(levelMap.isWall(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void isBrick() throws Exception {
        assertFalse(levelMap.isBrick(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
        assertTrue(levelMap.isBrick(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void isItem() throws Exception {
        assertTrue(levelMap.isItem(bombPowerUp.getNormalizedPositionX(), bombPowerUp.getNormalizedPositionY()));
        assertFalse(levelMap.isItem(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void isBomb() throws Exception {
        assertTrue(levelMap.isBomb(bomb.getNormalizedPositionX(), bomb.getNormalizedPositionY()));
        assertFalse(levelMap.isBomb(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void at() throws Exception {
        assertEquals(bomb, levelMap.at(bomb.getNormalizedPositionX(), bomb.getNormalizedPositionY()));
    }

    @Test
    public void set() throws Exception {
        assertEquals(wall, levelMap.at(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
        levelMap.set(wall.getNormalizedPositionX(), wall.getNormalizedPositionY(), bomb);
        assertEquals(bomb, levelMap.at(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
    }

    @Test
    public void overlapsField() throws Exception {
        assertTrue(levelMap.overlapsField(brick, brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void getEmptyFieldsAwayFromField() throws Exception {
        Set<Int2D> fields = levelMap.getEmptyFieldsAwayFromField(bombPowerUp.getNormalizedPosition(), 3);
        assertEquals(3, fields.size());
        for (Int2D field : fields) {
            assertTrue(levelMap.isEmpty(field.x, field.y));
            assertTrue(field.distance(bombPowerUp.getNormalizedPosition()) > 4);
        }
    }

}