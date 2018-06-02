package tomcarter.bombero.game.object.dynamic;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.math.Rectangle;
import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.game.object.constant.Brick;
import tomcarter.bombero.game.object.dynamic.enemy.PotatoEnemy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExplosionTest {

    private Player player;
    private Level level;
    private Explosion explosion;
    private LevelMap levelMap;
    private Brick brick;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        level = mock(Level.class);
        player = new Player(11, 10, level);
        when(level.getPlayer()).thenReturn(player);
        levelMap = new LevelMap(100, 100, new ArrayList<GameObject>());
        brick = mock(Brick.class);
        levelMap.set(10, 11, brick);
        when(level.getMap()).thenReturn(levelMap);
        explosion = new Explosion(level, 10, 10, 2);
    }

    @Test
    public void playerExplodes() throws Exception {
        explosion.update(1);
        assertTrue(player.isExploded());
    }

    @Test
    public void enemyExplodes() throws Exception {
        Enemy enemy = mock(PotatoEnemy.class);
        when(level.getEnemies()).thenReturn(Arrays.asList(enemy));
        when(enemy.getBounds()).thenReturn(new Rectangle(9, 10, 1, 1));
        explosion.update(1);
        verify(enemy, times(1)).explode();
    }

    @Test
    public void brickExplodes() throws Exception {
        explosion.update(1);
        verify(brick, times(1)).explode();
    }
}