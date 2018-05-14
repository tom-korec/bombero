package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.utils.Constants;

public class WorldController extends InputAdapter {
    private Level level;

    private int livesLeft;
    private int score;
    private int fireSize;
    private int bombCount;

    public WorldController() {
        init("tad");
    }

    private void init(String level){
        this.level = new Level(new Player(new Vector2(0,0)));
        livesLeft = Constants.STARTING_LIVES;
        score = 0;
        fireSize = Constants.STARTING_FIRE;
        bombCount = Constants.STARTING_BOMBS;
        Gdx.input.setInputProcessor(this);
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
