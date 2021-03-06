package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import tomcarter.bombero.game.logic.world.WorldController;
import tomcarter.bombero.game.logic.world.WorldRenderer;
import tomcarter.bombero.game.logic.level.LevelType;

/**
 * Game screen
 * Shows game
 */
public class GameScreen extends InputScreen {
    public static GameScreen instance = new GameScreen();

    private WorldController controller;
    private WorldRenderer renderer;

    private GameScreen() {
        super();
    }

    /**
     * starts new game from level 1
     */
    public void newGame(){
        controller = new WorldController();
        renderer = new WorldRenderer(controller, width, height);
    }

    /**
     * starts new game, starting at specified level
     * @param levelType - level, from which will game start
     */
    public void selectLevel(LevelType levelType){
        controller = new WorldController(levelType);
        renderer = new WorldRenderer(controller, width, height);
    }

    @Override
    public void render (float delta) {
        controller.update(delta);
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode) || controller.keyUp(keycode);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
