package tomcarter.bombero.game.entity.enemy;

import tomcarter.bombero.game.logic.level.Level;

public class PigEnemy extends Enemy {
    public PigEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
    }

    @Override
    public int getScore() {
        return 1000;
    }

    @Override
    public void explode() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void update(float delta) {

    }
}
