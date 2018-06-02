package tomcarter.bombero.game.logic.level;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.logic.world.WorldController;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.constant.*;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.utils.Int2D;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LevelTest {
    private Level level;
    private WorldController controller;
    private Player player;
    private Brick brick;
    private Wall wall;
    private BombPowerUp bombPowerUp;
    private FirePowerUp firePowerUp;
    private Floor floor;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        level = new Level(100, 100, LevelType.LEVEL1);
        controller = mock(WorldController.class);
        level.setContext(controller);
        player = new Player(50, 50, level);
        brick = new Brick(10, 10, level);
        wall = new Wall(0, 0);
        bombPowerUp = new BombPowerUp(10, 10, level);
        firePowerUp = new FirePowerUp(10, 10, level);
        floor = new Floor(1, 1);
        level.init(player, Arrays.asList(wall), Arrays.asList(brick), Arrays.asList(floor));
    }

    @Test
    public void init() throws Exception {
        List<GameObject> gameObjects = level.getGameObjects();
        assertEquals(7, gameObjects.size());
    }

    @Test
    public void playerLosesLife() throws Exception {
        Player player = mock(Player.class);
        when(player.getNormalizedPosition()).thenReturn(new Int2D(10, 10));
        when(player.getCenter()).thenReturn(new Vector2(10, 10));
        when(player.isDestroyed()).thenReturn(true);
        level.init(player, Arrays.asList(wall), Arrays.asList(brick), Arrays.asList(floor));
        level.update(1);
        verify(controller, times(1)).loseLife();
    }

    @Test
    public void placeBomb() throws Exception {
        level.placeBomb(2);
        assertEquals(1, level.getBombsCount());
        assertTrue(level.getMap().isBomb(player.getNormalizedPositionX(), player.getNormalizedPositionY()));
        level.placeBomb(2);
        assertEquals(1, level.getBombsCount());
    }

    @Test
    public void addFloor() throws Exception {
        level.addFloor(1, 1);
        assertEquals(8, level.getGameObjects().size());
    }

    @Test
    public void addItem() throws Exception {
        level.addItem(bombPowerUp);
        assertEquals(8, level.getGameObjects().size());
    }

    @Test
    public void deleteBrick() throws Exception {
        level.deleteBrick(brick);
        assertTrue(level.getMap().isEmpty(brick.getNormalizedPositionX(), brick.getNormalizedPositionY()));
    }

    @Test
    public void deleteObjectFromMap() throws Exception {
        assertEquals(7, level.getGameObjects().size());
        level.deleteObjectFromMap(wall);
        assertEquals(8, level.getGameObjects().size());
        assertTrue(level.getMap().isEmpty(wall.getNormalizedPositionX(), wall.getNormalizedPositionY()));
    }

    @Test
    public void levelPassed() throws Exception {
        level.levelPassed();
        verify(controller).addScore(7500);
    }

    @Test
    public void nextLevel() throws Exception {
        level.nextLevel();
        verify(controller).nextLevel();
    }

    @Test
    public void addFirePowerUp() throws Exception {
        assertEquals(7, level.getGameObjects().size());
        brick.setHiddenObject(firePowerUp);
        level.deleteBrick(brick);
        assertEquals(8, level.getGameObjects().size());
        level.addFirePowerUp(firePowerUp);
        verify(controller).addFirePowerUp();
        assertEquals(8, level.getGameObjects().size());
        assertTrue(level.getMap().isEmpty(firePowerUp.getNormalizedPositionX(), firePowerUp.getNormalizedPositionY()));
    }

    @Test
    public void addBombPowerUp() throws Exception {
        assertEquals(7, level.getGameObjects().size());
        brick.setHiddenObject(bombPowerUp);
        level.deleteBrick(brick);
        assertEquals(8, level.getGameObjects().size());
        level.addBombPowerUp(bombPowerUp);
        verify(controller).addBombPowerUp();
        assertEquals(8, level.getGameObjects().size());
        assertTrue(level.getMap().isEmpty(bombPowerUp.getNormalizedPositionX(), bombPowerUp.getNormalizedPositionY()));
    }

    @Test
    public void getEnemies() throws Exception {
        List<Enemy> enemies = level.getEnemies();
        assertEquals(3, enemies.size());
    }

    @Test
    public void getGameObjects() throws Exception {
        assertEquals(7, level.getGameObjects().size());
    }

}