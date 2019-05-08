package g11.mygdx.game.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Button {

    private Sprite button;
    private Music buttonClick;

    public Button(Sprite buttonSprite, float x, float y) {
        this.button = buttonSprite;
        this.button.setPosition(x, y);
        buttonClick = Gdx.audio.newMusic(Gdx.files.internal("buttonClick.mp3"));
        buttonClick.setVolume(0.5f);
    }

    public boolean isClicked(float x, float y) {
        if (x >= this.button.getX() && x <= (this.button.getX() + this.button.getWidth()) && y >= this.button.getY() && y <= (this.button.getY() + this.button.getHeight())) {
            buttonClick.play();
            return true;

        }

        return false;
    }

    public Sprite getButton() {
        return button;
    }
}
