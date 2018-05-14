package tomcarter.bombero.game.entity;

import com.badlogic.gdx.math.Vector2;
import tomcarter.bombero.utils.Assets;

public class Wall extends GameObject {

    public Wall(float positionX, float positionY) {
        super(positionX, positionY);
        region = Assets.instance.wall.texture;
        dimension.set( 1f, 1f);
    }

    @Override
    public void update(float delta) {

    }


}
