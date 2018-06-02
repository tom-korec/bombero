package tomcarter.bombero.assets;

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
import tomcarter.bombero.utils.Constants;


/**
 * Singleton
 * instance of this class contains game Assets - fonts and textures
 * contains inner classes which represent specific Asset
 */
public class Assets implements AssetErrorListener, Disposable {
    public static final String TAG = Assets.class.getName();
    public static Assets instance;

    private AssetManager assetManager;

    public final AssetMenu menu;
    public final AssetFonts fonts;
    public final AssetGui gui;

    public final AssetPlayer player;
    public final AssetEnemy enemies;

    public final AssetWall wall;
    public final AssetFloor floor;
    public final AssetBrick brick;

    public final AssetGate gate;
    public final AssetPowerUp powerUp;

    public final AssetBomb bomb;
    public final AssetExplosion explosion;

    /**
     * initialization of instance
     * @param assetManager - libGdx class, should not be null (except testing)
     */
    public static void init(AssetManager assetManager){
        if (assetManager != null){
            instance = new Assets(assetManager);
        }
        else {
            instance = new Assets();
        }
    }

    private Assets(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        menu = new AssetMenu(atlas);
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
        public final TextureRegion background;
        public final TextureRegion title;


        AssetMenu(TextureAtlas atlas) {
            background = atlas.findRegion("background");
            title = atlas.findRegion("title");
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultFont;
        public final BitmapFont fontXS;
        public final BitmapFont fontS;
        public final BitmapFont fontM;
        public final BitmapFont fontL;
        public final BitmapFont menuSelected;
        public final BitmapFont menuDefault;
        public final BitmapFont menuUnselectable;

        AssetFonts () {
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

            menuSelected = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC_MENU_SELECTED), true);
            menuSelected.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            menuDefault = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC_MENU_DEFAULT), true);
            menuDefault.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            menuUnselectable = new BitmapFont(Gdx.files.internal(Constants.FONT_CENTURY_GOTHIC_MENU_UNSELECTABLE), true);
            menuUnselectable.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        void dispose(){
            defaultFont.dispose();
            fontXS.dispose();
            fontS.dispose();
            fontM.dispose();
            fontL.dispose();
            menuSelected.dispose();
            menuDefault.dispose();
            menuUnselectable.dispose();
        }
    }

    public class AssetGui {
        public final TextureRegion life;
        public final TextureRegion score;

        AssetGui(TextureAtlas atlas) {
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

        AssetPlayer(){
            down = getEmptyTextureRegionArray(4);
            left = getEmptyTextureRegionArray(4);
            right = getEmptyTextureRegionArray(4);
            up = getEmptyTextureRegionArray(4);
            death = getEmptyTextureRegionArray(4);
        }

        AssetPlayer(TextureAtlas atlas){
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
        public final TextureRegion potatoExplosion[];

        public final TextureRegion cloud[];
        public final TextureRegion cloudExplosion[];

        public final TextureRegion pig[];
        public final TextureRegion pigExplosion[];

        AssetEnemy(){
            potato = getEmptyTextureRegionArray(4);
            potatoExplosion = getEmptyTextureRegionArray(4);
            cloud = getEmptyTextureRegionArray(4);
            cloudExplosion = getEmptyTextureRegionArray(4);
            pig = getEmptyTextureRegionArray(4);
            pigExplosion = getEmptyTextureRegionArray(4);
        }

        AssetEnemy(TextureAtlas atlas) {
            AtlasRegion regionDeath = atlas.findRegion("enemyDeath");
            TextureRegion[][] regionsDeath = regionDeath.split(regionDeath.getRegionWidth() / 7, regionDeath.getRegionHeight());

            AtlasRegion region = atlas.findRegion("enemyPotato");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 5, region.getRegionHeight());
            this.potato = new TextureRegion[3];
            this.potatoExplosion = new TextureRegion[9];
            System.arraycopy(regions[0], 0, potato, 0, 3);
            System.arraycopy(regions[0], 3, potatoExplosion, 0, 2);
            System.arraycopy(regionsDeath[0], 0, potatoExplosion, 2, 7);

            region = atlas.findRegion("enemyCloud");
            regions = region.split(region.getRegionWidth() / 5, region.getRegionHeight());
            this.cloud = new TextureRegion[3];
            this.cloudExplosion = new TextureRegion[9];
            System.arraycopy(regions[0], 0, cloud, 0, 3);
            System.arraycopy(regions[0], 3, cloudExplosion, 0, 2);
            System.arraycopy(regionsDeath[0], 0, cloudExplosion, 2, 7);

            region = atlas.findRegion("enemyPig");
            regions = region.split(region.getRegionWidth() / 5, region.getRegionHeight());
            this.pig = new TextureRegion[3];
            this.pigExplosion = new TextureRegion[9];
            System.arraycopy(regions[0], 0, pig, 0, 3);
            System.arraycopy(regions[0], 3, pigExplosion, 0, 2);
            System.arraycopy(regionsDeath[0], 0, pigExplosion, 2, 7);
        }
    }

    public class AssetWall {
        public final TextureRegion texture;

        AssetWall(){
            texture = new TextureRegion();
        }

        AssetWall(TextureAtlas atlas) {
            this.texture = atlas.findRegion("wall");
        }
    }

    public class AssetFloor {
        public final TextureRegion texture;

        AssetFloor(){
            texture = new TextureRegion();
        }

        AssetFloor(TextureAtlas atlas) {
            this.texture = atlas.findRegion("floor");
        }
    }

    public class AssetBrick {
        public final TextureRegion texture;
        public final TextureRegion[] breaking;

        AssetBrick(){
            texture = new TextureRegion();
            breaking = getEmptyTextureRegionArray(5);
        }

        AssetBrick(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion("brick");
            TextureRegion[][] regions = region.split(region.getRegionWidth() / 6, region.getRegionHeight());
            this.breaking = new TextureRegion[5];
            texture = regions[0][0];
            System.arraycopy(regions[0], 1, breaking, 0, 5);
        }
    }

    public class AssetGate {
        public final TextureRegion[] textures;

        AssetGate(){
            textures = getEmptyTextureRegionArray(2);
        }

        AssetGate(TextureAtlas atlas) {
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

        AssetPowerUp(){
            fire = getEmptyTextureRegionArray(2);
            bomb = getEmptyTextureRegionArray(2);
            itemDestroy = getEmptyTextureRegionArray(7);
        }

        AssetPowerUp(TextureAtlas atlas) {
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

        AssetBomb(){
            textures = getEmptyTextureRegionArray(3);
        }

        AssetBomb(TextureAtlas atlas) {
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

        AssetExplosion(){
            downEnd = getEmptyTextureRegionArray(4);
            leftEnd = getEmptyTextureRegionArray(4);
            rightEnd = getEmptyTextureRegionArray(4);
            upEnd = getEmptyTextureRegionArray(4);
            center = getEmptyTextureRegionArray(4);
            horizontal = getEmptyTextureRegionArray(4);
            vertical = getEmptyTextureRegionArray(4);
        }


        AssetExplosion(TextureAtlas atlas) {
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
        fonts.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    private Assets(){
        menu = null;
        fonts = null;
        gui = null;
        player = new AssetPlayer();
        enemies = new AssetEnemy();
        wall = new AssetWall();
        floor = new AssetFloor();
        brick = new AssetBrick();
        gate = new AssetGate();
        powerUp = new AssetPowerUp();
        bomb = new AssetBomb();
        explosion = new AssetExplosion();
    }

    private TextureRegion[] getEmptyTextureRegionArray(int size){
        TextureRegion mock[] = new TextureRegion[size];
        for (int i = 0; i < size; ++i){
            mock[i] = new TextureRegion();
        }
        return mock;
    }
}
