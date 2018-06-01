package tomcarter.bombero.game.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.assets.Assets;

public abstract class MenuOption {
    private String text;
    private float x;
    private float y;
    private boolean selected;

    public MenuOption(String text, float x, float y, boolean selected) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.selected = selected;
    }

    public void select(){
        selected = true;
    }

    public void deselect(){
        selected = false;
    }

    public void render(SpriteBatch batch){
        BitmapFont font = selected ? Assets.instance.fonts.menuSelected : Assets.instance.fonts.menuDefault;
        font.draw(batch, text, x, y);
    }

    public abstract void execute();
}
