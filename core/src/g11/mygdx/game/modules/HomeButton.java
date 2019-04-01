package g11.mygdx.game.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import g11.mygdx.game.BattleSheep;

public class HomeButton {

    private Sprite homeButton;
    private Sprite modalSprite;

    public HomeButton() {
        Texture homeButtonTexture = new Texture("home.png");
        this.homeButton = new Sprite(homeButtonTexture, homeButtonTexture.getWidth() / 6, homeButtonTexture.getHeight() / 6);
        this.homeButton.setPosition((float) 10, (float) BattleSheep.HEIGHT - 10 - this.homeButton.getHeight());

        Texture goHomeTexture = new Texture("go_home_modal.png");
        this.modalSprite = new Sprite(goHomeTexture, goHomeTexture.getWidth() / 2, goHomeTexture.getHeight() / 2);
    }

    public boolean isClicked(float x, float y) {
        if (x >= this.homeButton.getX()
                && x <= (this.homeButton.getX() + this.homeButton.getWidth())
                && y >= this.homeButton.getY()
                && y <= (this.homeButton.getY() + this.homeButton.getHeight())) {
            return true;

        }

        return false;
    }


    public void showDialog() {


    }

    public Sprite getHomeButton() {
        return homeButton;
    }
}
