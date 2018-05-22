package tomcarter.bombero.game.entity;

import tomcarter.bombero.utils.Assets;

public class Brick extends GameObject {

    public Brick(float positionX, float positionY) {
        super(positionX, positionY);
        dimension.set( 1f, 1f);

        region = Assets.instance.brick.texture;
    }

    @Override
    public void update(float delta) {

    }
}
