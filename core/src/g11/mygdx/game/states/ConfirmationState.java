package g11.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class ConfirmationState implements IState {

    private String previousState;
    private String nextState;
    private Sprite confirmationScreen;

    public ConfirmationState(String previousState, String nextState) {

        this.previousState = previousState;
        this.nextState = nextState;

        Texture confirmationScreenTexture = new Texture("confirmation.png");
        this.confirmationScreen = new Sprite();

    }

    public String confirmUserChoice() {

        return previousState;
    }

    @Override
    public String parseInput(float[] data) {
        return null;
    }

    @Override
    public Array<String> serveMessages() {
        return null;
    }

    @Override
    public Array<Sprite> serveData() {
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(this.confirmationScreen);
        return sprites;
    }

    @Override
    public void loadData() {

    }
}
