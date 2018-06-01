package tomcarter.bombero.game.logic.level;

import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public enum LevelType {
    LEVEL1(Constants.PATH_LEVEL1, 1){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL1;
        }

        @Override
        public LevelType next() {
            return LEVEL2;
        }
    },

    LEVEL2(Constants.PATH_LEVEL2, 2){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL2;
        }

        @Override
        public LevelType next() {
            return LEVEL3;
        }
    },
    LEVEL3(Constants.PATH_LEVEL3, 3){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL3;
        }

        @Override
        public LevelType next() {
            return LEVEL4;
        }
    },
    LEVEL4(Constants.PATH_LEVEL4, 4){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL4;
        }

        @Override
        public LevelType next() {
            return LEVEL5;
        }
    },
    LEVEL5(Constants.PATH_LEVEL5, 5){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL5;
        }

        @Override
        public LevelType next() {
            return LEVEL6;
        }
    },
    LEVEL6(Constants.PATH_LEVEL6, 6){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL6;
        }

        @Override
        public LevelType next() {
            return LEVEL7;
        }
    },
    LEVEL7(Constants.PATH_LEVEL7, 7){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL7;
        }

        @Override
        public LevelType next() {
            return LEVEL8;
        }
    },
    LEVEL8(Constants.PATH_LEVEL8, 8){
        @Override
        public EnemyType[] getEnemies() {
            return ENEMIES_LVL8;
        }

        @Override
        public LevelType next() {
            return null;
        }
    };

    private static final EnemyType[] ENEMIES_LVL1 = {
            EnemyType.POTATO, EnemyType.POTATO, EnemyType.POTATO
    };

    private static final EnemyType[] ENEMIES_LVL2 = {
            EnemyType.POTATO, EnemyType.POTATO, EnemyType.POTATO, EnemyType.POTATO, EnemyType.POTATO, EnemyType.CLOUD
    };

    private static final EnemyType[] ENEMIES_LVL3 = {
            EnemyType.POTATO, EnemyType.POTATO, EnemyType.POTATO, EnemyType.CLOUD, EnemyType.CLOUD
    };

    private static final EnemyType[] ENEMIES_LVL4 = {
            EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.CLOUD
    };

    private static final EnemyType[] ENEMIES_LVL5 = {
            EnemyType.POTATO, EnemyType.POTATO, EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.PIG
    };

    private static final EnemyType[] ENEMIES_LVL6 = {
            EnemyType.POTATO, EnemyType.PIG, EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.PIG
    };

    private static final EnemyType[] ENEMIES_LVL7 = {
            EnemyType.PIG, EnemyType.PIG, EnemyType.CLOUD, EnemyType.CLOUD, EnemyType.PIG
    };

    private static final EnemyType[] ENEMIES_LVL8 = {
            EnemyType.PIG, EnemyType.PIG, EnemyType.PIG, EnemyType.PIG, EnemyType.PIG
    };



    private static Map<Integer, LevelType> map = new HashMap<Integer, LevelType>();

    static {
        for (LevelType levelType : LevelType.values()) {
            map.put(levelType.number, levelType);
        }
    }

    public static LevelType valueOf(int levelNumber) {
        return map.get(levelNumber);
    }

    private String path;
    private int number;

    LevelType(String path, int number) {
        this.path = path;
        this.number = number;
    }

    public String getPath() {
        return path;
    }

    public int getNumber() {
        return number;
    }

    public abstract EnemyType[] getEnemies();

    public abstract LevelType next();

}
