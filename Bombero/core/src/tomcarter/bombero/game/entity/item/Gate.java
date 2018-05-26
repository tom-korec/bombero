package tomcarter.bombero.game.entity.item;

import tomcarter.bombero.game.entity.Player;
import tomcarter.bombero.game.logic.level.Level;
import tomcarter.bombero.utils.Assets;

public class Gate extends Item {
    private boolean opened;

    public Gate(float positionX, float positionY, Level context) {
        super(positionX, positionY, context);
        regions = Assets.instance.gate.textures;
        region = regions[0];
        opened = false;
    }

    @Override
    public void update(float delta) {
        if (opened){
            frameTime -= delta;
            if (frameTime < 0){
                frameTime = DEFAULT_FRAME_TIME;
                region = regions[(++frameIndex % FRAMES)];
            }
        }
        else{
            if (context.getEnemies().size() == 0){
                opened = true;
            }
        }
    }

    @Override
    public void enter() {
        if (opened){
            Player player = context.getPlayer();
            player.setPosition(position.x, position.y);
            player.beginTransport();
        }
    }

    public void explode() {
        // do nothing yet
    }

    public boolean isDestroyed(){
        return false;
    }



    public void turn(boolean on){
        opened = on;
    }
}
