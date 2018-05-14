package tomcarter.bombero.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.game.logic.WorldController;
import tomcarter.bombero.game.logic.WorldRenderer;
import tomcarter.bombero.utils.Assets;

public class Bombero implements ApplicationListener {
	private WorldController controller;
	private WorldRenderer renderer;

	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		controller = new WorldController();
		renderer = new WorldRenderer(controller);
	}

	@Override
	public void resize(int width, int height) {
		//renderer.resize(width, height);
	}

	@Override
	public void render () {
		controller.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		Assets.instance.dispose();
		renderer.dispose();
	}
}
