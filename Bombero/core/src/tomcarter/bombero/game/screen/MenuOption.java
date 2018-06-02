package tomcarter.bombero.game.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomcarter.bombero.assets.Assets;

/**
 * Option in menus
 */
public abstract class MenuOption {
    private String text;
    private float x;
    private float y;
    private boolean selected;
    private boolean selectable;

    public MenuOption(String text, float x, float y, boolean selectable) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    /**
     * If selectable, set selected to true
     */
    public void select(){
        if (selectable){
            selected = true;
        }
    }

    /**
     * If selectable, set selected to false
     */
    public void deselect(){
        if (selectable){
            selected = false;
        }
    }

    /**
     * Render text to screen
     * @param batch - libGdx batch
     */
    public void render(SpriteBatch batch){
        BitmapFont font;
        if (!selectable){
            font = Assets.instance.fonts.menuUnselectable;
        }
        else {
            font = selected ? Assets.instance.fonts.menuSelected : Assets.instance.fonts.menuDefault;
        }
        font.draw(batch, text, x, y);
    }

    /**
     * Executes this after choosing option
     */
    public abstract void execute();
}
