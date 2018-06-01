package tomcarter.bombero.game.logic.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import tomcarter.bombero.game.object.GameObject;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.assets.DataManager;

public class WorldRenderer implements Disposable {

    private static final String TAG = WorldRenderer.class.getName();

    private final int width;
    private final int height;
    private final float widthPercent;
    private final float heightPercent;

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;

    private boolean renderGameOver;

    public WorldRenderer (WorldController worldController, int width, int height) {
        this.worldController = worldController;
        this.width = width;
        this.height = height;
        widthPercent = width / 100f;
        heightPercent = height / 100f;

        init();
    }

    private void init () {
        batch = new SpriteBatch();

        float ratio = calculateRatio();

        camera = new OrthographicCamera(Constants.VIEWPORT_HEIGHT * ratio, Constants.VIEWPORT_HEIGHT);
        centerMap(worldController.getLevel().getMap().getWidth());

        cameraGUI = new OrthographicCamera(width, height);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    private float calculateRatio(){
        return width / (float) height;
    }

    public void centerMap(int arenaWidth){
        float ratio = calculateRatio();
        float totalWidth = Constants.VIEWPORT_HEIGHT * ratio;
        float shift = (totalWidth - arenaWidth);

        camera.position.set((totalWidth - shift) /2, Constants.VIEWPORT_HEIGHT / 2, 0);
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

        batch.end();
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        renderTimeLeft(batch);
        renderLivesLeft(batch);
        renderScore(batch);
        renderHighscore(batch);

        if (renderGameOver){
            renderGameOver(batch);
        }
        else if (worldController.isPaused()){
            renderPaused(batch);
        }

        batch.end();
    }

    private void renderTimeLeft(SpriteBatch batch){
        int time = (int) worldController.getLevel().getTimeLeft();
        int minutes = time / 60;
        int seconds = time % 60;

        Assets.instance.fonts.fontL.draw(batch, "" + minutes + " : " + getStandardizedNumber(seconds, 2),
                35*widthPercent, 6*heightPercent);
    }

    private void renderLivesLeft(SpriteBatch batch){
        int lives = worldController.getLivesLeft();
        float posX = 55*widthPercent;

        for (int i = 1; i <= Constants.NEW_GAME_LIVES; ++i){
            if (i > lives){
                batch.setColor(0.3f, 0.3f, 0.3f, 0.5f);
            }
            batch.draw(Assets.instance.gui.life, posX, 5*heightPercent, 32, 32, 64 ,64, 1, 1, 180);
            posX += 4*widthPercent;
        }
        batch.setColor(1,1,1,1);
    }

    private void renderScore(SpriteBatch batch){
        int score = worldController.getScore();

        Assets.instance.fonts.fontL.draw(batch, "SC:", 10*widthPercent, 6*heightPercent);
        Assets.instance.fonts.fontM.draw(batch, getStandardizedNumber(score, 5), 16*widthPercent, 7*heightPercent);
    }

    private void renderHighscore(SpriteBatch batch){
        int score = DataManager.getHighscore();
        if (score != -1){
            batch.draw(Assets.instance.gui.score, 76*widthPercent, 5*heightPercent, 32, 32, 64 ,64, 1, 1, 180);
            Assets.instance.fonts.fontL.draw(batch, ":", 80*widthPercent, 6*heightPercent);
            Assets.instance.fonts.fontM.draw(batch, getStandardizedNumber(score, 5), 82*widthPercent, 7*heightPercent);
        }
    }

    private String getStandardizedNumber(int number, int dec){
        String result = "" + number;

        int div = 10;

        for (int i = 1; i < dec; ++i){
            if (number / div == 0){
                result = "0" + result;
            }
            div *=10;
        }
        return result;
    }



    private void renderGameOver(SpriteBatch batch){
//        batch.setColor(0, 0, 0, 1);
        Assets.instance.fonts.fontL.draw(batch, "Game over", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
    }

    private void renderPaused(SpriteBatch batch){
//        batch.setColor(0, 0, 0, 1);
        Assets.instance.fonts.fontL.draw(batch, "Paused", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
    }

    public void setRenderGameOver(boolean renderGameOver) {
        this.renderGameOver = renderGameOver;
    }

    @Override
    public void dispose() {

    }
}
