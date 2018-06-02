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

import java.awt.*;

/**
 * Main
 * Building of atlas
 * Config of application
 */
public class DesktopLauncher {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 2048;
			settings.maxHeight = 2048;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, Constants.RAW_ASSETS, "images", "textures");
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.vSyncEnabled = true;
		config.addIcon(Constants.ICON, Files.FileType.Internal);

		new LwjglApplication(new Bombero(), config);
	}
}
