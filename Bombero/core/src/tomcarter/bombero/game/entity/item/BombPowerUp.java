package tomcarter.bombero.game.entity.item;

import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public class BombPowerUp extends PowerUp {
    public BombPowerUp(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(1f, 1f);
        regions = Assets.instance.powerUp.bomb;
        region = regions[0];;
    }

    @Override
    public void enter() {
        isTaken = true;
        context.addBombPowerUp(this);
    }
}
