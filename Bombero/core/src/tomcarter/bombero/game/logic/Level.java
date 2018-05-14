package tomcarter.bombero.game.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.game.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private Player player;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Bomb> bombs;
    private List<Bomb> justExploded;
    private List<Explosion> explosions;
    private List<Explosion> endedExplosions;


    public Level(Player player) {
        this.player = player;
        walls = new ArrayList<Wall>();
        bricks = new ArrayList<Brick>();
        bombs = new ArrayList<Bomb>();
        justExploded = new ArrayList<Bomb>();

        explosions = new ArrayList<Explosion>();
        endedExplosions = new ArrayList<Explosion>();

        Wall wall1 = new Wall(1,1);
        Wall wall2 = new Wall(1,3);
        Wall wall3 = new Wall(3,1);
        Wall wall4 = new Wall(3,3);
        Brick brick1 = new Brick(2,1);
        Brick brick2 = new Brick(1,2);
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        bricks.add(brick1);
        bricks.add(brick2);

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

    public void handleEndedExplosions(){
        explosions.removeAll(endedExplosions);
        endedExplosions.clear();
    }

    public void handleExplodedBombs(){
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
        Floor floor1 = new Floor(0,0);
        Floor floor2 = new Floor(0,1);
        Floor floor3 = new Floor(1,0);
        Floor floor4 = new Floor(2,2);
        Floor floor5 = new Floor(2,0);
        Floor floor6 = new Floor(0,2);
        objects.add(floor1);
        objects.add(floor2);
        objects.add(floor3);
        objects.add(floor4);
        objects.add(floor5);
        objects.add(floor6);
        objects.addAll(bricks);
        objects.addAll(walls);
        objects.addAll(bombs);
        objects.add(player);
        objects.addAll(explosions);
        return objects;
    }
}
