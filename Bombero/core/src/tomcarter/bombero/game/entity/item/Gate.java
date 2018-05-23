package tomcarter.bombero.game.entity.item;

import tomcarter.bombero.utils.Assets;

public class Gate extends Item {
    private static final int FRAMES = 2;
    private static final float DEFAULT_FRAME_TIME = 0.5f;

    private int frameIndex;
    private float frameTime;

    private boolean opened;

    public Gate(float positionX, float positionY) {
        super(positionX, positionY);
        frameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;
        region = Assets.instance.gate.textures[0];

        opened = false;
    }

    @Override
    public void update(float delta) {
        if (opened){
            frameTime -= delta;
            if (frameTime < 0){
                frameTime = DEFAULT_FRAME_TIME;
                region = Assets.instance.gate.textures[(++frameIndex % FRAMES)];
            }
        }
    }
}
