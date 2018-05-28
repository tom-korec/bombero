package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.logic.WorldController;
import tomcarter.bombero.game.logic.WorldRenderer;
import tomcarter.bombero.game.logic.level.LevelType;

public class GameScreen extends ScreenAdapter {
    public static GameScreen instance = new GameScreen();

    private WorldController controller;
    private WorldRenderer renderer;

    private GameScreen() {}

    public void newGame(){
        controller = new WorldController(this);
        renderer = new WorldRenderer(controller);
    }

    public void selectLevel(LevelType levelType){
        controller = new WorldController(this, levelType);
        renderer = new WorldRenderer(controller);
        Bombero.showGameScreen();
    }

    public WorldRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void render (float delta) {
        controller.update(delta);
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
