package tomcarter.bombero.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import tomcarter.bombero.game.screen.InputScreen;
import tomcarter.bombero.game.screen.MenuScreen;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.assets.DataManager;

/**
 * Main class
 * libGdx calls repeatedly render on screen class
 */
public class Bombero extends Game {
	private static Bombero instance;

	public Bombero() {
		instance = this;
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.init(new AssetManager());
		DataManager.init();
		setScreen(MenuScreen.initScreen());
	}

	/**
	 * Sets active screen
	 * Sets inpute processor to new screen
	 * @param screen - new active screen
	 */
	public static void showScreen(InputScreen screen){
		Gdx.input.setInputProcessor(screen);
		instance.setScreen(screen);
	}

	@Override
	public void dispose () {
		Assets.instance.dispose();
	}
}
