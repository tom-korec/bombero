package tomcarter.bombero.game.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.Constants;

public class WorldRenderer implements Disposable {

    private static final String TAG = WorldRenderer.class.getName();

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer (WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.update();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void render () {
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld (SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderPlayer(batch);


        batch.end();
    }

    private void renderPlayer(SpriteBatch batch) {
        TextureRegion region = Assets.instance.player.down[0];

        batch.draw(region.getTexture(), 0, 0, 0.5f, 0.5f, 1, 1, 1, 1,
                0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        // draw collected gold coins icon + text (anchored to top left edge)
        renderGuiScore(batch);

        batch.end();
    }

    private int i = 0;

    private void renderGuiScore (SpriteBatch batch) {


        Assets.instance.fonts.defaultFont.draw(batch, "800", 20, 20);
    }

    @Override
    public void dispose() {

    }
}
