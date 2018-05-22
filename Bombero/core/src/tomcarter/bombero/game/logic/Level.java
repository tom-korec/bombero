package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int width;
    private final int height;
    private LevelMap map;

    private Player player;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Floor> floors;
    private List<Bomb> bombs;
    private List<Bomb> justExploded;
    private List<Explosion> explosions;
    private List<Explosion> endedExplosions;


    public Level(int width, int height, Player player, List<Wall> walls, List<Brick> bricks, List<Floor> floors) {
        List<GameObject> staticGameObjects = new ArrayList<GameObject>(walls);
        staticGameObjects.addAll(bricks);
        map = new LevelMap(width, height, staticGameObjects);

        this.width = width;
        this.height = height;

        this.player = player;
        this.player.setContext(map);
        this.walls = new ArrayList<Wall>(walls);
        this.bricks = new ArrayList<Brick>(bricks);
        this.floors = new ArrayList<Floor>(floors);
        bombs = new ArrayList<Bomb>();
        justExploded = new ArrayList<Bomb>();

        explosions = new ArrayList<Explosion>();
        endedExplosions = new ArrayList<Explosion>();
    }

    public void update(float delta){
        player.update(delta);
        for(Bomb bomb : bombs){
            bomb.update(delta);
        }

        for (Explosion explosion : explosions){
            explosion.update(delta);
            if (explosion.isOver()){
                endedExplosions.add(explosion);
            }
        }
        handleExplodedBombs();
        handleEndedExplosions();
    }

    private void handleEndedExplosions(){
        explosions.removeAll(endedExplosions);
        endedExplosions.clear();
    }

    private void handleExplodedBombs(){
        bombs.removeAll(justExploded);
        for (Bomb bomb : justExploded){
            Explosion explosion = new Explosion((int) bomb.getPosition().x, (int) bomb.getPosition().y, bomb.getSize());
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

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getGameObjects(){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        objects.addAll(floors);
        objects.addAll(bricks);
        objects.addAll(walls);
        objects.addAll(bombs);
        objects.add(player);
        objects.addAll(explosions);
        return objects;
    }

}
