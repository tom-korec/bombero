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

    public AssetMenu menu;
    public AssetFonts fonts;
    public AssetGui gui;

    public AssetPlayer player;
    public AssetEnemy enemies;

    public AssetWall wall;
    public AssetFloor floor;
    public AssetBrick brick;

    public AssetGate gate;
    public AssetPowerUp powerUp;

    public AssetBomb bomb;
    public AssetExplosion explosion;


    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.TEXTURE_BACKGROUND, Texture.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        menu = new AssetMenu();
        fonts = new AssetFonts();
        gui = new AssetGui(atlas);
        player = new AssetPlayer(atlas);
        enemies = new AssetEnemy(atlas);
        wall = new AssetWall(atlas);
        floor = new AssetFloor(atlas);
        brick = new AssetBrick(atlas);
        gate = new AssetGate(atlas);
        powerUp = new AssetPowerUp(atlas);
        bomb = new AssetBomb(atlas);
        explosion = new AssetExplosion(atlas);
    }


    public class AssetMenu {
        public final Texture background;

        public AssetMenu() {
            background = assetManager.get(Constants.TEXTURE_BACKGROUND);
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultFont;
        public final BitmapFont fontXS;
        public final BitmapFont fontS;
        public final BitmapFont fontM;
        public final BitmapFont fontL;

        public AssetFonts () {
            defaultFont = new BitmapFont(Gdx.files.internal(Constants.FONT_DEFAULT), true);
            defaultFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            fontXS = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC24), true);
            fontXS.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            fontS = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC32), true);
            fontS.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            fontM = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC48), true);
            fontM.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            fontL = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC64), true);
            fontL.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public class AssetGui {
        public final TextureRegion life;
        public final TextureRegion score;

        public AssetGui(TextureAtlas atlas) {
            this.life = atlas.findRegion("guiLife");
            this.score = atlas.findRegion("guiScore");
        }

    }

    public class AssetPlayer {
        public final TextureRegion[] down;
        public final TextureRegion[] left;
        public final TextureRegion[] right;
        public final TextureRegion[] up;
        public final TextureRegion[] death;

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

            region = atlas.findRegion("playerDeath");
            regions = region.split(region.getRegionWidth() / 8, region.getRegionHeight());
            death = new TextureRegion[8];
            System.arraycopy(regions[0], 0, death, 0, 8);
        }
    }

    public class AssetEnemy {
        public final TextureRegion potato[];

        public AssetEnemy(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("enemyPotato");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 5, region.getRegionHeight());
            this.potato = new TextureRegion[5];
            System.arraycopy(regions[0], 0, potato, 0, 5);
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
        public final TextureRegion[] breaking;

        public AssetBrick(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("brick");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 6, region.getRegionHeight());
            this.breaking = new TextureRegion[5];
            texture = regions[0][0];
            System.arraycopy(regions[0], 1, breaking, 0, 5);
        }
    }

    public class AssetGate {
        public final TextureRegion[] textures;

        public AssetGate(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("teleporter");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 2, region.getRegionHeight());
            this.textures = new TextureRegion[2];
            System.arraycopy(regions[0], 0, textures, 0, 2);
        }
    }

    public class AssetPowerUp {
        public final TextureRegion[] fire;
        public final TextureRegion[] bomb;
        public final TextureRegion[] itemDestroy;

        public AssetPowerUp(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("items");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 2, region.getRegionHeight() / 5);
            this.fire = new TextureRegion[2];
            this.bomb = new TextureRegion[2];
            System.arraycopy(regions[0], 0, fire, 0, 2);
            System.arraycopy(regions[1], 0, bomb, 0, 2);

            region = atlas.findRegion("itemDestroy");
            regions = region.split(region.getRegionWidth() / 7, region.getRegionHeight());
            this.itemDestroy = new TextureRegion[7];
            System.arraycopy(regions[0], 0, itemDestroy, 0, 7);
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
