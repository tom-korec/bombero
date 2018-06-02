package tomcarter.bombero.game.logic.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import tomcarter.bombero.game.object.constant.Brick;
import tomcarter.bombero.game.object.constant.Floor;
import tomcarter.bombero.game.object.dynamic.Player;
import tomcarter.bombero.game.object.constant.Wall;
import tomcarter.bombero.game.object.constant.BombPowerUp;
import tomcarter.bombero.game.object.constant.FirePowerUp;
import tomcarter.bombero.game.object.constant.Gate;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides loading of level data and objects
 */
public class LevelLoader {
    public static final String TAG = LevelLoader.class.getName();

    /**
     * Objects are represented by color
     */
    public enum PixelType {
        PLAYER_SPAWN(0xFF, 0x0, 0x0), //red
        WALL(0x0, 0x0, 0x0), // black
        BRICK(0x66, 0x33, 0x0), // brown
        FLOOR(0xFF, 0xFF, 0xFF), // white
        GATE(0x0, 0xFF, 0x0), // green
        FIRE_POWERUP(0xFF, 0xAA, 0x0), // orange
        BOMB_POWERUP(0x0, 0x0, 0xFF); // blue
        private int color;

        PixelType (int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor (int color) {
            return this.color == color;
        }
    }

    private Level level;

    private Pixmap pixmap;
    private int width;
    private int height;

    private Player player;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Floor> floors;

    /**
     * Loads level
     * @param levelType - level data: path to file, level number
     * @return Level class with loaded game objects
     */
    public Level load (LevelType levelType){
        init(levelType.getPath());
        level = new Level(width, height, levelType);
        loadEntities();
        level.init(player, walls, bricks, floors);
        player.setMap(level.getMap());
        Gdx.app.debug(TAG, "level '" + levelType.getPath() + "' loaded");
        return level;
    }


    private void init (String filename) {
        pixmap = new Pixmap(Gdx.files.internal(filename));
        walls = new ArrayList<Wall>();
        bricks = new ArrayList<Brick>();
        floors = new ArrayList<Floor>();

        width = pixmap.getWidth();
        height = pixmap.getHeight();
    }

    /**
     * Clearing after loading
     */
    public void finishLoading(){
        pixmap.dispose();
        walls.clear();
        bricks.clear();
        floors.clear();
    }

    private void loadEntities() {
        for (int pixelY = 0; pixelY < height; ++pixelY) {
            int invY = height - 1 - pixelY;
            for (int pixelX = 0; pixelX < width; ++pixelX) {
                int currentPixel = pixmap.getPixel(pixelX, pixelY);

                if (PixelType.PLAYER_SPAWN.sameColor(currentPixel)) {
                    player = new Player(pixelX, invY, level);
                    Floor floor = new Floor(pixelX, invY);
                    floors.add(floor);
                } else if (PixelType.WALL.sameColor(currentPixel)) {
                    Wall wall = new Wall(pixelX, invY);
                    walls.add(wall);
                } else if (PixelType.BRICK.sameColor(currentPixel)) {
                    Brick brick = new Brick(pixelX, invY, level);
                    bricks.add(brick);
                } else if (PixelType.GATE.sameColor(currentPixel)) {
                    Brick brick = new Brick(pixelX, invY, level);
                    Gate gate = new Gate(pixelX, invY, level);
                    brick.setHiddenObject(gate);
                    bricks.add(brick);
                } else if (PixelType.FIRE_POWERUP.sameColor(currentPixel)) {
                    Brick brick = new Brick(pixelX, invY, level);
                    FirePowerUp firePowerUp = new FirePowerUp(pixelX, invY, level);
                    brick.setHiddenObject(firePowerUp);
                    bricks.add(brick);
                } else if (PixelType.BOMB_POWERUP.sameColor(currentPixel)) {
                    Brick brick = new Brick(pixelX, invY, level);
                    BombPowerUp bombPowerUp = new BombPowerUp(pixelX, invY, level);
                    brick.setHiddenObject(bombPowerUp);
                    bricks.add(brick);
                } else if (PixelType.FLOOR.sameColor(currentPixel)) {
                    Floor floor = new Floor(pixelX, invY);
                    floors.add(floor);
                } else {
                    int r = 0xff & (currentPixel >>> 24);
                    int g = 0xff & (currentPixel >>> 16);
                    int b = 0xff & (currentPixel >>> 8);
                    int a = 0xff & currentPixel;
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b
                            + "> a<" + a + ">");
                }
            }
        }
    }

}
