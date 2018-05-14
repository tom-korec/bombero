package tomcarter.bombero.game.entity;

import tomcarter.bombero.utils.Assets;

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
