package tomcarter.bombero.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements AssetErrorListener, Disposable {
    public static final String TAG = Assets.class.getName();

    public static Assets instance = new Assets();

    private AssetManager assetManager;

    public AssetFonts fonts;
    public AssetPlayer player;


    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        fonts = new AssetFonts();
        player = new AssetPlayer(atlas);
    }


    public class AssetFonts {
        public final BitmapFont defaultFont;

        public AssetFonts () {
            // create three fonts using Libgdx's 15px bitmap font
            defaultFont = new BitmapFont(Gdx.files.internal("arial-15.fnt"), true);
            defaultFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public class AssetPlayer {
        public final TextureRegion[] down;
        public final TextureRegion[] left;
        public final TextureRegion[] right;
        public final TextureRegion[] up;

        public AssetPlayer(TextureAtlas atlas){
            AtlasRegion region = atlas.findRegion("player");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 4, region.getRegionHeight() / 4);

            left = new TextureRegion[4];
            right = new TextureRegion[4];
            up = new TextureRegion[4];
            down = new TextureRegion[4];

            System.arraycopy(regions[0], 0, down, 0, 4);
            System.arraycopy(regions[1], 0, left, 0, 4);
            System.arraycopy(regions[2], 0, right, 0, 4);
            System.arraycopy(regions[3], 0, up, 0, 4);
        }
    }

    public class AssetWall {
        public final TextureRegion texture;

        public AssetWall(TextureAtlas atlas) {
            this.texture = atlas.findRegion("wall");
        }
    }

    public class AssetFloor {
        public final TextureRegion texture;

        public AssetFloor(TextureAtlas atlas) {
            this.texture = atlas.findRegion("floor");
        }
    }

    public class AssetBrick {
        public final TextureRegion texture;

        public AssetBrick(TextureAtlas atlas) {
            this.texture = atlas.findRegion("brick");
        }
    }

    public class AssetBomb {
        public final TextureRegion[] textures;

        public AssetBomb(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("bomb");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 3, region.getRegionHeight());
            this.textures = new TextureRegion[3];
            System.arraycopy(regions[0], 0, textures, 0, 3);
        }
    }

    public class AssetExplosion {
        public final TextureRegion[] downEnd;
        public final TextureRegion[] leftEnd;
        public final TextureRegion[] rightEnd;
        public final TextureRegion[] upEnd;
        public final TextureRegion[] center;
        public final TextureRegion[] horizontal;
        public final TextureRegion[] vertical;


        public AssetExplosion(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("explosion");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 4, region.getRegionHeight() / 7);
            downEnd = new TextureRegion[4];
            leftEnd = new TextureRegion[4];
            rightEnd = new TextureRegion[4];
            upEnd = new TextureRegion[4];
            center = new TextureRegion[4];
            horizontal = new TextureRegion[4];
            vertical = new TextureRegion[4];

            System.arraycopy(regions[0], 0, downEnd, 0, 4);
            System.arraycopy(regions[1], 0, leftEnd, 0, 4);
            System.arraycopy(regions[2], 0, rightEnd, 0, 4);
            System.arraycopy(regions[3], 0, upEnd, 0, 4);
            System.arraycopy(regions[4], 0, center, 0, 4);
            System.arraycopy(regions[5], 0, horizontal, 0, 4);
            System.arraycopy(regions[6], 0, vertical, 0, 4);
        }
    }


    @Override
    public void dispose () {
        assetManager.dispose();
        fonts.defaultFont.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }
}
