package tomcarter.bombero.game.entity;

import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.utils.Assets;

public class Wall extends GameObject {

    public Wall(float positionX, float positionY) {
        super();
        region = Assets.instance.wall.texture;
        position.set(positionX, positionY);
        dimension.set( 1f, 1f);
    }

    @Override
    public void update(float delta) {

    }


}
