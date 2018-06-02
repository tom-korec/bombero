package tomcarter.bombero.game.object.constant;

import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.PowerUp;

public class BombPowerUp extends PowerUp {
    public BombPowerUp(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(1f, 1f);
        regions = Assets.instance.powerUp.bomb;
        region = regions[0];
    }

    @Override
    public void enter() {
        isTaken = true;
        context.addBombPowerUp(this);
    }
}
