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

    public HomeButton() {
        Texture hb = new Texture("home.png");
        this.homeButton = new Sprite(hb, hb.getWidth() / 6, hb.getHeight() / 6);
        this.homeButton.setPosition((float) 10, (float) BattleSheep.HEIGHT - 10 - this.homeButton.getHeight());
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
        Skin uiSkin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));
        Stage stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Dialog dialog = new Dialog("Warning", uiSkin, "dialog") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.text("Are you sure you want to yada yada?");
        dialog.button("Yes", true); //sends "true" as the result
        dialog.button("No", false); //sends "false" as the result
        dialog.show(stage);

    }

    public Sprite getHomeButton() {
        return homeButton;
    }
}
