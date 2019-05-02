package g11.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Screen {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected SpriteBatch sb;
    protected Viewport gamePort;

    protected Screen(SpriteBatch batch){
        cam = new OrthographicCamera(BattleSheep.WIDTH, BattleSheep.HEIGHT);
        gamePort = new FitViewport(480, 800, cam);
        mouse = new Vector3();
        cam.update();
        sb = batch;

    }
    protected  abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(Array<Sprite> sprites, Array<String> messages);
    public abstract void resize(int width, int height);
    public abstract void dispose();
}
