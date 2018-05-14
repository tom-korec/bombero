package tomcarter.bombero.game.entity;

import tomcarter.bombero.utils.Assets;

public class Floor extends GameObject {

    public Floor(int positionX, int positionY) {
        super();
        region = Assets.instance.floor.texture;
        position.set(positionX, positionY);
        dimension.set( 1f, 1f);
    }

    @Override
    public void update(float delta) {

    }
}
