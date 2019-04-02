package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.modules.Button;
import g11.mygdx.game.modules.NoButton;
import g11.mygdx.game.modules.YesButton;

public class ConfirmationState implements IState {

    private String previousState;
    private String nextState;
    private Sprite confirmationScreen;
    private Button yesButton;
    private Button noButton;
    private Array<Sprite> sprites = new Array<Sprite>();

    public ConfirmationState(String previousState) {

        this.previousState = previousState;
        this.nextState = "menuState";

        Texture confirmationScreenTexture = new Texture("confirmation.png");
        this.confirmationScreen = new Sprite(confirmationScreenTexture);
        this.confirmationScreen.setPosition(0,0);

        Texture yesTexture = new Texture("yes_button.png");
        this.yesButton = new YesButton(new Sprite(yesTexture), (confirmationScreen.getWidth() / 2) - (yesTexture.getWidth() / 2), 100);

        Texture noTexture = new Texture("no_button.png");
        this.noButton = new NoButton(new Sprite(noTexture), (confirmationScreen.getWidth() / 2) - (yesTexture.getWidth() / 2), 240);

        this.sprites.add(confirmationScreen);
        this.sprites.add(yesButton.getButton());
        this.sprites.add(noButton.getButton());

    }

    public String confirmUserChoice(float x, float y) {
        if (this.yesButton.isClicked(x, y)) {
            return "menuState";
        }
        if (this.noButton.isClicked(x, y)) {
            return previousState;
        }
        return "confirmationState";
    }

    @Override
    public String parseInput(float[] data) {
        return confirmUserChoice(data[0], data[1]);
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
