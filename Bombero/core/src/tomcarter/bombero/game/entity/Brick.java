package tomcarter.bombero.game.entity;

import tomcarter.bombero.utils.Assets;

public class Brick extends GameObject {

    public Brick(float positionX, float positionY) {
        super(positionX, positionY);
        region = Assets.instance.brick.texture;
        dimension.set( 1f, 1f);
    }

    @Override
    public void update(float delta) {

    }
}
