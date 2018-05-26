package tomcarter.bombero.game.logic;

import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.entity.enemy.PotatoEnemy;
import tomcarter.bombero.game.logic.level.EnemyType;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.utils.Int2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EnemyFactory {
    private Level context;
    private LevelMap map;

    public EnemyFactory(Level context) {
        this.context = context;
        map = context.getMap();
    }

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
            case POTATO:
            default:
                enemy = new PotatoEnemy(x, y, context);

        }
        return enemy;
    }
}
