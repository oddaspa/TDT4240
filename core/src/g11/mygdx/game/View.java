package g11.mygdx.game;

/* GENERIC SCREEN THAT TAKES DATA FROM MODEL AND PUSHES TO THE SCREEN */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import g11.mygdx.game.states.Screen;

public class View extends Screen {
    float gx;
    float gy;
    int currentScreen;
    private SpriteBatch sb;
    private Controller channel;


    View(SpriteBatch batch) {
        super(batch);
        this.channel = null;
        this.sb = batch;
        this.currentScreen = 1;
    }


    public void addObserver(Controller c){
        this.channel = c;
    }
    // METHODS TO GET THE DATA

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.println("View got input");
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gx = touchPos.x;
            gy = BattleSheep.HEIGHT - touchPos.y;
            System.out.println("(X: " + gx + ", " + gy + ").");
        }
        float[] coordinates = new float[2];
        coordinates[0] = gx;
        coordinates[1] = gy;
        this.channel.update(coordinates);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(Array<Sprite> sprites) {
        sb.begin();
        for (Sprite s : sprites) {
            sb.draw(s.getTexture(), Math.round(s.getX()),Math.round(s.getY()), s.getWidth(), s.getHeight());
        }
        sb.end();
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
