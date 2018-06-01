package tomcarter.bombero.game.object.constant;

import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.GameObject;

public class Floor extends GameObject {

    public Floor(int positionX, int positionY) {
        super(positionX, positionY);
        region = Assets.instance.floor.texture;
        dimension.set( 1f, 1f);
    }

    @Override
    public void update(float delta) {

    }
}
