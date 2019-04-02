package g11.mygdx.game.modules;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import g11.mygdx.game.BattleSheep;

public class HomeButton extends Button {

    private Sprite homeButton;
    private Sprite modalSprite;

    public HomeButton(Sprite buttonSprite) {
        super(buttonSprite);

        Texture homeButtonTexture = new Texture("home.png");
        this.homeButton = new Sprite(homeButtonTexture, homeButtonTexture.getWidth() / 6, homeButtonTexture.getHeight() / 6);
        this.homeButton.setPosition((float) 10, (float) BattleSheep.HEIGHT - 10 - this.homeButton.getHeight());

        Texture goHomeTexture = new Texture("go_home_modal.png");
        this.modalSprite = new Sprite(goHomeTexture, goHomeTexture.getWidth() / 2, goHomeTexture.getHeight() / 2);
    }


    public void showDialog() {


    }

    public Sprite getHomeButton() {
        return homeButton;
    }
}
