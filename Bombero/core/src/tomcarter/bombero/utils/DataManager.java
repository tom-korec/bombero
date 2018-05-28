package tomcarter.bombero.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class DataManager {
    private static final String PREFERENCES = "bombero/prefs";
    private static final String DATA_LEVEL_COMPLETED = "level.completed";
    private static final String DATA_LEVEL_SCORE = "level.score";
    private static final String DATA_LEVEL_LIFE_LEFT = "level.lifeLeft";
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

    public static void reset(){
        getStorage().clear();
    }

    public static void saveLevelData(int levelNumber, int score, int livesLeft, int fireSize, int bombCount){
        Preferences storage = getStorage();

        String scoreKey = getKey(DATA_LEVEL_SCORE, levelNumber);
        String lifeKey = getKey(DATA_LEVEL_LIFE_LEFT, levelNumber);
        String fireKey = getKey(DATA_LEVEL_FIRE_SIZE, levelNumber);
        String bombKey = getKey(DATA_LEVEL_BOMB_COUNT, levelNumber);

        int currentLevelCompleted = getNumberOfCompletedLevels();
        if (levelNumber > currentLevelCompleted){
            storage.putInteger(DATA_LEVEL_COMPLETED, levelNumber);
            storage.putInteger(scoreKey, score);
            storage.putInteger(lifeKey, livesLeft);
            storage.putInteger(fireKey, fireSize);
            storage.putInteger(bombKey, bombCount);

        }
        else{
            if (storage.getInteger(scoreKey) < score){
                storage.putInteger(scoreKey, score);
            }

            if (storage.getInteger(lifeKey) < livesLeft){
                storage.putInteger(lifeKey, livesLeft);
            }

            if (storage.getInteger(fireKey) < fireSize){
                storage.putInteger(fireKey, fireSize);
            }

            if (storage.getInteger(bombKey) < bombCount){
                storage.putInteger(bombKey, bombCount);
            }
        }
        storage.flush();
    }

    public static int getNumberOfCompletedLevels(){
        return getStorage().getInteger("level.completed");
    }

    public static int getLevelScore(int levelNumber){
        String scoreKey = getKey(DATA_LEVEL_SCORE, levelNumber);
        return getStorage().getInteger(scoreKey);
    }

    public static int getLevelLivesLeft(int levelNumber){
        String lifeKey = getKey(DATA_LEVEL_LIFE_LEFT, levelNumber);
        return getStorage().getInteger(lifeKey);
    }

    public static int getLevelFireSize(int levelNumber){
        String fireKey = getKey(DATA_LEVEL_FIRE_SIZE, levelNumber);
        return getStorage().getInteger(fireKey);
    }

    public static int getLevelBombCount(int levelNumber){
        String bombKey = getKey(DATA_LEVEL_BOMB_COUNT, levelNumber);
        return getStorage().getInteger(bombKey);
    }

    private static String getKey(String prefix, int number){
        return prefix + "." + number;
    }

}
