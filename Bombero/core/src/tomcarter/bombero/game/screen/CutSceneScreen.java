package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.utils.Assets;

public class CutSceneScreen extends InputScreen {
    private String text;
    private float textShift;
    private float timeLeft;
    private InputScreen next;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TextureRegion title;

    public CutSceneScreen(String text, float timeLeft, InputScreen next) {
        super();

        this.text = text;
        countShift();
        this.timeLeft = timeLeft;
        this.next = next;

        this.batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 0, 0);
        camera.setToOrtho(true);
        camera.update();
        title = Assets.instance.menu.title;

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

        Gdx.gl.glClearColor(0x11 / 256f, 0x11 / 256f, 0x11 / 256f, 0xFF / 256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(title, 12.5f*widthPercent, 40*heightPercent, 0,0, 75*widthPercent, 40*heightPercent, 1,-1, 0);
        Assets.instance.fonts.menuSelected.draw(batch, text, (50-textShift)*widthPercent, 50*heightPercent);
        batch.end();
    }


    private void countShift(){
        float shift = 0;
        for(Character c : text.toCharArray()){
            if (Character.isUpperCase(c)){
                shift += 1.4;
            }
            else {
                shift += 0.8f;
            }
        }
        this.textShift = shift;
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
