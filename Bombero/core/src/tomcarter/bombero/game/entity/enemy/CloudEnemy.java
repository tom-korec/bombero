package tomcarter.bombero.game.entity.enemy;

import tomcarter.bombero.game.logic.level.Level;

public class CloudEnemy extends Enemy {
    public CloudEnemy(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
    }

    @Override
    public void update(float delta) {

    }


    @Override
    public int getScore() {
        return 500;
    }

    @Override
    public void explode() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
