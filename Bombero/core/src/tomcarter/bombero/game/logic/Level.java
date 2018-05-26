package tomcarter.bombero.game.logic;

import tomcarter.bombero.game.entity.*;
import tomcarter.bombero.game.entity.enemy.Enemy;
import tomcarter.bombero.game.entity.enemy.PotatoEnemy;
import tomcarter.bombero.game.entity.item.BombPowerUp;
import tomcarter.bombero.game.entity.item.FirePowerUp;
import tomcarter.bombero.game.entity.item.Item;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level {
    private WorldController context;

    private final int width;
    private final int height;
    private LevelMap map;

    private Player player;
    private List<Enemy> enemies;
    private List<Item> items;
    private List<Wall> walls;
    private List<Brick> bricks;
    private List<Floor> floors;
    private List<Bomb> bombs;
    private List<Explosion> explosions;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(Player player, List<Wall> walls, List<Brick> bricks, List<Floor> floors) {
        List<GameObject> staticGameObjects = new ArrayList<GameObject>(walls);
        staticGameObjects.addAll(bricks);
        map = new LevelMap(width, height, staticGameObjects);

        this.player = player;
        this.enemies = new ArrayList<Enemy>();
        this.walls = new ArrayList<Wall>(walls);
        this.bricks = new ArrayList<Brick>(bricks);
        this.floors = new ArrayList<Floor>(floors);
        this.items = new ArrayList<Item>();
        this.bombs = new ArrayList<Bomb>();

        enemies.add(new PotatoEnemy(3f, 10f, this));

        explosions = new ArrayList<Explosion>();
    }

    public void setContext(WorldController context) {
        this.context = context;
    }

    public void update(float delta){
        updatePlayer(delta);
        updateEnemies(delta);
        updateBombs(delta);
        updateExplosions(delta);
        updateItems(delta);
        updateBricks(delta);
    }

    private void updatePlayer(float delta){
        player.update(delta);
        if (player.isDestroyed()){
            context.gameOver();
        }
    }

    private void updateEnemies(float delta){
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
            Enemy enemy = iterator.next();
            enemy.update(delta);

            if (enemy.isDestroyed()){
                iterator.remove();
            }
        }
    }

    private void updateBombs(float delta){
        for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();) {
            Bomb bomb = iterator.next();
            bomb.update(delta);
            if (bomb.isDestroyed()){
                Explosion explosion = new Explosion(this, (int) bomb.getPosition().x, (int) bomb.getPosition().y, bomb.getSize());
                explosions.add(explosion);
                iterator.remove();
                deleteObjectFromMap(bomb);
            }
        }

    }

    private void updateExplosions(float delta){
        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext();) {
            Explosion explosion = iterator.next();
            explosion.update(delta);
            if (explosion.isOver()){
                iterator.remove();
            }
        }
    }

    private void updateBricks(float delta){
        for (Iterator<Brick> iterator = bricks.iterator(); iterator.hasNext();) {
            Brick brick = iterator.next();
            brick.update(delta);
            if (brick.isDestroyed()) {
                iterator.remove();
                deleteBrick(brick);
            }
        }
    }

    private void updateItems(float delta){
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();){
            Item item = iterator.next();
            item.update(delta);

            if (item.isDestroyed()){
                iterator.remove();
                deleteObjectFromMap(item);
            }
        }
    }


    public int getBombsCount(){
        return bombs.size();
    }

    public void placeBomb(int size){
        if (player.isExploded()){
            return;
        }
        int posX = player.getNormalizedPositionX();
        int posY = player.getNormalizedPositionY();
        Bomb bomb = new Bomb( posX, posY,this, size);
        bombs.add(bomb);
        map.set(posX, posY, bomb);
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
        Item hiddenObject = brick.getHiddenObject();
        if (hiddenObject != null){
            items.add(hiddenObject);
        }

        map.set(x, y, hiddenObject);
    }

    public void deleteObjectFromMap(GameObject object){
        int x = object.getNormalizedPositionX();
        int y = object.getNormalizedPositionY();
        Floor floor = new Floor(x, y);
        floors.add(floor);
        map.set(x, y, null);
    }



    public void addFirePowerUp(FirePowerUp powerUp){
        context.addFirePowerUp();
        items.remove(powerUp);
        deleteObjectFromMap(powerUp);
    }

    public void addBombPowerUp(BombPowerUp powerUp){
        context.addBombPowerUp();
        items.remove(powerUp);
        deleteObjectFromMap(powerUp);
    }

    public LevelMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<GameObject> getGameObjects(){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        objects.addAll(floors);
        objects.addAll(items);
        objects.addAll(bricks);
        objects.addAll(walls);
        objects.addAll(bombs);
        objects.add(player);
        objects.addAll(enemies);
        objects.addAll(explosions);
        return objects;
    }

}
