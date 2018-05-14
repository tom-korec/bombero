package tomcarter.bombero.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import tomcarter.bombero.game.entity.Brick;
import tomcarter.bombero.game.entity.Floor;
import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.game.entity.Wall;
import tomcarter.bombero.game.logic.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static final String TAG = LevelLoader.class.getName();

    public enum PixelType {
        PLAYER_SPAWN(0xFF, 0x0, 0x0), //red
        WALL(0x0, 0x0, 0x0), // black
        BRICK(0x66, 0x33, 0x0), // green
        FLOOR(0xFF, 0xFF, 0xFF); // white

        private int color;

        private PixelType (int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor (int color) {
            return this.color == color;
        }

        public int getColor () {
            return color;
        }
    }

    private Pixmap pixmap;
    private int width;
    private int height;

    private Player player;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Floor> floors;


    public Level load (String filename){
        init(filename);
        loadEntities();
        Gdx.app.debug(TAG, "level '" + filename + "' loaded");

        Level loadedLevel = new Level(width, height, player, walls, bricks, floors);
        return loadedLevel;
    }


    private void init (String filename) {
        pixmap = new Pixmap(Gdx.files.internal(filename));
        walls = new ArrayList<Wall>();
        bricks = new ArrayList<Brick>();
        floors = new ArrayList<Floor>();

        width = pixmap.getWidth();
        height = pixmap.getHeight();
    }

    public void finishLoading(){
        pixmap.dispose();
        walls.clear();
        bricks.clear();
        floors.clear();
    }

    private void loadEntities() {
        for (int pixelY = height - 1; pixelY >= 0; --pixelY) {
            for (int pixelX = 0; pixelX < width; ++pixelX) {
                int currentPixel = pixmap.getPixel(pixelX, pixelY);

                if (PixelType.PLAYER_SPAWN.sameColor(currentPixel)) {
                    player = new Player(pixelX, pixelY);
                    Floor floor = new Floor(pixelX, pixelY);
                    floors.add(floor);
                } else if (PixelType.WALL.sameColor(currentPixel)) {
                    Wall wall = new Wall(pixelX, pixelY);
                    walls.add(wall);
                } else if (PixelType.BRICK.sameColor(currentPixel)) {
                    Brick brick = new Brick(pixelX, pixelY);
                    bricks.add(brick);
                } else if (PixelType.FLOOR.sameColor(currentPixel)) {
                    Floor floor = new Floor(pixelX, pixelY);
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
