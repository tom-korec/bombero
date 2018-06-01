package tomcarter.bombero.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.game.logic.WorldController;
import tomcarter.bombero.game.logic.WorldRenderer;
import tomcarter.bombero.game.screen.GameScreen;
import tomcarter.bombero.game.screen.InputScreen;
import tomcarter.bombero.game.screen.MenuScreen;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.DataManager;

public class Bombero extends Game {
	private static Bombero instance;

	public Bombero() {
		instance = this;
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		DataManager.init();
		setScreen(new MenuScreen());
	}

	public static void showScreen(InputScreen screen){
		Gdx.input.setInputProcessor(screen);
		instance.setScreen(screen);
	}


	@Override
	public void dispose () {
		Assets.instance.dispose();
	}
}
