package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.Constants;
import tomcarter.bombero.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends InputScreen{
    private final int width;
    private final int height;
    private final float widthPercent;
    private final float heightPercent;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private List<MenuOption> menuOptions;
    private MenuOption selected;

    private Texture background;

    public MenuScreen() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        widthPercent = width / 100f;
        heightPercent = height / 100f;

        camera = new OrthographicCamera(width, height);
        camera.position.set(0,0,0);
        camera.setToOrtho(true);
        camera.update();
        this.batch = new SpriteBatch();

        background = Assets.instance.menu.background;

        initMainMenuOptions();

        Gdx.input.setInputProcessor(this);
    }

    private void initMainMenuOptions(){
        menuOptions = new ArrayList<MenuOption>();

        selected = new MenuOption("New game", 14*widthPercent, 55*heightPercent, true) {
            @Override
            public void execute() {
                GameScreen.instance.newGame();
                Bombero.showGameScreen();
            }
        };
        menuOptions.add(selected);

        menuOptions.add(
                new MenuOption("Select level", 14*widthPercent, 65*heightPercent, false) {
                    @Override
                    public void execute() {
                        initSelectLevelOptions();
                    }
                }
        );

        menuOptions.add(
                new MenuOption("Exit", 14*widthPercent, 75*heightPercent, false) {
                    @Override
                    public void execute() {
                        Gdx.app.exit();
                    }
                }
        );
    }

    private void initSelectLevelOptions(){
        menuOptions = new ArrayList<MenuOption>();

        int levelsAvailable = 8;//DataManager.getNumberOfCompletedLevels();

        selected = new MenuOption("1", widthPercent * 20, heightPercent * 82, true) {
            @Override
            public void execute() {
                GameScreen.instance.newGame();
                Bombero.showGameScreen();
            }
        };
        menuOptions.add(selected);

        for (int i = 2; i <= levelsAvailable; ++i){
            float x = widthPercent * 17 + widthPercent * 3 * i;
            float y = i % 2 == 0 ? heightPercent * 88 : heightPercent * 82;
            final int levelNumber = i;
            menuOptions.add(
                new MenuOption("" + levelNumber, x, y, false) {
                    @Override
                    public void execute() {
                        GameScreen.instance.selectLevel(LevelType.valueOf(levelNumber));
                        Bombero.showGameScreen();
                    }
                }
            );
        }

        menuOptions.add(
                new MenuOption("Back", 65*widthPercent, 85*heightPercent, false) {
                    @Override
                    public void execute() {
                        initMainMenuOptions();
                    }
                }
        );
    }

    private void selectNext(){
        int i = (menuOptions.indexOf(selected) + 1) % menuOptions.size();
        selected.deselect();
        selected = menuOptions.get(i);
        selected.select();
    }

    private void selectPrevious(){
        int i = menuOptions.indexOf(selected) - 1;
        selected.deselect();
        selected = menuOptions.get((i >= 0 ? i : menuOptions.size() - 1));
        selected.select();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, width, height, 0,0, background.getWidth(), background.getHeight(), false, true);
        batch.setProjectionMatrix(camera.combined);

        for (MenuOption option : menuOptions){
            option.render(batch);
        }

        batch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Keys.DOWN:
            case Keys.RIGHT:
                selectNext();
                break;
            case Keys.UP:
            case Keys.LEFT:
                selectPrevious();
                break;
            case Keys.ENTER:
                selected.execute();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
