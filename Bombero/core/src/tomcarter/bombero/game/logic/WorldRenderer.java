package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import tomcarter.bombero.game.entity.GameObject;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.utils.DataManager;

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

        Assets.instance.fonts.fontL.draw(batch, "" + minutes + " : " + getStandardizedNumber(seconds, 2), 450, 50);
    }

    private void renderLivesLeft(SpriteBatch batch){
        int lives = worldController.getLivesLeft();
        int posX = 750;

        for (int i = 1; i <= Constants.NEW_GAME_LIVES; ++i){
            if (i > lives){
                batch.setColor(0.3f, 0.3f, 0.3f, 0.5f);
            }
            batch.draw(Assets.instance.gui.life, posX, 40, 32, 32, 64 ,64, 1, 1, 180);
            posX += 70;
        }
        batch.setColor(1,1,1,1);
    }

    private void renderScore(SpriteBatch batch){
        int score = worldController.getScore();

        Assets.instance.fonts.fontL.draw(batch, "SC:", 50, 50);
        Assets.instance.fonts.fontM.draw(batch, getStandardizedNumber(score, 5), 160, 60);
    }

    private void renderHighscore(SpriteBatch batch){
        int score = DataManager.getHighscore();
        if (score != -1){
            batch.draw(Assets.instance.gui.score, 1100, 40, 32, 32, 64 ,64, 1, 1, 180);
            Assets.instance.fonts.fontL.draw(batch, ":", 1164, 50);
            Assets.instance.fonts.fontM.draw(batch, getStandardizedNumber(score, 5), 1195, 60);
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
