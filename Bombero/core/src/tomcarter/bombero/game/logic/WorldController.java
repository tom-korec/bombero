package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.game.screen.CutSceneScreen;
import tomcarter.bombero.game.screen.GameScreen;
import tomcarter.bombero.game.screen.MenuScreen;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.utils.DataManager;
import tomcarter.bombero.utils.LevelLoader;

public class WorldController {
    private GameScreen app;

    private boolean paused;

    private LevelLoader levelLoader;
    private Level level;

    private int livesLeft;
    private int score;
    private int fireSize;
    private int bombCount;



    public WorldController(GameScreen app) {
        init(app);
        initNewGame();
    }

    public WorldController(GameScreen app, LevelType levelType) {
        init(app);
        initLevel(levelType);
    }

    private void init(GameScreen app){
        this.app = app;
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

    public void nextLevel(){
        LevelType next = level.getLevelType().next();

        if (next != null){
            DataManager.saveLevelData(next.getNumber(), score, livesLeft, fireSize, bombCount);
            int currentHS = DataManager.getHighscore();
            if (score > currentHS){
                DataManager.postHighscore(score);
            }
            loadLevel(next);
            Bombero.showScreen(new CutSceneScreen("Level " + next.getNumber(), 3, GameScreen.instance));
        }
        else{
            Bombero.showScreen(new CutSceneScreen("YOU WON!", 10, new MenuScreen()));
        }
    }

    public void loseLife(){
        if (--livesLeft < 0){
            Bombero.showScreen(new CutSceneScreen("GAME OVER!", 5, new MenuScreen()));
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

    public void addScore(int score){
        this.score += score;
    }

    public void addFirePowerUp(){
        ++fireSize;
    }

    public void addBombPowerUp(){
        ++bombCount;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.ESCAPE:
                Gdx.app.exit();
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
