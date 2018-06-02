package tomcarter.bombero.game.object.dynamic;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.movement.Direction;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        Level level = new Level(100, 100, LevelType.LEVEL1);
        player = new Player(10, 10, level);
        player.setMap(new LevelMap(100, 100, new ArrayList<GameObject>()));
    }

    @Test
    public void setPosition() throws Exception {
        Vector2 position = new Vector2(15.5f, 13.2f);
        player.setPosition(position.x, position.y);
        assertEquals(position, player.getPosition());
        assertEquals(16, player.getNormalizedPositionX());
        assertEquals(13, player.getNormalizedPositionY());
        assertEquals(16.0, player.getCenterX(), 0.001);
        assertEquals(13.7, player.getCenterY(), 0.001);
    }

    @Test
    public void updateDown() throws Exception {
        player.move(Direction.DOWN);
        player.update(1);
        assertEquals(10, player.getNormalizedPositionX());
        assertEquals(8, player.getNormalizedPositionY());
    }

    @Test
    public void updateUp() throws Exception {
        player.move(Direction.UP);
        player.update(1);
        assertEquals(10, player.getNormalizedPositionX());
        assertEquals(13, player.getNormalizedPositionY());
    }

    @Test
    public void updateRight() throws Exception {
        player.move(Direction.RIGHT);
        player.update(1);
        assertEquals(13, player.getNormalizedPositionX());
        assertEquals(10, player.getNormalizedPositionY());
    }

    @Test
    public void updateLeft() throws Exception {
        player.move(Direction.LEFT);
        player.update(1);
        assertEquals(8, player.getNormalizedPositionX());
        assertEquals(10, player.getNormalizedPositionY());
    }

    @Test
    public void moveDown() throws Exception {
        player.move(Direction.DOWN);
        assertEquals(Direction.DOWN, player.getDirection());
    }

    @Test
    public void moveUp() throws Exception {
        player.move(Direction.UP);
        assertEquals(Direction.UP, player.getDirection());
    }

    @Test
    public void moveRight() throws Exception {
        player.move(Direction.RIGHT);
        assertEquals(Direction.RIGHT, player.getDirection());
    }

    @Test
    public void moveLeft() throws Exception {
        player.move(Direction.LEFT);
        assertEquals(Direction.LEFT, player.getDirection());
    }

    @Test
    public void explode() throws Exception {
        assertFalse(player.isExploded());
        player.explode();
        assertTrue(player.isExploded());
    }

}