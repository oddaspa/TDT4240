package g11.mygdx.game.modules;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Button {

    private Sprite button;

    public Button(Sprite buttonSprite) {
        this.button = buttonSprite;
    }

    public boolean isClicked(float x, float y) {
        if (x >= this.button.getX()
                && x <= (this.button.getX() + this.button.getWidth())
                && y >= this.button.getY()
                && y <= (this.button.getY() + this.button.getHeight())) {
            return true;

        }

        return false;
    }

}
