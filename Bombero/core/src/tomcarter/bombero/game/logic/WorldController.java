package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.utils.LevelLoader;

public class WorldController extends InputAdapter {
    LevelLoader levelLoader;
    private Level level;

    private int livesLeft;
    private int score;
    private int fireSize;
    private int bombCount;

    public WorldController(String level) {
        levelLoader = new LevelLoader();
        init();
        loadLevel(level);
    }

    private void init(){
        livesLeft = Constants.STARTING_LIVES;
        score = 0;
        fireSize = Constants.STARTING_FIRE;
        bombCount = Constants.STARTING_BOMBS;
        Gdx.input.setInputProcessor(this);
    }

    private void loadLevel(String level){
        this.level = levelLoader.load(level);
        levelLoader.finishLoading();
    }

    public void update(float delta){
        handleInput();
        level.update(delta);
    }

    public Level getLevel(){
        return level;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;
//            case Keys.SPACE:
//                if (level.getBombsCount() < bombCount){
//                    level.placeBomb();
//                }
//                break;
        }
        return true;
    }

    private void handleInput(){
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

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
            if (level.getBombsCount() < bombCount){
                level.placeBomb(fireSize);
            }
        }
    }
}
