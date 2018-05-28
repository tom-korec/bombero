package tomcarter.bombero.game.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.utils.Assets;

public abstract class MenuOption {
    private String text;
    private int x;
    private int y;
    private boolean selected;

    public MenuOption(String text, int x, int y, boolean selected) {
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
        BitmapFont font = selected ? Assets.instance.fonts.fontL : Assets.instance.fonts.fontM;
        font.setColor(0,0,0, 1f);
        font.draw(batch, text, x, y);
        font.setColor(1,1,1, 1f);
    }

    public abstract void execute();
}
