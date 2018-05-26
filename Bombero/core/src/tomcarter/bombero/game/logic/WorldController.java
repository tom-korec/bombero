package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.utils.LevelLoader;

public class WorldController extends InputAdapter {
    private Bombero app;

    private boolean paused;

    private LevelLoader levelLoader;
    private Level level;

    private int livesLeft;
    private int score;
    private int fireSize;
    private int bombCount;

    public WorldController(LevelType levelType, Bombero app) {
        this.app = app;
        levelLoader = new LevelLoader();
        init();
        loadLevel(levelType);
    }

    private void init(){
        livesLeft = Constants.STARTING_LIVES;
        score = 0;
        fireSize = Constants.STARTING_FIRE;
        bombCount = Constants.STARTING_BOMBS;
        paused = false;
        Gdx.input.setInputProcessor(this);
    }

    private void loadLevel(LevelType levelType){
        this.level = levelLoader.load(levelType);
        levelLoader.finishLoading();
        this.level.setContext(this);
    }

    public void nextLevel(){
        LevelType next = level.getLevelType().next();
        if (next != null){
            loadLevel(next);
        }
        else{
            // won - last level completed
        }
    }

    public void gameOver(){
        if (--livesLeft < 0){
            app.getRenderer().setRenderGameOver(true);
            paused = true;
        }
        else{
            loadLevel(level.getLevelType());
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

    @Override
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
