package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends InputScreen{
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private List<MenuOption> menuOptions;
    private MenuOption selected;

    private TextureRegion background;

    public MenuScreen() {
        super();
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
                Bombero.showScreen(new CutSceneScreen("Level 1", 3, GameScreen.instance));
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

        int levelsAvailable = DataManager.getNumberOfCompletedLevels();

        selected = new MenuOption("1", widthPercent * 20, heightPercent * 82, true) {
            @Override
            public void execute() {
                GameScreen.instance.newGame();
                Bombero.showScreen(new CutSceneScreen("Level 1", 3000, GameScreen.instance));
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
                        Bombero.showScreen(new CutSceneScreen("Level " + levelNumber, 3, GameScreen.instance));
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
        batch.setProjectionMatrix(camera.combined);

        batch.draw(background, 0, height, 0,0, width, height, 1,-1, 0);
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
