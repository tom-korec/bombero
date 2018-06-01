package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.utils.Assets;

public class CutSceneScreen extends InputScreen {
    private String text;
    private float timeLeft;
    private InputScreen next;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture background;

    public CutSceneScreen(String text, float timeLeft, InputScreen next) {
        super();

        this.text = text;
        this.timeLeft = timeLeft;
        this.next = next;

        this.batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 0, 0);
        camera.setToOrtho(true);
        camera.update();
        background = Assets.instance.menu.background;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        timeLeft -= delta;



        if (timeLeft < 0){
            Bombero.showScreen(next);
            return;
        }

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.instance.fonts.menuSelected.draw(batch, text, 40*widthPercent, 50*heightPercent);

        batch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.ESCAPE:
            case Keys.SPACE:
            case Keys.ENTER:
                Bombero.showScreen(next);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
