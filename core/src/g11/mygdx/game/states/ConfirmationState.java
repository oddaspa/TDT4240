package g11.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.BattleSheep;
import g11.mygdx.game.Model;
import g11.mygdx.game.modules.Button;
import g11.mygdx.game.modules.HomeButton;
import g11.mygdx.game.modules.NoButton;
import g11.mygdx.game.modules.YesButton;

public class ConfirmationState implements IState {

    private String previousState;
    private String nextState;
    private Sprite confirmationScreen;
    private Button yesButton;
    private Button noButton;
    private Button soundButton;
    private Array<Sprite> sprites = new Array<Sprite>();
    private MenuState menu;

    public ConfirmationState(String previousState, MenuState menu) {
        this.menu = menu;
        this.previousState = previousState;
        this.nextState = "menuState";

        Texture confirmationScreenTexture = new Texture("confirmation.png");
        this.confirmationScreen = new Sprite(confirmationScreenTexture);
        this.confirmationScreen.setPosition(0,0);
        this.confirmationScreen.setSize(BattleSheep.WIDTH, BattleSheep.HEIGHT);

        Texture yesTexture = new Texture("yes_button.png");
        this.yesButton = new YesButton(new Sprite(yesTexture), (confirmationScreen.getWidth() / 2) - (BattleSheep.WIDTH / 3), (float) (BattleSheep.HEIGHT / 1.9));

        Texture noTexture = new Texture("no_button.png");
        this.noButton = new NoButton(new Sprite(noTexture), (confirmationScreen.getWidth() / 2) - (BattleSheep.WIDTH / 3), (float) (BattleSheep.HEIGHT / 4));

        Texture soundTexture = new Texture("sound.png");
        Sprite soundSprite = new Sprite(soundTexture, BattleSheep.WIDTH / 8, BattleSheep.WIDTH / 8);
        this.soundButton = new HomeButton(soundSprite, (float) BattleSheep.WIDTH/2 - soundSprite.getWidth()/2, (float) BattleSheep.HEIGHT/10);

        this.sprites.add(confirmationScreen);
        this.sprites.add(yesButton.getButton());
        this.sprites.add(noButton.getButton());
        this.sprites.add(soundButton.getButton());

    }

    public String confirmUserChoice(float x, float y) {
        if (this.yesButton.isClicked(x, y)) {
            return "menuState";
        }
        if (this.noButton.isClicked(x, y)) {
            return previousState;
        }
        if (this.soundButton.isClicked(x,y)){
            Gdx.app.log("confirmationstate----","sound pressed");
            if (menu.music.getVolume() == 0.6f){
                menu.music.setVolume(0f);
            }else{
                menu.music.setVolume(0.6f);
            }
        }
        return "confirmationState";
    }

    @Override
    public String parseInput(float[] data) {
        if (data == null) {
            return "confirmationState";
        } else {
            return confirmUserChoice(data[0], data[1]);
        }
    }

    @Override
    public Array<String> serveMessages() {
        return null;
    }

    @Override
    public Array<Sprite> serveData() {
        return this.sprites;
    }

    @Override
    public void loadData() {

    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }
}
