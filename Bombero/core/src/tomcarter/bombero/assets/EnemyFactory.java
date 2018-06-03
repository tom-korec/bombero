package tomcarter.bombero.assets;

import tomcarter.bombero.game.logic.level.EnemyType;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelMap;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.game.object.dynamic.enemy.CloudEnemy;
import tomcarter.bombero.game.object.Enemy;
import tomcarter.bombero.game.object.dynamic.enemy.PigEnemy;
import tomcarter.bombero.game.object.dynamic.enemy.PotatoEnemy;
import tomcarter.bombero.utils.Int2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Generates enemies based on level number
 */
public class EnemyFactory {
    private Level context;
    private LevelMap map;

    public EnemyFactory(Level context) {
        this.context = context;
        map = context.getMap();
    }

    /**
     * Generates enemies for given param levelType
     * @param levelType - specifies level, type and count of enemies
     * @return list of all enemies for level
     */
    public List<Enemy> createEnemies(LevelType levelType){
        List<Enemy> enemies = new ArrayList<Enemy>();

        EnemyType[] enemyTypes = levelType.getEnemies();

        Set<Int2D> fields = map.getEmptyFieldsAwayFromField(context.getPlayer().getNormalizedPosition(), enemyTypes.length);

        int i = 0;
        for (Int2D p : fields){
            enemies.add(placeEnemy(enemyTypes[i++], p.x, p.y));
        }
        return enemies;
    }


    private Enemy placeEnemy(EnemyType type, int x, int y){
        Enemy enemy;
        switch (type){
            case CLOUD:
                enemy = new CloudEnemy(x, y, context);
                break;
            case PIG:
                enemy = new PigEnemy(x, y, context);
                break;
            case POTATO:
            default:
                enemy = new PotatoEnemy(x, y, context);

        }
        return enemy;
    }
}
