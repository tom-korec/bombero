package tomcarter.bombero.game.object.dynamic;

import java.util.Arrays;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.game.logic.world.WorldController;
import tomcarter.bombero.game.object.Item;
import tomcarter.bombero.game.object.constant.*;
import tomcarter.bombero.game.object.movement.Direction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTest {
    private Player player;
    private Level level;
    private WorldController controller;
    private FirePowerUp firePowerUp;
    private BombPowerUp bombPowerUp;
    private Item gate;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        level = mock(Level.class);
        player = new Player(10, 10, level);
        Brick brick = new Brick(14, 10, level);
        Wall wall = new Wall(10, 14);
        bombPowerUp = new BombPowerUp(20, 20, level);
        firePowerUp = new FirePowerUp(30, 20, level);
        gate = new Gate(40, 20, level);
        player.setMap(new LevelMap(100, 100, Arrays.asList(brick, wall, bombPowerUp, firePowerUp, gate)));
        when(level.getPlayer()).thenReturn(player);
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
    public void updateUpWithCollision() throws Exception {
        player.setPosition(10, 10.7f);
        player.move(Direction.UP);
        player.update(1);
        assertEquals(10, player.getPosition().x, 0.001);
        assertEquals(13, player.getPosition().y, 0.001);
    }

    @Test
    public void updateDownWithCollision() throws Exception {
        player.setPosition(10, 17.3f);
        player.move(Direction.DOWN);
        player.update(1);
        assertEquals(10, player.getPosition().x, 0.001);
        assertEquals(14.95, player.getPosition().y, 0.001);
    }

    @Test
    public void updateRightWithCollision() throws Exception {
        player.setPosition(10.7f, 10);
        player.move(Direction.RIGHT);
        player.update(1);
        assertEquals(13, player.getPosition().x, 0.001);
        assertEquals(10, player.getPosition().y, 0.001);
    }

    @Test
    public void updateLeftWithCollision() throws Exception {
        player.setPosition(17.3f, 10);
        player.move(Direction.LEFT);
        player.update(1);
        assertEquals(14.95, player.getPosition().x, 0.001);
        assertEquals(10, player.getPosition().y, 0.001);
    }

    @Test
    public void takeFirePowerUp() throws Exception {
        player.setPosition(27, 20);
        player.move(Direction.RIGHT);
        player.update(1);
        verify(level, times(1)).addFirePowerUp(firePowerUp);
        assertTrue(firePowerUp.isDestroyed());
    }

    @Test
    public void takeBombPowerUp() throws Exception {
        player.setPosition(17, 20);
        player.move(Direction.RIGHT);
        player.update(1);
        verify(level, times(1)).addBombPowerUp(bombPowerUp);
        assertTrue(bombPowerUp.isDestroyed());
    }

    @Test
    public void enterOpenGate() throws Exception {
        gate.update(1);
        player.setPosition(37, 20);
        assertFalse(player.isTransporting());
        player.move(Direction.RIGHT);
        player.update(1);
        assertTrue(player.isTransporting());
        verify(level, times(1)).levelPassed();
        assertEquals(new Vector2(40, 20), player.getPosition());
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
    public void beginTransport() throws Exception {
        assertFalse(player.isTransporting());
        player.beginTransport();
        verify(level, times(1)).levelPassed();
        assertTrue(player.isTransporting());
    }

    @Test
    public void explode() throws Exception {
        assertFalse(player.isExploded());
        player.explode();
        assertTrue(player.isExploded());
    }

}