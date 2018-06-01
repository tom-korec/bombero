package tomcarter.bombero.game.object.constant;

import tomcarter.bombero.assets.Assets;
import tomcarter.bombero.game.object.GameObject;

public class Wall extends GameObject {

    public Wall(float positionX, float positionY) {
        super(positionX, positionY);
        dimension.set( 1f, 1f);

        region = Assets.instance.wall.texture;
    }

    @Override
    public void update(float delta) {

    }


}
