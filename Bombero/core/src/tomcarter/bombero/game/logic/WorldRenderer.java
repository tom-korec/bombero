package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.Constants;

public class WorldRenderer implements Disposable {

    private static final String TAG = WorldRenderer.class.getName();

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;

    private boolean renderGameOver;

    public WorldRenderer (WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        batch = new SpriteBatch();
        float ratio = Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        camera = new OrthographicCamera(Constants.VIEWPORT_HEIGHT * ratio, Constants.VIEWPORT_HEIGHT);
        camera.position.set(Constants.VIEWPORT_HEIGHT * ratio /2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.update();
        cameraGUI = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void resize(int width, int height){
        System.out.println(""+ width + " " + height);
        float ratio = width / (float)height;

        camera = new OrthographicCamera(Constants.VIEWPORT_HEIGHT * ratio, Constants.VIEWPORT_HEIGHT);
        camera.position.set(Constants.VIEWPORT_HEIGHT * ratio /2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.update();
    }

    public void render () {
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld (SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (GameObject object : worldController.getLevel().getGameObjects()) {
            object.render(batch);
        }

        //renderPlayer(batch);


        batch.end();
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        renderGameOver(batch);

        batch.end();
    }

    private int i = 0;

    private void renderGameOver(SpriteBatch batch){
        Assets.instance.fonts.defaultFont.draw(batch, "Game over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    public void setRenderGameOver(boolean renderGameOver) {
        this.renderGameOver = renderGameOver;
    }

    @Override
    public void dispose() {

    }
}
