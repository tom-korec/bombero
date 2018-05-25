package tomcarter.bombero.game.entity.item;

import tomcarter.bombero.game.logic.Level;
import tomcarter.bombero.utils.Assets;

public class FirePowerUp extends PowerUp {
    public FirePowerUp(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        dimension.set(1f, 1f);


        regions = Assets.instance.powerUp.fire;
        region = regions[0];
    }

    @Override
    public void enter() {
        isTaken = true;
        context.addFirePowerUp(this);
    }
}
