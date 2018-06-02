package tomcarter.bombero.game.object;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.game.object.constant.Bomb;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.game.object.dynamic.enemy.PotatoEnemy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EnemyTest {
    private Enemy enemy;
    private Level level;
    private LevelMap levelMap;
    private Player player;

    @Before
    public void setUp() throws Exception {
        Assets.init(null);
        level = mock(Level.class);
        player = new Player(10.5f, 10, level);
        levelMap = new LevelMap(100, 100, new ArrayList<GameObject>());
        Bomb bomb = new Bomb(10.5f, 10, 2);
        levelMap.set(11, 10, bomb);
        when(level.getMap()).thenReturn(levelMap);
        when(level.getPlayer()).thenReturn(player);
        enemy = new PotatoEnemy(10, 10, level);
    }

    @Test
    public void collisionWithPlayer() throws Exception {
        enemy.update(1);
        assertTrue(player.isExploded());
    }

    @Test
    public void collisionWithBomb() throws Exception {
        player.setPosition(20, 10);
        enemy.update(1);
        assertEquals(10, enemy.getNormalizedPositionX());
        assertEquals(11, enemy.getNormalizedPositionY());
    }

    @Test
    public void explode() throws Exception {
        assertFalse(enemy.isExploded);
        enemy.explode();
        assertTrue(enemy.isExploded);
    }
}