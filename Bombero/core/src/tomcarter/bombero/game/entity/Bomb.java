package tomcarter.bombero.game.entity;

import tomcarter.bombero.game.logic.Level;
import tomcarter.bombero.utils.Assets;
import tomcarter.bombero.utils.Constants;

public class Bomb extends GameObject {
    private static final float DEFAULT_FRAME_TIME = 0.4f;
    private static final int[] FRAME_INDEX_SEQUENCE = { 0, 1, 2, 1};
    private int size;
    private float timeToExplode;
    private float currentFrameTime;
    private int frameIndex;

    private Level context;

    public Bomb(float positionX, float positionY, Level context, int size) {
        super(positionX, positionY);
        region = Assets.instance.bomb.textures[0];
        dimension.set( 0.8f, 0.8f);
        origin.set(0.4f, 0.4f);

        this.context = context;

        this.size = size;
        timeToExplode = Constants.BOMB_TIME_TO_EXPLODE;
        currentFrameTime = DEFAULT_FRAME_TIME;
        frameIndex = 0;

    }

    @Override
    public void update(float delta) {
        timeToExplode -= delta;
        currentFrameTime -= delta;

        if (timeToExplode < 0){
            context.explode(this);
            return;
        }

        if (currentFrameTime < 0){
            currentFrameTime = DEFAULT_FRAME_TIME;
            frameIndex = ++frameIndex % 4;
            region = Assets.instance.bomb.textures[FRAME_INDEX_SEQUENCE[frameIndex]];
        }
    }

    public int getSize() {
        return size;
    }

    //    public boolean didExplode(){
//        return timeToExplode < 0;
//    }
}
