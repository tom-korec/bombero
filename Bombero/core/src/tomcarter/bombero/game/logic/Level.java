package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.*;
import tomcarter.bombero.game.entity.item.Gate;
import tomcarter.bombero.game.entity.item.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level {
    private final int width;
    private final int height;
    private LevelMap map;

    private Player player;
    private List<Item> items;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Floor> floors;
    private List<Bomb> bombs;
    private List<Bomb> justExploded;
    private List<Explosion> explosions;
    private List<Explosion> endedExplosions;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(Player player, List<Wall> walls, List<Brick> bricks, List<Floor> floors) {
        List<GameObject> staticGameObjects = new ArrayList<GameObject>(walls);
        staticGameObjects.addAll(bricks);
        map = new LevelMap(width, height, staticGameObjects);

        this.player = player;
        this.player.setContext(map);
        this.walls = new ArrayList<Wall>(walls);
        this.bricks = new ArrayList<Brick>(bricks);
        this.floors = new ArrayList<Floor>(floors);
        this.items = new ArrayList<Item>();
        bombs = new ArrayList<Bomb>();
        justExploded = new ArrayList<Bomb>();

        explosions = new ArrayList<Explosion>();
        endedExplosions = new ArrayList<Explosion>();
    }

    public void update(float delta){
        player.update(delta);
        updateBombs(delta);
        updateExplosions(delta);
        updateBricks(delta);

        handleExplodedBombs();
        handleEndedExplosions();
    }

    private void updateBombs(float delta){
        for(Bomb bomb : bombs){
            bomb.update(delta);
        }
    }

    private void updateExplosions(float delta){
        for (Explosion explosion : explosions){
            explosion.update(delta);
            if (explosion.isOver()){
                endedExplosions.add(explosion);
            }
        }
    }

    private void updateBricks(float delta){
        for (Iterator<Brick> iterator = bricks.iterator(); iterator.hasNext();) {
            Brick brick = iterator.next();
            if (brick.isDestroyed()) {
                iterator.remove();
                deleteBrick(brick);
            }
        }

        for(Brick brick : bricks){
            brick.update(delta);
        }
    }

    private void handleEndedExplosions(){
        explosions.removeAll(endedExplosions);
        endedExplosions.clear();
    }

    private void handleExplodedBombs(){
        bombs.removeAll(justExploded);
        for (Bomb bomb : justExploded){
            Explosion explosion = new Explosion(map, (int) bomb.getPosition().x, (int) bomb.getPosition().y, bomb.getSize());
            explosions.add(explosion);
        }
        justExploded.clear();
    }

    public int getBombsCount(){
        return bombs.size();
    }

    public void placeBomb(int size){
        Vector2 position = new Vector2(player.getPosition()).add(new Vector2(player.getDimension()).scl(0.5f));
        Bomb bomb = new Bomb( (int) position.x + 0.1f, (int) position.y + 0.1f,this, size);
        bombs.add(bomb);
    }

    public void explode(Bomb bomb){
        justExploded.add(bomb);
    }

    public void addFloor(int x, int y){
        floors.add(new Floor(x, y));
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void deleteBrick(Brick brick){
        int x = brick.getNormalizedPositionX();
        int y = brick.getNormalizedPositionY();
        GameObject hiddenObject = brick.getHiddenObject();
        map.set(x, y, hiddenObject);
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getGameObjects(){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        objects.addAll(floors);
        objects.addAll(items);
        objects.addAll(bricks);
        objects.addAll(walls);
        objects.addAll(bombs);
        objects.add(player);
        objects.addAll(explosions);
        return objects;
    }

}
