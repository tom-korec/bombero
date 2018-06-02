package tomcarter.bombero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tomcarter.bombero.game.Bombero;
import tomcarter.bombero.game.logic.level.LevelType;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.assets.DataManager;
import tomcarter.bombero.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu screen
 * Application starts here
 * User selects from options what he wants to do
 */
public class MenuScreen extends InputScreen{
    public static MenuScreen instance = new MenuScreen();

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

    }

    public static MenuScreen initScreen(){
        instance.initMainMenuOptions();
        Gdx.input.setInputProcessor(instance);
        return instance;
    }

    private void initMainMenuOptions(){
        final int levelsCompleted = DataManager.getNumberOfCompletedLevels();
        menuOptions = new ArrayList<MenuOption>();
        boolean advancedMenu = levelsCompleted >= 2;
        MenuOption continueGame = new MenuOption("Continue", 14*widthPercent, 45*heightPercent, advancedMenu) {
            @Override
            public void execute() {
                GameScreen.instance.selectLevel(LevelType.valueOf(levelsCompleted));
                Bombero.showScreen(new CutSceneScreen("Level " + levelsCompleted, 3, GameScreen.instance));
            }
        };
        menuOptions.add(continueGame);

        MenuOption newGame = new MenuOption("New game", 14*widthPercent, 55*heightPercent, true) {
            @Override
            public void execute() {
                GameScreen.instance.newGame();
                Bombero.showScreen(new CutSceneScreen("Level 1", 3, GameScreen.instance));
            }
        };
        menuOptions.add(newGame);

        MenuOption selectLevel = new MenuOption("Select level", 14*widthPercent, 65*heightPercent, advancedMenu) {
            @Override
            public void execute() {
                initSelectLevelOptions();
            }
        };
        menuOptions.add(selectLevel);

        MenuOption reset = new MenuOption("Reset", 14 * widthPercent, 75*heightPercent, advancedMenu) {
            @Override
            public void execute() {
                DataManager.reset();
                initMainMenuOptions();
            }
        };
        menuOptions.add(reset);


        MenuOption exit = new MenuOption("Exit", 14*widthPercent, 85*heightPercent, true) {
            @Override
            public void execute() {
                Gdx.app.exit();
            }
        };
        menuOptions.add(exit);
        chooseSelected();
    }

    private void initSelectLevelOptions(){
        menuOptions = new ArrayList<MenuOption>();

        int levelsAvailable = DataManager.getNumberOfCompletedLevels();
        for (int i = 1; i <= Constants.LEVEL_COUNT; ++i){
            float x = widthPercent * 17 + widthPercent * 3 * i;
            float y = i % 2 == 0 ? heightPercent * 88 : heightPercent * 82;
            final int levelNumber = i;
            menuOptions.add(
                new MenuOption("" + levelNumber, x, y, i <= levelsAvailable) {
                    @Override
                    public void execute() {
                        GameScreen.instance.selectLevel(LevelType.valueOf(levelNumber));
                        Bombero.showScreen(new CutSceneScreen("Level " + levelNumber, 3, GameScreen.instance));
                    }
                }
            );
        }

        menuOptions.add(
                new MenuOption("Back", 65*widthPercent, 85*heightPercent, true) {
                    @Override
                    public void execute() {
                        initMainMenuOptions();
                    }
                }
        );
        chooseSelected();
    }

    /**
     * Next selectable option
     */
    private void selectNext(){
        int i = (menuOptions.indexOf(selected) + 1) % menuOptions.size();
        selected.deselect();
        selected = menuOptions.get(i);
        if (selected.isSelectable()){
            selected.select();
        }
        else {
            selectNext();
        }
    }

    /**
     * Previous selectable option
     */
    private void selectPrevious(){
        int i = menuOptions.indexOf(selected) - 1;
        selected.deselect();
        selected = menuOptions.get((i >= 0 ? i : menuOptions.size() - 1));
        if (selected.isSelectable()){
            selected.select();
        }
        else {
            selectPrevious();
        }
    }

    /**
     * First selectable option
     */
    private void chooseSelected(){
        int i = 0;
        do {
            selected = menuOptions.get(i++);
        } while (!selected.isSelectable());
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
