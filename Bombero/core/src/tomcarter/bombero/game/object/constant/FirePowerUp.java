package tomcarter.bombero.game.object.constant;

import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.PowerUp;

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
