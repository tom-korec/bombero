package tomcarter.bombero.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.utils.Constants;

public class DesktopLauncher {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "../../desktop/assets-raw/images", "images", "textures");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.width = 1920;
		config.height = 1080;
		config.vSyncEnabled = true;
		config.addIcon(Constants.ICON, Files.FileType.Internal);
		new LwjglApplication(new Bombero(), config);
//		config.fullscreen = true;
//		config.width = Gdx.graphics.getWidth();
//		config.height = Gdx.graphics.getHeight();
//		config.vSyncEnabled = true;
	}
}
