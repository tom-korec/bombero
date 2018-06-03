package tomcarter.bombero.game.logic.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.game.object.movement.Direction;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.game.screen.CutSceneScreen;
import tomcarter.bombero.game.screen.GameScreen;
import tomcarter.bombero.game.screen.MenuScreen;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.assets.DataManager;
import tomcarter.bombero.assets.LevelLoader;

/**
 * World controller
 * Holds game specific data as score, lives left...
 * updates level
 * alters game state - reload / load of levels
 */
public class WorldController {
    private GameScreen app;

    private boolean paused;

    private LevelLoader levelLoader;
    private Level level;

    private int livesLeft;
    private int score;
    private int fireSize;
    private int bombCount;

    /**
     * Starts new game - level 1
     */
    public WorldController() {
        init();
        initNewGame();
    }

    /**
     * Starts game with specific level
     * @param levelType - specifies level
     */
    public WorldController(LevelType levelType) {
        init();
        initLevel(levelType);
    }

    private void init(){
        levelLoader = new LevelLoader();
        paused = false;

    }

    private void initNewGame(){
        score = 0;
        livesLeft = Constants.NEW_GAME_LIVES;
        fireSize = Constants.NEW_GAME_FIRE_SIZE;
        bombCount = Constants.NEW_GAME_BOMB_COUNT;
        loadLevel(LevelType.LEVEL1);
    }

    private void initLevel(LevelType levelType){
        int levelNumber = levelType.getNumber();
        score = DataManager.getLevelScore(levelNumber);
        livesLeft = DataManager.getLevelLivesLeft(levelNumber);
        fireSize = DataManager.getLevelFireSize(levelNumber);
        bombCount = DataManager.getLevelBombCount(levelNumber);
        loadLevel(levelType);
    }

    private void loadLevel(LevelType levelType){
        this.level = levelLoader.load(levelType);
        levelLoader.finishLoading();
        this.level.setContext(this);
    }

    /**
     * Checks if highscore was beaten - if so, it will post new highscore
     * Loads next level if there is one, else shows victory cut scene
     */
    public void nextLevel(){
        LevelType next = level.getLevelType().next();

        int currentHS = DataManager.getHighscore();
        if (currentHS != -1 && score > currentHS){
            DataManager.postHighscore(score);
        }

        if (next != null){
            DataManager.saveLevelData(next.getNumber(), score, livesLeft, fireSize, bombCount);
            loadLevel(next);
            Bombero.showScreen(new CutSceneScreen("Level " + next.getNumber(), 3, GameScreen.instance));
        }
        else{
            Bombero.showScreen(new CutSceneScreen("YOU WON!", 10, MenuScreen.initScreen()));
        }
    }

    /**
     * Subtract 1 life
     * Checks game over condition
     */
    public void loseLife(){
        if (--livesLeft < 0){
            Bombero.showScreen(new CutSceneScreen("GAME OVER!", 5, MenuScreen.initScreen()));
        }
        else{
            LevelType levelType = level.getLevelType();
            if (levelType.getNumber() == 1){
                fireSize = Constants.NEW_GAME_FIRE_SIZE;
                bombCount = Constants.NEW_GAME_BOMB_COUNT;
            }
            else{
                fireSize = DataManager.getLevelFireSize(levelType.getNumber());
                bombCount = DataManager.getLevelBombCount(levelType.getNumber());
            }
            loadLevel(level.getLevelType());
            Bombero.showScreen(new CutSceneScreen("Lives left: " + livesLeft, 3, GameScreen.instance));
        }
    }

    /**
     * Update state of level
     * Handle input
     * @param delta - time since last update
     */
    public void update(float delta){
        if (!paused){
            handleInput();
            level.update(delta);
        }
    }

    public Level getLevel(){
        return level;
    }

    public int getScore(){
        return score;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public boolean isPaused() {
        return paused;
    }

    /**
     * Adds score
     * @param score - score to be added
     */
    public void addScore(int score){
        this.score += score;
    }

    /**
     * increases fire size
     */
    public void addFirePowerUp(){
        ++fireSize;
    }

    /**
     * increases bomb count
     */
    public void addBombPowerUp(){
        ++bombCount;
    }



    /**
     * Handles application controlling keys - pause and return to menu
     * @param keycode - key released
     * @return - if was key handled
     */
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.ESCAPE:
                Bombero.showScreen(MenuScreen.initScreen());
                break;
            case Keys.P:
                paused = !paused;
        }
        return true;
    }

    private void handleInput(){
        handlePlayerMovementInput();

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
            if (level.getBombsCount() < bombCount){
                level.placeBomb(fireSize);
            }
        }
    }

    private void handlePlayerMovementInput(){
        Player player = level.getPlayer();
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            player.move(Direction.DOWN);
        }
        else if (Gdx.input.isKeyPressed(Keys.LEFT)){
            player.move(Direction.LEFT);
        }
        else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            player.move(Direction.RIGHT);
        }
        else if (Gdx.input.isKeyPressed(Keys.UP)){
            player.move(Direction.UP);
        }
        else {
            player.stop();
        }
    }
}
