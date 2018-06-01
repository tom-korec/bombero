package tomcarter.bombero.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestHeader;


public class DataManager {
    private static final String PREFERENCES = "bombero/prefs";
    private static final String DATA_LEVEL_COMPLETED = "level.completed";
    private static final String DATA_LEVEL_SCORE = "level.score";
    private static final String DATA_LEVEL_LIFE_LEFT = "level.lifeLeft";
    private static final String DATA_LEVEL_FIRE_SIZE = "level.fireSize";
    private static final String DATA_LEVEL_BOMB_COUNT = "level.bombCount";
    private static final String HIGH_SCORE_URL = "https://quiet-coast-77581.herokuapp.com/highScore/";
    private static final String AUTHORIZATION = "Basic Ym9tYmVybzpib21iZXJvMTcxOA==";

    private static DataManager instance;

    private Preferences data;
    private int highscore;


    public static void init(){
        instance = new DataManager();
        fetchHighscore();
    }

    private DataManager() {
        data = Gdx.app.getPreferences(PREFERENCES);
        highscore = -1;
    }

    private static Preferences getStorage(){
        return instance.data;
    }

    public static void reset(){
        getStorage().clear();
        getStorage().flush();
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

    public static int getHighscore(){
        return instance.highscore;
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


    public static void fetchHighscore(){
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(HIGH_SCORE_URL);
        request.setHeader(HttpRequestHeader.Authorization, AUTHORIZATION);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                instance.highscore = Integer.parseInt(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                instance.highscore = -1;
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public static void postHighscore(final int highscore){
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setHeader(HttpRequestHeader.Authorization, AUTHORIZATION);
        request.setUrl(HIGH_SCORE_URL + highscore);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                instance.highscore = highscore;
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
