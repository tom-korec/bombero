package tomcarter.bombero.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class DataManager {
    private static final String PREFERENCES = "bombero/prefs";
    private static final String DATA_LEVEL_COMPLETED = "level.completed";
    private static final String DATA_LEVEL_FIRE_SIZE = "level.fireSize";
    private static final String DATA_LEVEL_BOMB_COUNT = "level.bombCount";

    private static DataManager instance;

    private Preferences data;

    public static void init(){
        instance = new DataManager();
    }

    private DataManager() {
        data = Gdx.app.getPreferences(PREFERENCES);
    }

    private static Preferences getStorage(){
        return instance.data;
    }

    public static int getNumberOfCompletedLevels(){
        return getStorage().getInteger("level.completed");
    }

    public static void setLevelData(int level, int fireSize, int bombCount){
        Preferences storage = getStorage();

        String fireKey = DATA_LEVEL_FIRE_SIZE + "." + level;
        String bombKey = DATA_LEVEL_BOMB_COUNT + "." + level;

        int currentLevelCompleted = getNumberOfCompletedLevels();
        if (level > currentLevelCompleted){
            storage.putInteger(DATA_LEVEL_COMPLETED, level);
            storage.putInteger(fireKey, fireSize);
            storage.putInteger(bombKey, bombCount);

        }
        else{
            if (storage.getInteger(fireKey) < fireSize){
                storage.putInteger(fireKey, fireSize);
            }

            if (storage.getInteger(bombKey) < bombCount){
                storage.putInteger(bombKey, bombCount);
            }
        }
        storage.flush();
    }

    public static int getLevelFireSize(int level){
        String fireKey = DATA_LEVEL_FIRE_SIZE + "." + level;
        return getStorage().getInteger(fireKey);
    }

    public static int getLevelBombCount(int level){
        String bombKey = DATA_LEVEL_FIRE_SIZE + "." + level;
        return getStorage().getInteger(bombKey);
    }

}
