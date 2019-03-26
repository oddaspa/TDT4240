package g11.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public abstract class Screen {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected SpriteBatch sb;

    protected Screen(SpriteBatch batch){
        sb = batch;
        cam = new OrthographicCamera();
        mouse = new Vector3();

    }

    protected  abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(Array<Sprite> sprites, Array<String> messages);
    public abstract void dispose();
}
